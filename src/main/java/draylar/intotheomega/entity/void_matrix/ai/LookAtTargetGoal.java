package draylar.intotheomega.entity.void_matrix.ai;

import draylar.intotheomega.entity.void_matrix.VoidMatrixEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.EnumSet;

public class LookAtTargetGoal extends Goal {

    private final VoidMatrixEntity vm;

    public LookAtTargetGoal(VoidMatrixEntity vm) {
        this.vm = vm;
        this.setControls(EnumSet.of(Goal.Control.LOOK));
    }

    @Override
    public boolean canStart() {
        return true;
    }

    @Override
    public void tick() {
        // If the entity's target is null, look towards the current movement destination.
        if (this.vm.getTarget() == null) {
            Vec3d vec3d = this.vm.getVelocity();

            if(vec3d.y != 0 && vec3d.z != 0) {
                this.vm.yaw = -((float) MathHelper.atan2(vec3d.x, vec3d.z)) * 57.295776F;
                this.vm.bodyYaw = this.vm.yaw;
            } else {
                this.vm.pitch = 0;
            }
        }

        // If the entity's target is valid, look towards it.
        else {
            LivingEntity target = this.vm.getTarget();
            double d = 64.0D;

            if (target.squaredDistanceTo(this.vm) < d * d) {
                double diffX = target.getX() - this.vm.getX();
                double diffZ = target.getZ() - this.vm.getZ();

                boolean slow = vm.isFiringLaser();

                // Calculate yaw
//                this.vm.yaw = -((float) MathHelper.atan2(diffX, diffZ)) * 57.295776F;
                this.vm.bodyYaw = this.vm.yaw;

                int max = slow ? 1 : 1000;
                vm.lookAtEntity(target, max, max);
            }
        }
    }
}