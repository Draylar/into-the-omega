package draylar.intotheomega.entity.movement;

import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class FloatingMoveControl extends MoveControl {

    private final MobEntity entity;
    private int collisionCheckCooldown;

    public FloatingMoveControl(MobEntity entity) {
        super(entity);
        this.entity = entity;
    }

    @Override
    public void tick() {
        if(state == MoveControl.State.MOVE_TO) {
            if(collisionCheckCooldown-- <= 0) {
                collisionCheckCooldown += entity.getRandom().nextInt(5) + 2;
                Vec3d toTarget = new Vec3d(targetX - entity.getX(), targetY - entity.getY(), targetZ - entity.getZ());
                double distanceToTarget = toTarget.length();
                toTarget = toTarget.normalize();

                // Float towards the target location.
                if(willNotCollide(toTarget, MathHelper.ceil(distanceToTarget))) {
                    entity.setVelocity(entity.getVelocity().add(toTarget.multiply(0.1D)));
                } else {
                    state = State.WAIT;
                }

                // Stop if we're done
                if(distanceToTarget <= 1) {
                    state = State.WAIT;
                }
            }
        }
    }

    private boolean willNotCollide(Vec3d direction, int steps) {
        Box box = entity.getBoundingBox();

        for (int i = 1; i < steps; ++i) {
            box = box.offset(direction);
            if(!this.entity.world.isSpaceEmpty(this.entity, box)) {
                return false;
            }
        }

        return true;
    }
}