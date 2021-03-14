package draylar.intotheomega.entity.void_matrix;

import draylar.intotheomega.api.HomeEntity;
import draylar.intotheomega.entity.matrite.MatriteEntity;
import draylar.intotheomega.entity.void_matrix.ai.*;
import draylar.intotheomega.entity.void_matrix.ai.look.VoidMatrixLookControl;
import draylar.intotheomega.entity.void_matrix.ai.movement.VoidMatrixMoveControl;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
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
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
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
 * The Void Matrix has 3 primary attack stages:
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
 *
 *     Stage 2 <=> 400 - 200 HP
 *          In Stage 2, the Void Matrix summons charged particle attacks from the sky. These are telegraphed by large vertical lines of particles that last for 3 seconds.
 *          After charging, the attacks will pause for 1 second, then deal large damage to any entity standing underneath it (besides the Void Matrix).
 *          As the Void Matrix gets closer to 200 HP, more attacks will spawn.
 *
 *          In this stage, the Void Matrix takes 10% from physical attacks and 75% from ranged attacks.
 *          The player will need to attack from afar and shoot the Void Matrix while dodging projectiles to progress to the next stage.
 *
 *     Stage 3 <=> 200 - 0 HP
 *          In Stage 3, the final stage, the Void Matrix will enter physical mode and chase after the player, sliding and stomping to reach its destination.
 *          If the Void Matrix misses a stomp, it will be stunned for 3 seconds.
 *          As the Void Matrix reaches 0 HP, it will attack faster and more frequently.
 *
 *          All attacks do normal damage in this stage.
 *          The player will need to wait for the Void Matrix to stun itself to deal melee damage, or pelt it from afar with ranged attacks.
 *          At random phases in the 3rd stage, the Void Matrix will pause, and then fire a large array of projectiles at the player.
 *
 *
 *  If all nearby players leave the battle or are killed, the Void Matrix will heal back to full and enter a dormant state at its altar.
 *  The Void Matrix restores 100 health when killing a player, but will not revert to a previous phase unless all players leave the area.
 *
 *  Drops:
 *      - Matrix Rune - done
 *      - Experience
 *      - Matrix Bombs
 *      - End Crystals
 *      - Omega Fragments
 */
public class VoidMatrixEntity extends FlyingEntity implements IAnimatable, HomeEntity {

    private final AnimationFactory factory = new AnimationFactory(this);;
    private final AnimationController<VoidMatrixEntity> spinController = new AnimationController<>(this, "spin", 20, this::refresh);

    private final ServerBossBar bossBar;
    private BlockPos home = null;
    private Stage stage = Stage.ONE;

    public VoidMatrixEntity(EntityType<? extends VoidMatrixEntity> entityType, World world) {
        super(entityType, world);
        moveControl = new VoidMatrixMoveControl(this);
        lookControl = new VoidMatrixLookControl(this);
        bossBar = (ServerBossBar) new ServerBossBar(this.getDisplayName(), BossBar.Color.PURPLE, BossBar.Style.PROGRESS).setDarkenSky(true);
    }

    @Override
    public void initGoals() {
        // Always look at nearby players
        goalSelector.add(0, new LookAtTargetGoal(this));

        // Things to do when the boss is alone
        goalSelector.add(0, new HealWhenAloneGoal(this));
        goalSelector.add(0, new TeleportHomeWhenAloneGoal(this));

        // Stage 1: stay at center to assert dominance  and shoot floating rocks, small chance to spin
        goalSelector.add(1, new MatriteProjectileAttackGoal(this, Stage.ONE));

        // Stage 2: aggressively float and shoot giant lasers from the sky
        goalSelector.add(1, new AggressivelyFloatGoal(this, Stage.TWO));
        goalSelector.add(2, new RandomSpinGoal(this, Stage.TWO, 20 * 7));
        goalSelector.add(2, new BeamAttackGoal(this, Stage.TWO));

        // Stage 3: chase player while shooting lasers and floating rocks at them
        goalSelector.add(1, new AggressivelyFloatGoal(this, Stage.THREE));
        goalSelector.add(2, new RandomSpinGoal(this, Stage.THREE, 20 * 3));
        goalSelector.add(2, new MatriteProjectileAttackGoal(this, Stage.THREE));
        goalSelector.add(2, new BeamAttackGoal(this, Stage.THREE));

        // Idle when no other movement goals are running
        goalSelector.add(10, new IdleGoal(this));

        // Always target nearby player
        targetSelector.add(1, new FollowTargetGoal<>(this, PlayerEntity.class, 0, false, false, (entity) -> true));
    }

    @Override
    public void tick() {
        super.tick();

        if(!world.isClient) {
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
        if(stage == Stage.ONE) {
            if(!isOnGround()) {
                this.updateVelocity(0.03f, new Vec3d(0, -1, 0));
                this.velocityDirty = true;
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
    public void registerControllers(AnimationData data) {
        data.addAnimationController(spinController);
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    private <P extends IAnimatable> PlayState refresh(AnimationEvent<P> event) {
//        spinController.setAnimation(new AnimationBuilder().addAnimation("animation.void_matrix.rotate", false));
        return PlayState.CONTINUE;
    }

    public AnimationController<VoidMatrixEntity> getSpinController() {
        return spinController;
    }

    @Override
    public BlockPos getHome() {
        return home;
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

    public enum Stage {
        NONE(1, 1, -Integer.MAX_VALUE, -Integer.MAX_VALUE),
        ONE(.5f, .75f, 1, 2 / 3f),
        TWO(.75f, .1f, 2 / 3f, 1 / 3f),
        THREE(1f, 1f, 1 / 3f, 0);

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
