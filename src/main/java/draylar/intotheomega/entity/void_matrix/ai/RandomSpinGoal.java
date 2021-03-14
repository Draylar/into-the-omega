package draylar.intotheomega.entity.void_matrix.ai;

import draylar.intotheomega.entity.void_matrix.VoidMatrixEntity;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;

public class RandomSpinGoal extends StageGoal {

    private final int maxCooldown;
    private int currentCooldown;

    public RandomSpinGoal(VoidMatrixEntity entity, VoidMatrixEntity.Stage stage, int cooldown) {
        super(entity, stage);
        maxCooldown = cooldown;
        currentCooldown = cooldown;
    }

    @Override
    public void tick() {
        super.tick();

        if(currentCooldown <= 0) {
            // Trigger spin
            AnimationController<VoidMatrixEntity> controller = vm.getFactory().getOrCreateAnimationData(vm.hashCode()).getAnimationControllers().get("spin");

            if(controller.getAnimationState() == AnimationState.Stopped) {
                controller.markNeedsReload();
                controller.setAnimation(new AnimationBuilder().addAnimation("animation.void_matrix.rotate", false));
            }

            currentCooldown = maxCooldown;
        }

        currentCooldown--;
    }
}
