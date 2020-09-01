package draylar.intotheomega.entity.ai;

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
        if (this.entity.getTarget() == null) {
            Vec3d vec3d = this.entity.getVelocity();
            this.entity.yaw = -((float) MathHelper.atan2(vec3d.x, vec3d.z)) * 57.295776F;
            this.entity.bodyYaw = this.entity.yaw;
        } else {
            LivingEntity livingEntity = this.entity.getTarget();

            // check if the target entity is within a reasonable distance, then look towards them
            if (livingEntity.squaredDistanceTo(this.entity) < 4096.0D) {
                double e = livingEntity.getX() - this.entity.getX();
                double f = livingEntity.getZ() - this.entity.getZ();
                this.entity.yaw = -((float) MathHelper.atan2(e, f)) * 57.295776F;
                this.entity.bodyYaw = this.entity.yaw;
            }
        }
    }
}
