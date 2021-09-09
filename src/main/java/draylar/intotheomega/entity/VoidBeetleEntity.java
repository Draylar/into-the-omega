package draylar.intotheomega.entity;

import draylar.intotheomega.entity.ai.FlyRandomlyGoal;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

/**
 * The Void Beetle is a generic end entity which often appears around obsidian.
 *
 */
public class VoidBeetleEntity extends PathAwareEntity implements IAnimatable {

    private static final String FLIGHT_ANIMATION = "flight";
    private static final String IDLE_ANIMATION = "idle";
    private static final String WALK_ANIMATION = "walk";
    private static final String WARN_ANIMATION = "warn";

    private final AnimationFactory factory = new AnimationFactory(this);

    public VoidBeetleEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
        moveControl = new FlightMoveControl(this, 10, false);
    }

    @Override
    public void initGoals() {
        goalSelector.add(0, new FlyRandomlyGoal(this));
        targetSelector.add(0, new RevengeGoal(this).setGroupRevenge());
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "main", 10, this::main));
        data.addAnimationController(new AnimationController<>(this, "warning", 10, this::warning));
    }

    private <P extends IAnimatable> PlayState main(AnimationEvent<P> event) {
        if(!onGround) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(FLIGHT_ANIMATION, true));
        } else if(event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(WALK_ANIMATION, true));
        } else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(IDLE_ANIMATION, true));
        }

        return PlayState.CONTINUE;
    }


    private <P extends IAnimatable> PlayState warning(AnimationEvent<P> event) {
        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    public static DefaultAttributeContainer.Builder createBeetleAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE)
                .add(EntityAttributes.GENERIC_FLYING_SPEED, 1.0f);
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier) {
        return false;
    }

    @Override
    public void fall(double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPosition) {

    }
}
