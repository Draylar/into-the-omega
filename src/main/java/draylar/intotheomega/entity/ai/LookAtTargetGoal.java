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
        setControls(EnumSet.of(Goal.Control.LOOK));
    }

    @Override
    public boolean canStart() {
        return true;
    }

    @Override
    public void tick() {
        if (entity.getTarget() == null) {
            Vec3d vec3d = entity.getVelocity();
            entity.setYaw(-((float) MathHelper.atan2(vec3d.x, vec3d.z)) * 57.295776F);
            entity.bodyYaw = entity.getYaw();
        } else {
            LivingEntity livingEntity = entity.getTarget();

            // check if the target entity is within a reasonable distance, then look towards them
            if (livingEntity.squaredDistanceTo(this.entity) < 4096.0D) {
                double e = livingEntity.getX() - entity.getX();
                double f = livingEntity.getZ() - entity.getZ();
                entity.setYaw(-((float) MathHelper.atan2(e, f)) * 57.295776F);
                entity.bodyYaw = entity.getYaw();
            }
        }
    }
}
