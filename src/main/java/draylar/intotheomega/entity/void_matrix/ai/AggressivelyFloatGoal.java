package draylar.intotheomega.entity.void_matrix.ai;

import draylar.intotheomega.entity.void_matrix.VoidMatrixEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.Goal;

import java.util.EnumSet;
import java.util.Random;

public class AggressivelyFloatGoal extends StageGoal {

    private final float speed;

    public AggressivelyFloatGoal(VoidMatrixEntity entity, VoidMatrixEntity.Stage stage, float speed) {
        super(entity, stage);
        this.speed = speed;
        this.setControls(EnumSet.of(Goal.Control.MOVE));
    }

    @Override
    public boolean canStart() {
        if(!super.canStart()) {
            return false;
        }

        MoveControl moveControl = this.vm.getMoveControl();

        if (!moveControl.isMoving()) {
            return true;
        } else if (vm.getTarget() == null) {
            return false;
        } else {
            double d = moveControl.getTargetX() - this.vm.getX();
            double e = moveControl.getTargetY() - this.vm.getY();
            double f = moveControl.getTargetZ() - this.vm.getZ();
            double g = d * d + e * e + f * f;
            return g < 1.0D || g > 3600.0D;
        }
    }

    @Override
    public boolean shouldContinue() {
        return false;
    }

    @Override
    public void start() {
        LivingEntity target = vm.getTarget();
        Random random = this.vm.getRandom();

        if(target != null) {
            double destinationX = target.getX() + (double) ((random.nextFloat() * 2.0F - 1.0F) * 16);
            double destinationY = target.getY() + (double) ((random.nextFloat()) * 12.0F + 2);
            double destinationZ = target.getZ() + (double) ((random.nextFloat() * 2.0F - 1.0F) * 16);

            this.vm.getMoveControl().moveTo(destinationX, destinationY, destinationZ, speed);
        }
    }
}
