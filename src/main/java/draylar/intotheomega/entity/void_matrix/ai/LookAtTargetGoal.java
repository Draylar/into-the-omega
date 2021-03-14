package draylar.intotheomega.entity.void_matrix.ai;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.EnumSet;

public class LookAtTargetGoal extends Goal {

    private final MobEntity entity;

    public LookAtTargetGoal(MobEntity entity) {
        this.entity = entity;
        this.setControls(EnumSet.of(Goal.Control.LOOK));
    }

    @Override
    public boolean canStart() {
        return true;
    }

    @Override
    public void tick() {
        // If the entity's target is null, look towards the current movement destination.
        if (this.entity.getTarget() == null) {
            Vec3d vec3d = this.entity.getVelocity();

            if(vec3d.y != 0 && vec3d.z != 0) {
                this.entity.yaw = -((float) MathHelper.atan2(vec3d.x, vec3d.z)) * 57.295776F;
                this.entity.bodyYaw = this.entity.yaw;
            } else {
                this.entity.pitch = 0;
            }
        }

        // If the entity's target is valid, look towards it.
        else {
            LivingEntity target = this.entity.getTarget();
            double d = 64.0D;

            if (target.squaredDistanceTo(this.entity) < d * d) {
                double e = target.getX() - this.entity.getX();
                double f = target.getZ() - this.entity.getZ();

                // Calculate yaw
                this.entity.yaw = -((float) MathHelper.atan2(e, f)) * 57.295776F;
                this.entity.bodyYaw = this.entity.yaw;


                entity.lookAtEntity(target, 1000, 1000);
            }
        }
    }
}