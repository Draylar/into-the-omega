package draylar.intotheomega.entity;

import draylar.intotheomega.entity.ai.TrueEyeLookControl;
import draylar.intotheomega.entity.ai.LookAtTargetGoal;
import draylar.intotheomega.entity.ai.FlyRandomlyGoal;
import draylar.intotheomega.entity.ai.TrueEyeMoveControl;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import software.bernie.geckolib.animation.builder.AnimationBuilder;
import software.bernie.geckolib.animation.controller.EntityAnimationController;
import software.bernie.geckolib.entity.IAnimatedEntity;
import software.bernie.geckolib.event.AnimationTestEvent;
import software.bernie.geckolib.manager.EntityAnimationManager;

public class TrueEyeOfEnderEntity extends PathAwareEntity implements IAnimatedEntity {

    private final EntityAnimationManager manager = new EntityAnimationManager();
    private final EntityAnimationController<TrueEyeOfEnderEntity> controller = new EntityAnimationController<>(this, "controller", 1, this::animationPredicate);
    private int customDeathTime = 0; // death time that does not trigger the fall over animation

    public TrueEyeOfEnderEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
        moveControl = new TrueEyeMoveControl(this);
        lookControl = new TrueEyeLookControl(this);

        registerAnimationControllers();
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new FlyRandomlyGoal(this));
        this.goalSelector.add(1, new LookAtTargetGoal(this));

        // targets
        this.targetSelector.add(1, new FollowTargetGoal<>(this, PlayerEntity.class, 10, true, false, (livingEntity) -> Math.abs(livingEntity.getY() - this.getY()) <= 4.0D));
    }

    @Override
    public void updatePostDeath() {
        ++this.customDeathTime;

        if (this.customDeathTime == 15) {
            for(int i = 0; i < 50; ++i) {
                double d = this.random.nextGaussian() * 0.02D;
                double e = this.random.nextGaussian() * 0.02D;
                double f = this.random.nextGaussian() * 0.02D;
                this.world.addParticle(ParticleTypes.PORTAL, this.getParticleX(1.0D), this.getY() + .15, this.getParticleZ(1.0D), d, e, f);
            }
        }

        if(this.customDeathTime >= 25) {
            this.remove();
        }
    }

    @Override
    public SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_ENDERMAN_DEATH;
    }

    @Override
    public float getSoundVolume() {
        return 0.5f;
    }

    @Override
    public float getSoundPitch() {
        return 5f;
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier) {
        return false;
    }

    @Override
    public void fall(double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPosition) {

    }

    @Override
    public void travel(Vec3d movementInput) {
        // Slow down in water
        if (this.isTouchingWater()) {
            this.updateVelocity(0.02F, movementInput);
            this.move(MovementType.SELF, this.getVelocity());
            this.setVelocity(this.getVelocity().multiply(0.800000011920929D));
        }

        // Slow down in lava
        else if (this.isInLava()) {
            this.updateVelocity(0.02F, movementInput);
            this.move(MovementType.SELF, this.getVelocity());
            this.setVelocity(this.getVelocity().multiply(0.5D));
        } else {
            float f = 0.91F;

            // Ground slipperiness
            if (this.onGround) {
                f = this.world.getBlockState(new BlockPos(this.getX(), this.getY() - 1.0D, this.getZ())).getBlock().getSlipperiness() * 0.91F;
            }

            float g = 0.16277137F / (f * f * f);
            f = 0.91F;
            if (this.onGround) {
                f = this.world.getBlockState(new BlockPos(this.getX(), this.getY() - 1.0D, this.getZ())).getBlock().getSlipperiness() * 0.91F;
            }

            // Move
            this.updateVelocity(this.onGround ? 0.1F * g : 0.02F, movementInput);
            this.move(MovementType.SELF, this.getVelocity());
            this.setVelocity(this.getVelocity().multiply(f));
        }
    }

    @Override
    public boolean isClimbing() {
        return false;
    }

    @Override
    public EntityAnimationManager getAnimationManager() {
        return manager;
    }

    private void registerAnimationControllers() {
        if(world.isClient) {
            controller.setAnimation(new AnimationBuilder().addAnimation("animation.true_eoe.idle"));
            manager.addAnimationController(controller);
        }
    }

    private <E extends Entity> boolean animationPredicate(AnimationTestEvent<E> event) {
        if (event.getEntity() instanceof LivingEntity) {
            LivingEntity living = (LivingEntity) event.getEntity();

            if(living.isDead()) {
                controller.transitionLengthTicks = 0;
                controller.setAnimation(new AnimationBuilder().addAnimation("animation.true_eoe.death"));
            }
        }

        return true;
    }
}
