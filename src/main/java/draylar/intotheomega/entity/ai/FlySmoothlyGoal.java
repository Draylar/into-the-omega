package draylar.intotheomega.entity.ai;

import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.EnumSet;
import java.util.Random;

public class FlySmoothlyGoal extends Goal {

    private final MobEntity entity;
    private final float speed;
    private final float xVariance;
    private final float yVariance;
    private final float zVariance;

    private BlockPos target;

    public FlySmoothlyGoal(MobEntity entity) {
        this.entity = entity;
        this.setControls(EnumSet.of(Control.MOVE));
        speed = 1.0f;
        xVariance = 16f;
        yVariance = 16f;
        zVariance = 16f;
    }

    public FlySmoothlyGoal(MobEntity entity, float speed, float xVariance, float yVariance, float zVariance) {
        this.entity = entity;
        this.speed = speed;
        this.xVariance = xVariance;
        this.yVariance = yVariance;
        this.zVariance = zVariance;
        this.setControls(EnumSet.of(Control.MOVE));
    }

    @Override
    public boolean canStart() {
        MoveControl moveControl = this.entity.getMoveControl();

        if (!moveControl.isMoving()) {
            return true;
        } else {
            double distanceX = moveControl.getTargetX() - this.entity.getX();
            double distanceY = moveControl.getTargetY() - this.entity.getY();
            double distanceZ = moveControl.getTargetZ() - this.entity.getZ();

            double squaredDistance = distanceX * distanceX + distanceY * distanceY + distanceZ * distanceZ;
            return squaredDistance < 1.0D || squaredDistance > 3600.0D;
        }
    }

    @Override
    public boolean shouldContinue() {
        MoveControl moveControl = this.entity.getMoveControl();

        double distanceX = moveControl.getTargetX() - this.entity.getX();
        double distanceY = moveControl.getTargetY() - this.entity.getY();
        double distanceZ = moveControl.getTargetZ() - this.entity.getZ();

        double squaredDistance = distanceX * distanceX + distanceY * distanceY + distanceZ * distanceZ;
        return squaredDistance > 1.0D || squaredDistance > 3600.0D;
    }

    @Override
    public void start() {
        Random random = this.entity.getRandom();

        double nextX = this.entity.getX() + (double) ((random.nextFloat() * 2.0F - 1.0F) * xVariance);
        double nextY = this.entity.getY() + (double) ((random.nextFloat() * 2.0F - 1.0F) * yVariance);
        double nextZ = this.entity.getZ() + (double) ((random.nextFloat() * 2.0F - 1.0F) * zVariance);

        this.entity.getMoveControl().moveTo(nextX, nextY, nextZ, speed);
    }
}