package draylar.intotheomega.entity.ai;

import draylar.intotheomega.entity.TrueEyeOfEnderEntity;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class TrueEyeMoveControl extends MoveControl {

    private final TrueEyeOfEnderEntity eye;
    private int collisionCheckCooldown;

    public TrueEyeMoveControl(TrueEyeOfEnderEntity eye) {
        super(eye);
        this.eye = eye;
    }

    @Override
    public void tick() {
        // If we're moving to a new position
        if (this.state == MoveControl.State.MOVE_TO) {
            if (this.collisionCheckCooldown-- <= 0) {
                this.collisionCheckCooldown += this.eye.getRandom().nextInt(5) + 2;

                // get distance to the target location
                Vec3d vec3d = new Vec3d(this.targetX - this.eye.getX(), this.targetY - this.eye.getY(), this.targetZ - this.eye.getZ());
                double d = vec3d.length();
                vec3d = vec3d.normalize();

                // check if we're about to collide with a block
                if (this.willCollide(vec3d, MathHelper.ceil(d))) {
                    this.eye.setVelocity(this.eye.getVelocity().add(vec3d.multiply(0.1D)));
                    this.entity.lookAt(EntityAnchorArgumentType.EntityAnchor.EYES, new Vec3d(targetX, targetY, targetZ));
                } else {
                    this.state = MoveControl.State.WAIT;
                }
            }
        }
    }

    private boolean willCollide(Vec3d direction, int steps) {
        Box box = this.eye.getBoundingBox();

        for(int i = 1; i < steps; ++i) {
            box = box.offset(direction);
            if (!this.eye.world.isSpaceEmpty(this.eye, box)) {
                return false;
            }
        }

        return true;
    }
}