package draylar.intotheomega.entity;

import draylar.intotheomega.entity.ai.FreeLookControl;
import draylar.intotheomega.entity.ai.LookAtTargetGoal;
import draylar.intotheomega.entity.ai.FlyRandomlyGoal;
import draylar.intotheomega.entity.ai.TrueEyeMoveControl;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class TrueEyeOfEnderEntity extends PathAwareEntity {

//    private final EntityAnimationManager manager = new EntityAnimationManager();
//    private final EntityAnimationController<TrueEyeOfEnderEntity> controller = new EntityAnimationController<>(this, "controller", 1, this::animationPredicate);
    private int customDeathTime = 0; // death time that does not trigger the fall over animation

    public TrueEyeOfEnderEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
        moveControl = new TrueEyeMoveControl(this);
        lookControl = new FreeLookControl(this);

//        registerAnimationControllers();
    }

    @Override
    protected void initGoals() {
        goalSelector.add(0, new FlyRandomlyGoal(this));
        goalSelector.add(1, new LookAtTargetGoal(this));

        // targets
        targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, 10, true, false, (livingEntity) -> Math.abs(livingEntity.getY() - this.getY()) <= 4.0D));
    }

    @Override
    public void updatePostDeath() {
        customDeathTime++;

        if (customDeathTime == 15) {
            for(int i = 0; i < 50; ++i) {
                double d = random.nextGaussian() * 0.02D;
                double e = random.nextGaussian() * 0.02D;
                double f = random.nextGaussian() * 0.02D;
                world.addParticle(ParticleTypes.PORTAL, getParticleX(1.0D), getY() + .15, getParticleZ(1.0D), d, e, f);
            }
        }

        if(customDeathTime >= 25) {
            discard();
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
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
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

//    @Override
//    public EntityAnimationManager getAnimationManager() {
//        return manager;
//    }
//
//    private void registerAnimationControllers() {
//        if(world.isClient) {
//            controller.setAnimation(new AnimationBuilder().addAnimation("animation.true_eoe.idle"));
//            manager.addAnimationController(controller);
//        }
//    }
//
//    private <E extends Entity> boolean animationPredicate(AnimationTestEvent<E> event) {
//        if (event.getEntity() instanceof LivingEntity) {
//            LivingEntity living = (LivingEntity) event.getEntity();
//
//            if(living.isDead()) {
//                controller.transitionLengthTicks = 0;
//                controller.setAnimation(new AnimationBuilder().addAnimation("animation.true_eoe.death"));
//            }
//        }
//
//        return true;
//    }
}
