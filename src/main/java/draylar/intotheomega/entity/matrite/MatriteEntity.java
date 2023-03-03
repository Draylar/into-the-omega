package draylar.intotheomega.entity.matrite;

import draylar.intotheomega.api.RandomUtils;
import draylar.intotheomega.api.VectorHelper;
import draylar.intotheomega.api.event.ExplosionDamageEntityCallback;
import draylar.intotheomega.entity.void_matrix.VoidMatrixEntity;
import draylar.intotheomega.registry.OmegaEntities;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class MatriteEntity extends ProjectileEntity implements IAnimatable {

    private static final TrackedData<Boolean> IDLE = DataTracker.registerData(MatriteEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    // Animation fields
    private final AnimationFactory factory = new AnimationFactory(this);

    // Entity state
    private Entity target;
    private VoidMatrixEntity source;
    private int shootAt = -1;
    private int nonIdleTicks = 0;
    private int idleShootTime = 10 + world.random.nextInt(25);
    private boolean hasFiredAtPlayer = false;

    public MatriteEntity(EntityType<? extends MatriteEntity> type, World world) {
        super(type, world);
    }

    // client constructor
    public MatriteEntity(World world, double x, double y, double z) {
        super(OmegaEntities.MATRITE, world);
        this.updatePosition(x, y, z);
        this.updateTrackedPosition(x, y, z);
    }

    public MatriteEntity(World world) {
        super(OmegaEntities.MATRITE, world);
    }

    @Override
    public void initDataTracker() {
        dataTracker.startTracking(IDLE, true);
    }

    public boolean idle() {
        return dataTracker.get(IDLE);
    }

    public void setIdle(boolean idle) {
        dataTracker.set(IDLE, idle);
    }

    @Override
    public void tick() {
        super.tick();

        HitResult hitResult = ProjectileUtil.getCollision(this, this::collidesWith);
        if (hitResult.getType() != HitResult.Type.MISS) {
            onCollision(hitResult);
        }
        checkBlockCollision();

        Vec3d vel = this.getVelocity();
        updatePosition(getX() + vel.x, getY() + vel.y, getZ() + vel.z);

        // undo .99 velocity modifier at end of super tick
        setVelocity(getVelocity().multiply(1.01));

        // kill if too old
        if(age > 20 * 15) {
            discard();
        }

        // fire if timer is set
        if(!world.isClient && shootAt != -1 && this.age > shootAt && dataTracker.get(IDLE)) {
            shoot();
        }

        // track target
        if(!world.isClient && source != null && target != null && !dataTracker.get(IDLE)) {
            nonIdleTicks++;

            // veer towards target
            if(nonIdleTicks >= idleShootTime && !hasFiredAtPlayer){
                Vec3d towards = target.getPos().subtract(getPos()).normalize();
                Vec3d towardsTarget = towards.multiply(3.0);
                setVelocity(towardsTarget);
                hasFiredAtPlayer = true;
            }
        }

        // check if there are any intercepting projectiles, remove if so
        if(!world.getEntitiesByClass(ProjectileEntity.class, new Box(getBlockPos().add(-1, -1, -1), getBlockPos().add(1f, 1f, 1f)), entity -> !(entity instanceof MatriteEntity)).isEmpty()) {
            explode();
            discard();
        }
    }

    @Override
    public boolean isPushable() {
        return true;
    }

    @Override
    public boolean collides() {
        return true;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        }

        // If the projectile is hit by an arrow while it is flying, direct it back towards the source
        if(source.isProjectile()) {
            Entity owner = getOwner();
            this.scheduleVelocityUpdate();

            // if the owner is not null, send the projectile back at them
            if(owner != null) {
                Vec3d towardsOwner = getPos().subtract(owner.getPos()).normalize().multiply(-1);
                setVelocity(towardsOwner);
            }

            // owner is null, just reverse velocity
            else {
                setVelocity(getVelocity().multiply(-1));
            }

            // re-assign owner
            setOwner(source.getAttacker());
            shootAt = -1;
            return true;
        }

        // melee attacks
        this.scheduleVelocityUpdate();
        Entity entity = source.getAttacker();

        if (entity != null) {
            Vec3d vec3d = entity.getRotationVector();
            this.setVelocity(vec3d);
            this.setOwner(entity);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);

        if (!this.world.isClient && hitResult.getType().equals(HitResult.Type.BLOCK)) {
            explode();
            discard();
        }
    }

    @Override
    public void onEntityHit(EntityHitResult hit) {
        if(world.isClient) {
            return;
        }

        Entity entity = hit.getEntity();
        float damage = 10;

        // Determine whether a hit on a VM was valid
        boolean isValidVMHit = false;
        if(entity instanceof VoidMatrixEntity) {
            if(getOwner() instanceof PlayerEntity) {
                isValidVMHit = true;
            }
        }

        // If this projectile is hitting a Void Matrix but was summoned by the Void Matrix,
            // ignore the hit.
        if(entity instanceof VoidMatrixEntity && !isValidVMHit) {
            return;
        }

        // If this entity hit a Void Matrix but was deflected by a player,
            // increase damage done to 25 and allow the hit.
        if(entity instanceof VoidMatrixEntity) {
            damage = 25;
            ((VoidMatrixEntity) entity).incrementStun();
        }

        if(!(entity instanceof MatriteEntity)) {
            // Calculate damage source type based on whether this Matrite has a valid source
            DamageSource damageSource;
            if(source != null) {
                damageSource = DamageSource.mobProjectile(this, source);
            } else if (getOwner() instanceof LivingEntity) {
                damageSource = DamageSource.mobProjectile(this, (LivingEntity) getOwner());
            } else {
                damageSource = DamageSource.MAGIC;
            }

            int fireTicks = entity.getFireTicks();

            // Transfer fire
            if (this.isOnFire()) {
                entity.setOnFireFor(5);
            }

            // todo: this does not account for armor
            if (entity.damage(damageSource, damage)) {
                if (entity instanceof LivingEntity) {
                    LivingEntity livingEntity = (LivingEntity) entity;

                    if (!this.world.isClient && source != null) {
                        EnchantmentHelper.onUserDamaged(livingEntity, source);
                        EnchantmentHelper.onTargetDamaged(source, livingEntity);
                    }
                }
            }

            explode();
            discard();
        }
    }

    public void explode() {
        if(world instanceof ServerWorld serverWorld) {
            serverWorld.spawnParticles(ParticleTypes.EXPLOSION, getX(), getY(), getZ(), 3, 0.25, 0.25, 0.25, 0);
            world.playSound(null, getBlockPos(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.HOSTILE, .25f, 0);
        }

        world.getEntitiesByClass(LivingEntity.class, new Box(getBlockPos().add(-3, -3 ,-3), getBlockPos().add(3, 3, 3)), entity -> !(entity instanceof VoidMatrixEntity))
                .forEach(target -> {
                    if(source != null) {
                        target.damage(DamageSource.mobProjectile(this, source), 10);
                    } else {
                        if(getOwner() instanceof LivingEntity) {
                            DamageSource source = DamageSource.mobProjectile(this, (LivingEntity) getOwner());
                            TypedActionResult<Float> result = ExplosionDamageEntityCallback.EVENT.invoker().onDamage(target, source, 10);

                            if(!result.getResult().equals(ActionResult.FAIL)) {
                                target.damage(source, result.getValue());
                            }
                        } else {
                            target.damage(DamageSource.MAGIC, 10);
                        }
                    }
                });
    }

    public void setTarget(Entity target, int delay) {
        this.shootAt = this.age + delay;
        this.target = target;
    }

    public void setSource(VoidMatrixEntity source) {
        this.source = source;
    }

    public void shoot() {
        if(source != null) {
            setVelocity(RandomUtils.range(world.random, 1.0f), 1, RandomUtils.range(world.random, 1.0f));
            setIdle(false);
            world.playSound(null, getX(), getY(), getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, SoundCategory.HOSTILE, 2, 2);
            return;
        }

        // Calculate vector between new Matrite and the target Player, then fire
        Vec3d vel = target.getPos().add(0, target.getHeight() / 2, 0).subtract(getPos()).normalize();
        setVelocity(vel.multiply(0.25 + world.random.nextFloat() * 0.5));
        shootAt = -1;

        setIdle(false);

        // todo: chance to refire? hard mode mechanic? set shootAt again
        world.playSound(null, getX(), getY(), getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, SoundCategory.HOSTILE, 2, 2);
    }

    @Override
    public boolean collidesWith(Entity other) {
        if (other instanceof VoidMatrixEntity && !(getOwner() instanceof VoidMatrixEntity)) {
            return true;
        } else if (other instanceof VoidMatrixEntity) {
            return false;
        }

        return super.collidesWith(other);
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }

    @Override
    public boolean hasNoGravity() {
        return true;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "spin", 20, this::refresh));
    }

    private <P extends IAnimatable> PlayState refresh(AnimationEvent<P> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.void_matrix.spin", false));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}
