package draylar.intotheomega.entity.enigma;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
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

public class EnigmaKingEntity extends PathAwareEntity implements IAnimatable {

    public static final String IDLE_ANIMATION = "animation.enigma_king.idle";
    public static final String SMASH_ANIMATION = "animation.enigma_king.smash";
    public static final String ATTACK_ANIMATION = "animation.enigma_king.slash";
    public static final String WALK_ANIMATION = "animation.enigma_king.walk";

    private static final TrackedData<String> ACTIVITY = DataTracker.registerData(EnigmaKingEntity.class, TrackedDataHandlerRegistry.STRING);
    public static final String ACTIVITY_SMASHING = "Smashing";
    public static final String ACTIVITY_ATTACKING = "Attacking";
    public static final String ACTIVITY_NONE = "";

    private final AnimationFactory factory = new AnimationFactory(this);
    private final AnimationController<EnigmaKingEntity> core = new AnimationController<>(this, "controller", 20, this::predicate);
    private final ServerBossBar bossBar;
    private BlockPos origin;

    public EnigmaKingEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
        this.bossBar = (ServerBossBar) new ServerBossBar(this.getDisplayName(), BossBar.Color.PURPLE, BossBar.Style.PROGRESS).setDarkenSky(true);
    }

    @Nullable
    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityTag) {
        this.origin = getBlockPos();
        return super.initialize(world, difficulty, spawnReason, entityData, entityTag);
    }

    @Override
    public void initGoals() {
//        this.goalSelector.add(2, new EnigmaKingSmashGoal(this));
//        this.goalSelector.add(2, new EnigmaKingThornAttackGoal(this));
        this.goalSelector.add(2, new EnigmaKingAttackGoal(this));
//        this.goalSelector.add(5, new WanderAroundFarGoal(this, 1, 1));
//        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
//        this.goalSelector.add(8, new LookAroundGoal(this));

//        this.targetSelector.add(0, new ActiveTargetGoal<>(this, PigEntity.class, true));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
    }

    @Override
    public void tick() {
        super.tick();

        this.bossBar.setPercent(this.getHealth() / this.getMaxHealth());
    }

    // S2C DATA-SYNCING AND GENERAL STATE MANAGEMENT ----------------------------------------------------------------------

    @Override
    public void initDataTracker() {
        super.initDataTracker();
        dataTracker.startTracking(ACTIVITY, "");
    }

    public void updateActivity(String activity) {
        dataTracker.set(ACTIVITY, activity);
    }

    // GECKOLIB ANIMATION ----------------------------------------------------------------------

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(core);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        float limbSwingAmount = event.getLimbSwingAmount();
        boolean isMoving = !(limbSwingAmount > -0.05F && limbSwingAmount < 0.05F);

        if (dataTracker.get(ACTIVITY).equals(ACTIVITY_ATTACKING)) {
            core.setAnimation(new AnimationBuilder().addAnimation("animation.enigma_king.slash", false));
        } else if (dataTracker.get(ACTIVITY).equals(ACTIVITY_SMASHING)) {
            core.setAnimation(new AnimationBuilder().addAnimation("animation.enigma_king.smash", false));
        } else if (isMoving) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.enigma_king.walk", true));
        } else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.enigma_king.idle", true));
        }

//        core.setAnimation(new AnimationBuilder().addAnimation("animation.enigma_king.slash", true));


//        if (dataTracker.get(ACTIVITY).equals(ACTIVITY_SMASHING)) {
//            core.setAnimation(new AnimationBuilder().addAnimation(SMASH_ANIMATION, false));
//        } else if (dataTracker.get(ACTIVITY).equals(ACTIVITY_ATTACKING)) {
//            core.setAnimation(new AnimationBuilder().addAnimation(ATTACK_ANIMATION, false));
//        }

//        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.enigma_king.smash", false));

//        if(event.getLimbSwingAmount() < -.15f || event.getLimbSwingAmount() > 0.15f) {
////            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.enigma_king.walk", true));
//        } else {
//        else {
//            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.enigma_king.idle", true));
//        }
//        }


//        core.setAnimation(new AnimationBuilder().addAnimation("animation.enigma_king.smash", true));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    public AnimationController<EnigmaKingEntity> getCoreAnimationController() {
        return core;
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 64.0D)
                .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 10)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 600)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 1);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound tag) {
        super.writeCustomDataToNbt(tag);
        tag.putFloat("HeadYaw", this.headYaw);
        tag.putLong("OriginPos", this.origin.asLong());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound tag) {
        super.readCustomDataFromNbt(tag);
        this.headYaw = tag.getFloat("HeadYaw");
        this.origin = BlockPos.fromLong(tag.getLong("OriginPos"));
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

    public BlockPos getOrigin() {
        return origin;
    }
}
