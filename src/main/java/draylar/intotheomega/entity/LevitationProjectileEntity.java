package draylar.intotheomega.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class LevitationProjectileEntity extends ProjectileEntity {

    public LevitationProjectileEntity(EntityType<? extends ProjectileEntity> type, World world) {
        super(type, world);
    }

    @Override
    public void tick() {
        super.tick();

        if(!world.isClient) {
            // Entity collision
            HitResult hitResult = ProjectileUtil.getCollision(this, this::canHit);
            if (hitResult.getType() != HitResult.Type.MISS) {
                onCollision(hitResult);
            }

            // Kill when not moving
            if(getVelocity().multiply(1, 0, 1).length() <= 0.5) {
                discard();
                return;
            }

            // Kill when too old
            if(age >= 20 * 7) {
                discard();
                return;
            }
        } else {
            for(int i = 0; i < 5; i++) {
                world.addParticle(ParticleTypes.END_ROD, true, getX() + world.random.nextFloat() - 0.5, getY() + world.random.nextFloat() - 0.5, getZ() + world.random.nextFloat() - 0.5, 0, 0, 0);
            }
        }

        // Movement
        checkBlockCollision();
        move(MovementType.SELF, getVelocity());
    }

    @Override
    public void onEntityHit(EntityHitResult entityHit) {
        super.onEntityHit(entityHit);

        if(!world.isClient) {
            // Deal direct damage to enemies hit.
            Entity target = entityHit.getEntity();
            if(getOwner() instanceof LivingEntity living) {
                target.damage(DamageSource.mobProjectile(this, living), 10.0f);
            } else {
                target.damage(DamageSource.GENERIC, 10.0f);
            }

            // Enemies hit are inflicted with the Levitation effect.
            if(target instanceof LivingEntity living) {
                living.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 10 * 5, 1, true, false));
            }

            // SFX
            world.playSound(null, getX(), getY(), getZ(), SoundEvents.ENTITY_SHULKER_BULLET_HIT, SoundCategory.AMBIENT, 2.0f, 1.0f);
        }
    }

    @Override
    public void onBlockCollision(BlockState state) {
        super.onBlockCollision(state);

        if(!world.isClient && !state.isAir() || onGround) {
            discard();
        }
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);

        if(!world.isClient && onGround) {
            discard();
        }
    }

    @Override
    public void initDataTracker() {

    }
}
