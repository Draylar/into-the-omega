package draylar.intotheomega.entity.slime;

import draylar.intotheomega.entity.slime.ai.OmegaSlimeEmperorMoveGoal;
import draylar.intotheomega.registry.OmegaParticles;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class OmegaSlimeEmperorEntity extends SlimeEntity implements IAnimatable {

    private static final String IDLE = "animation.omega_slime_emperor.idle";
    private static final String SQUISH = "animation.omega_slime_emperor.squish";
    private static final Map<String, Byte> registeredAnimations = new HashMap<>();

    private final ServerBossBar bossBar;
    private final Queue<String> queuedAnimations = new ArrayDeque<>();
    private final AnimationFactory factory = new AnimationFactory(this);
    private String currentQueuedAnimation = null;

    static {
        registeredAnimations.put(IDLE, (byte) 100);
        registeredAnimations.put(SQUISH, (byte) 101);
    }

    public OmegaSlimeEmperorEntity(EntityType<? extends SlimeEntity> type, World world) {
        super(type, world);
        this.bossBar = (ServerBossBar) new ServerBossBar(this.getDisplayName(), BossBar.Color.PURPLE, BossBar.Style.PROGRESS).setDarkenSky(true);
    }

    @Override
    public void initGoals() {
        this.goalSelector.add(1, new SlimeEntity.SwimmingGoal(this));
        this.goalSelector.add(2, new SlimeEntity.FaceTowardTargetGoal(this));
        this.goalSelector.add(3, new SlimeEntity.RandomLookGoal(this));
        this.goalSelector.add(5, new OmegaSlimeEmperorMoveGoal(this));
        this.targetSelector.add(0, new ActiveTargetGoal<>(this, PlayerEntity.class, 10, true, false, living -> Math.abs(living.getY() - this.getY()) <= 4.0D));
    }

    @Override
    public void tick() {
        super.tick();

        if(!world.isClient) {
            bossBar.setPercent(getHealth() / getMaxHealth());
        }
    }

    @Override
    public ParticleEffect getParticles() {
        return OmegaParticles.OMEGA_SLIME;
    }

    @Override
    public int getTicksUntilNextJump() {
        return super.getTicksUntilNextJump() * 3;
    }

    @Override
    public void fall(double heightDifference, boolean onGround, BlockState state, BlockPos pos) {
        super.fall(heightDifference, onGround, state, pos);
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        if(!world.isClient && fallDistance > 1 && onGround) {
            sendS2CAnimation(SQUISH);

            // spam slime particles around the boss
            ((ServerWorld) world).spawnParticles(OmegaParticles.OMEGA_SLIME, getX(), getY() + 0.25, getZ(), 100, 3, 0, 3, 0);
        }

        return super.handleFallDamage(fallDistance, damageMultiplier, damageSource);
    }

    public void sendS2CAnimation(String animation) {
        byte id = registeredAnimations.get(animation);
        world.sendEntityStatus(this, id);
    }

    @Override
    public void handleStatus(byte status) {
        super.handleStatus(status);

        registeredAnimations.forEach((name, id) -> {
            if(id == status) {
                queueAnimation(name);
            }
        });
    }

    @Environment(EnvType.CLIENT)
    public void queueAnimation(String animation) {
        queuedAnimations.add(animation);
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "main", 5, this::main));
    }

    private <P extends IAnimatable> PlayState main(AnimationEvent<P> event) {
        // If an animation has been queued up, run it...
        if(!queuedAnimations.isEmpty()) {
            String queued = queuedAnimations.poll();
            currentQueuedAnimation = queued;
            event.getController().setAnimation(new AnimationBuilder().addAnimation(queued));
            event.getController().markNeedsReload();
            return PlayState.CONTINUE;
        }

        // If an animation is currently running, wait for it to finish, and stop it afterwards.
        if(currentQueuedAnimation != null) {
            if (event.getController().getAnimationState().equals(AnimationState.Stopped)) {
                currentQueuedAnimation = null;
                event.getController().clearAnimationCache();
                return PlayState.CONTINUE;
            }

            return PlayState.CONTINUE;
        }

        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    public void onStartedTrackingBy(ServerPlayerEntity player) {
        super.onStartedTrackingBy(player);
        bossBar.addPlayer(player);
    }

    @Override
    public void onStoppedTrackingBy(ServerPlayerEntity player) {
        super.onStoppedTrackingBy(player);
        bossBar.removePlayer(player);
    }

    public static DefaultAttributeContainer.Builder createEmperorAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 64.0D)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 1000.0D)
                .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK);
    }

    // prevent slime from splitting up on death
    @Override
    public void remove(RemovalReason reason) {
        setRemoved(reason);
        if (reason == RemovalReason.KILLED) {
            emitGameEvent(GameEvent.ENTITY_KILLED);
        }
    }
}
