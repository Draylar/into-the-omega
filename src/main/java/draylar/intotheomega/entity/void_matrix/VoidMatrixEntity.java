package draylar.intotheomega.entity.void_matrix;

import draylar.intotheomega.api.HomeEntity;
import draylar.intotheomega.entity.matrite.MatriteEntity;
import draylar.intotheomega.entity.void_matrix.ai.*;
import draylar.intotheomega.entity.void_matrix.ai.look.VoidMatrixLookControl;
import draylar.intotheomega.entity.void_matrix.ai.movement.VoidMatrixMoveControl;
import draylar.intotheomega.registry.OmegaBlocks;
import draylar.intotheomega.registry.OmegaParticles;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.FlyingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.ArrayList;
import java.util.List;

/*
 * The Void Matrix is an entity that spawns on boss platforms in the middle ring of the End (~5k+ blocks out).
 * It has 600 HP and offers an upgrade to Crystalia, as well as End-Crystal Matrix themed loot.
 *
 * The Void Matrix has 3 primary attack stages.
 *
 *  If all nearby players leave the battle or are killed, the Void Matrix will heal back to full and enter a dormant state at its altar.
 *  The Void Matrix restores 100 health when killing a player, but will not revert to a previous phase unless all players leave the area.
 */
public class VoidMatrixEntity extends FlyingEntity implements IAnimatable, HomeEntity {

    private final AnimationFactory factory = new AnimationFactory(this);;
    private final AnimationController<VoidMatrixEntity> controller = new AnimationController<>(this, "base", 20, this::refresh);

    public static final byte STOP = 100;
    public static final byte SINGLE_SPIN = 101;
    public static final byte QUICK_SINGLE_SPIN = 102;
    public static final byte CONSTANT_SPIN = 103;
    public static final byte STOMP = 104;

    private final ServerBossBar bossBar;
    private BlockPos home = null;
    private Stage stage = Stage.ONE;
    private boolean isSlamming = false;
    private boolean stunned = false;
    private int stunCounter = 0;
    private boolean flagStop = false;
    // TODO: SERIALIZE THIS ^^

    public static final int MAX_LASER_TICKS = 10 * 25;
    private static final TrackedData<Boolean> FIRING_LASER = DataTracker.registerData(VoidMatrixEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Integer> LASER_TICKS = DataTracker.registerData(VoidMatrixEntity.class, TrackedDataHandlerRegistry.INTEGER);

    public VoidMatrixEntity(EntityType<? extends VoidMatrixEntity> entityType, World world) {
        super(entityType, world);
        moveControl = new VoidMatrixMoveControl(this);
        lookControl = new VoidMatrixLookControl(this);
        bossBar = (ServerBossBar) new ServerBossBar(this.getDisplayName(), BossBar.Color.PURPLE, BossBar.Style.PROGRESS).setDarkenSky(true);
        ignoreCameraFrustum = true;
    }

    @Override
    public void initGoals() {
        // Stun has max priority
        goalSelector.add(0, new StunnedGoal(this));

        // Always look at nearby players
        goalSelector.add(1, new LookAtTargetGoal(this));

        // Things to do when the boss is alone
        goalSelector.add(1, new HealWhenAloneGoal(this));
        goalSelector.add(1, new TeleportHomeWhenAloneGoal(this));

        /*
         *     Stage 1 <=> 600 - 400 HP
         *          In Stage 1, the Void Matrix summons floating projectiles similar to Shulker bullets. These look like miniature floating End Crystals.
         *          These bullets will take 2 seconds to aim, showing their path with a particle trail, and then charge ahead at high speeds.
         *          Anything hit by these bullets will take damage. When the bullet lands, additional AOE damage triggers around the hit location.
         *          In hard mode, the Void Matrix has a chance to spawn projectiles behind or above the player, but most will appear surrounding the Void Matrix.
         *
         *          In this stage, the Void Matrix takes 50% damage from melee attacks, and 25% from ranged attacks.
         *          Shooting a projectile while it is being summoned will greatly damage the Void Matrix (-25).
         *          After 3 explosions hit the Void Matrix, it will be stunned.
         *
         *          The Void Matrix will also periodically send out a pulse wave, damaging and knocking back targets nearby.
         *          Targets that are closest to the Void Matrix will take the most damage and knockback.
         */
        goalSelector.add(1, new MatriteProjectileAttackGoal(this, Stage.ONE));
        goalSelector.add(3, new KnockbackPulseGoal(this, Stage.ONE));

        /*
         *     Stage 2 <=> 400 - 200 HP
         *          In Stage 2, the Void Matrix summons charged particle attacks from the sky. These are telegraphed by large vertical lines of particles that last for 3 seconds.
         *          After charging, the attacks will pause for 1 second, then deal large damage to any entity standing underneath it (besides the Void Matrix).
         *          As the Void Matrix gets closer to 200 HP, more attacks will spawn.
         *
         *          In this stage, the Void Matrix takes 10% from physical attacks and 75% from ranged attacks.
         *          The player will need to attack from afar and shoot the Void Matrix while dodging projectiles to progress to the next stage.
         *
         *          Additionally, the Void Matrix will randomly charge at the player, dealing massive damage and launching the player away.
         *          If the player dodges the attack, they will have an opportunity to deal massive damage to the Void Matrix (due to stun).
         */
        goalSelector.add(2, new SlamGoal(this, Stage.TWO));
        goalSelector.add(3, new AggressivelyFloatGoal(this, Stage.TWO, 1.0f));
        goalSelector.add(4, new BeamAttackGoal(this, 20, Stage.TWO));

        /*
         *     Stage 3 <=> 200 - 0 HP
         *          In Stage 3, the final stage, the Void Matrix will unleash a flurry of ranged attacks.
         *          The Void Matrix will take full damage from attacks, but the player will have to balance that with
         *          dodging the massive number of attacks aimed in their direction.
         */
        goalSelector.add(0, new LaserSpinGoal(this, Stage.THREE));
        goalSelector.add(2, new AggressivelyFloatGoal(this, Stage.THREE, 1.25f));
        goalSelector.add(3, new MatriteProjectileAttackGoal(this, Stage.THREE));
        goalSelector.add(3, new BeamAttackGoal(this, 15, Stage.THREE));

        // Idle when no other movement goals are running
//        goalSelector.add(10, new IdleGoal(this));

        // Always target nearby player
        targetSelector.add(2, new FollowTargetGoal<>(this, PlayerEntity.class, 0, false, false, (entity) -> true));
    }

    @Override
    public void initDataTracker() {
        super.initDataTracker();
        dataTracker.startTracking(LASER_TICKS, 0);
        dataTracker.startTracking(FIRING_LASER, false);
    }

    @Nullable
    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable CompoundTag entityTag) {
        if(home == null) {
            home = getBlockPos();
        }

        return super.initialize(world, difficulty, spawnReason, entityData, entityTag);
    }

    @Override
    public void tick() {
        super.tick();

        if(!world.isClient) {
            // attempt to find a home in a nearby spawn block if needed
            if(home == null && age < 5) {
                   for(int x = -5; x <= 5; x++) {
                       for(int z = -5; z <= 5; z++) {
                           for(int y = -5; y <= 5; y++) {
                               BlockState blockState = world.getBlockState(getBlockPos().add(x, y, z));
                               if(blockState.getBlock().equals(OmegaBlocks.VOID_MATRIX_SPAWN_BLOCK)) {
                                   home = getBlockPos().add(x, y, z);
                                   break;
                               }
                           }
                       }
                   }
            }

            this.bossBar.setPercent(this.getHealth() / this.getMaxHealth());

            // Exit current stage if entity health is under minimum threshold
            if(getHealth() < stage.getMinHealth(this)) {
                stage = Stage.NONE;
            }

            // Exit current stage if entity health is above maximum threshold
            if(getHealth() > stage.getMaxHealth(this)) {
                stage = Stage.NONE;
            }

            // Attempt to move to another stage if the current stage is none
            if(stage == Stage.NONE) {
                stage = Stage.getValidStageFor(this);
            }
        }
    }

    @Override
    public void tickMovement() {
        // Entity should have gravity in stage 1
        if(!world.isClient) {
            if (stage == Stage.ONE) {
                if (!isOnGround()) {
                    this.updateVelocity(0.03f, new Vec3d(0, -1, 0));
                    this.velocityDirty = true;
                }
            }
        }

        super.tickMovement();
    }

    @Override
    public void onKilledOther(ServerWorld world, LivingEntity killed) {
        // Restore 100 HP when killing a player
        if(killed instanceof PlayerEntity) {
            heal(100);
        }
    }

    public void init(BlockPos home) {
        this.home = home;
    }

    public Stage getStage() {
        return stage;
    }

    public static DefaultAttributeContainer.Builder createVoidMatrixAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 64.0D)
                .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 10)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 600)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 10);
    }

    @Override
    public void onStartedTrackingBy(ServerPlayerEntity player) {
        super.onStartedTrackingBy(player);
        this.bossBar.addPlayer(player);
    }

    @Override
    public void onStoppedTrackingBy(ServerPlayerEntity player) {
        super.onStoppedTrackingBy(player);
        this.bossBar.removePlayer(player);
    }

    @Override
    public void writeCustomDataToTag(CompoundTag tag) {
        super.writeCustomDataToTag(tag);
        tag.putLong("HomePos", home.asLong());
    }

    @Override
    public void readCustomDataFromTag(CompoundTag tag) {
        super.readCustomDataFromTag(tag);
        home = tag.contains("HomePos") ? null : BlockPos.fromLong(tag.getLong("HomePos"));
    }

    @Override
    public boolean isInvulnerableTo(DamageSource damageSource) {
        // TODO: can't kill while firing lasers
        return super.isInvulnerableTo(damageSource) || isFiringLaser();
    }

    @Override
    public void applyDamage(DamageSource source, float amount) {
        boolean ranged = source.isProjectile() || source.isExplosive();

        if(source.getSource() instanceof MatriteEntity) {
            super.applyDamage(source, amount);
        }

        // Lower damage amount based on current stage reduction of the given type of damage
        if(ranged) {
            super.applyDamage(source, amount * stage.getRangedDamageMultiplier());
        } else {
            super.applyDamage(source, amount * stage.getPhysicalDamageMultiplier());
        }
    }

    @Override
    public @Nullable SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.BLOCK_GLASS_BREAK;
    }

    @Override
    public void pushAwayFrom(Entity entity) {

    }

    @Override
    public void onBlockCollision(BlockState state) {
        super.onBlockCollision(state);

        // If we slam into a valid position...
        if(isSlamming && !world.isClient && !state.isAir()) {
            slam();
        }
    }

    @Override
    public void onPlayerCollision(PlayerEntity player) {
        super.onPlayerCollision(player);

        if(isSlamming && !world.isClient) {
            slam();
        }
    }

    public void slam() {
        // SFX
        world.playSoundFromEntity(null, this, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.HOSTILE, 3.0f, 1.0f);
        ((ServerWorld) world).spawnParticles(OmegaParticles.MATRIX_EXPLOSION, getX(), getY(), getZ(), 100, 0, 0, 0, 1);

        // stop animations & stun
        world.sendEntityStatus(this, VoidMatrixEntity.STOP);
        isSlamming = false;
        stun(true);

        // damage & fling nearby players
        world.getEntitiesByClass(LivingEntity.class, new Box(getBlockPos().add(-8, -8, -8), getBlockPos().add(8, 8, 8)), entity -> entity != this).forEach(e -> {
            double distance = Math.sqrt(Math.pow(e.getX() - getX(), 2) + Math.pow(e.getZ() - getZ(), 2));
            double scale = Math.max(0, -(Math.pow((distance - 3), 3) / 100) + 1);
            e.damage(DamageSource.GENERIC, 25.0f * (float) scale);
        });
    }

    @Override
    public float getEyeHeight(EntityPose pose) {
        return super.getEyeHeight(pose);
    }

    @Override
    public void pushAway(Entity entity) {
        if (!this.isConnectedThroughVehicle(entity)) {
            if (!entity.noClip && !this.noClip) {
                double diffX = entity.getX() - this.getX();
                double diffZ = entity.getZ() - this.getZ();
                double greatestDiff = MathHelper.absMax(diffX, diffZ);

                if (greatestDiff >= 0.009999999776482582D) {
                    greatestDiff = MathHelper.sqrt(greatestDiff);
                    diffX /= greatestDiff;
                    diffZ /= greatestDiff;
                    double mod = 1.0D / greatestDiff;
                    if (mod > 1.0D) {
                        mod = 1.0D;
                    }

                    diffX *= mod;
                    diffZ *= mod;
                    diffX *= 0.05000000074505806D;
                    diffZ *= 0.05000000074505806D;
                    diffX *= 1.0F - this.pushSpeedReduction;
                    diffZ *= 1.0F - this.pushSpeedReduction;

                    int intensity = 3;

                    if (!entity.hasPassengers()) {
                        entity.addVelocity(diffX * intensity, 0.0D, diffZ * intensity);
                    }
                }

            }
        }
    }

    @Override
    public void handleStatus(byte status) {
        switch(status) {
            case STOP:
                controller.markNeedsReload();
                controller.clearAnimationCache();
                flagStop = true;
                break;
            case SINGLE_SPIN:
                controller.markNeedsReload();
                controller.setAnimation(new AnimationBuilder().addAnimation("animation.void_matrix.rotate", false));
                break;
            case QUICK_SINGLE_SPIN:
                controller.markNeedsReload();
                controller.setAnimation(new AnimationBuilder().addAnimation("animation.void_matrix.quick_rotate", false));
                break;
            case CONSTANT_SPIN:
                controller.markNeedsReload();
                controller.setAnimation(new AnimationBuilder().addAnimation("animation.void_matrix.rotate", true));
                break;
            case STOMP:
                controller.markNeedsReload();
                controller.setAnimation(new AnimationBuilder().addAnimation("animation.void_matrix.stomp", false));
                break;
        }

        super.handleStatus(status);
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(controller);
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    private <P extends IAnimatable> PlayState refresh(AnimationEvent<P> event) {
        if(flagStop) {
            flagStop = false;
            event.getController().setAnimation(null);
            return PlayState.STOP;
        }

        return PlayState.CONTINUE;
    }

    @Override
    @Nullable
    public BlockPos getHome() {
        return home.equals(BlockPos.ORIGIN) ? null : home;
    }

    @Override
    public void setHome(BlockPos home) {
        this.home = home;
    }

    @Override
    public boolean hasHome() {
        return home != null;
    }

    @Override
    public int getHomeRadius() {
        return 64;
    }

    public void setSlamming(boolean b) {
        this.isSlamming = b;
    }

    public void incrementStun() {
        stunCounter++;

        // after 3 increments to the counter, stun the boss
        if(stunCounter >= 3 && !world.isClient) {
            stun(true);
            stunCounter = 0;
        }
    }

    public void stun(boolean stunned) {
        if(!this.stunned) {
            world.playSoundFromEntity(null, this, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.HOSTILE, 5.0f, 1.0f);
            ((ServerWorld) world).spawnParticles(OmegaParticles.MATRIX_EXPLOSION, getX(), getY(), getZ(), 50, 1, 1, 1, 1);
        }

        this.stunned = stunned;
    }

    public boolean isStunned() {
        return stunned;
    }

    public void setFiringLaser(boolean firingLaser) {
        dataTracker.set(FIRING_LASER, firingLaser);
    }

    public boolean isFiringLaser() {
        return dataTracker.get(FIRING_LASER);
    }

    public void setLaserTicks(int laserTicks) {
        dataTracker.set(LASER_TICKS, laserTicks);
    }

    public int getLaserTicks() {
        return dataTracker.get(LASER_TICKS);
    }

    public boolean isSlamming() {
        return isSlamming;
    }

    @Override
    public void checkDespawn() {
        if (this.world.getDifficulty() == Difficulty.PEACEFUL && this.isDisallowedInPeaceful()) {
            this.remove();
        }
    }

    @Override
    public boolean cannotDespawn() {
        return true;
    }

    public enum Stage {
        NONE(1, 1, -Integer.MAX_VALUE, -Integer.MAX_VALUE),
        ONE(.5f, .75f, 1, 2 / 3f),
        TWO(.75f, .5f, 2 / 3f, 1 / 3f),
        THREE(1f, 1.1f, 1 / 3f, 0);

        private final float rangedDamageMultiplier;
        private final float meleeDamageMultiplier;
        private final float minHealth;
        private final float maxHealth;

        Stage(float rangedDamageMultiplier, float meleeDamageMultiplier, float maxHealth, float minHealth) {
            this.rangedDamageMultiplier = rangedDamageMultiplier;
            this.meleeDamageMultiplier = meleeDamageMultiplier;
            this.minHealth = minHealth;
            this.maxHealth = maxHealth;
        }

        public float getRangedDamageMultiplier() {
            return rangedDamageMultiplier;
        }

        public float getPhysicalDamageMultiplier() {
            return meleeDamageMultiplier;
        }

        public float getMinHealth(LivingEntity entity) {
            return minHealth * entity.getMaxHealth();
        }

        public float getMaxHealth(LivingEntity entity) {
            return maxHealth * entity.getMaxHealth();
        }

        public float getMinHealthScalar() {
            return minHealth;
        }

        public float getMaxHealthScalar() {
            return maxHealth;
        }

        public boolean isValidFor(LivingEntity entity) {
            float maxHealth = entity.getMaxHealth();
            float currentHealth = entity.getHealth();
            float percentage = currentHealth / maxHealth;

            return percentage >= minHealth && percentage <= maxHealth;
        }

        public static Stage getValidStageFor(LivingEntity entity) {
            List<Stage> validStages = new ArrayList<>();

            for (Stage potentialStage : Stage.values()) {
                if(potentialStage.isValidFor(entity)) {
                    validStages.add(potentialStage);
                }
            }

            if(validStages.isEmpty()) {
                return NONE;
            } else {
                return validStages.get(entity.world.random.nextInt(validStages.size()));
            }
        }
    }
}
