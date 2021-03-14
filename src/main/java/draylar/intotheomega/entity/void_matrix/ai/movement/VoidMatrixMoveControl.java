package draylar.intotheomega.entity.void_matrix.ai.movement;

import draylar.intotheomega.entity.void_matrix.VoidMatrixEntity;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class VoidMatrixMoveControl extends MoveControl {

    private final VoidMatrixEntity vm;
    private int collisionCheckCooldown;

    public VoidMatrixMoveControl(VoidMatrixEntity vm) {
        super(vm);
        this.vm = vm;
    }

    @Override
    public void tick() {
        if (this.state == MoveControl.State.MOVE_TO) {
            if (this.collisionCheckCooldown-- <= 0) {
                this.collisionCheckCooldown += this.vm.getRandom().nextInt(5) + 2;

                // Calculate the distance from this entity to the entity's target position,
                //    then calculate movement director from the difference
                Vec3d distanceToTarget = new Vec3d(this.targetX - this.vm.getX(), this.targetY - this.vm.getY(), this.targetZ - this.vm.getZ());
                double distance = distanceToTarget.length();
                distanceToTarget = distanceToTarget.normalize();

                if (this.willCollide(distanceToTarget, MathHelper.ceil(distance))) {
                    this.vm.setVelocity(this.vm.getVelocity().add(distanceToTarget.multiply(0.1D)));
                } else {
                    this.state = MoveControl.State.WAIT;
                }
            }

        }
    }

    private boolean willCollide(Vec3d direction, int steps) {
        Box box = this.vm.getBoundingBox();

        for(int i = 1; i < steps; ++i) {
            box = box.offset(direction);

            if (!this.vm.world.isSpaceEmpty(this.vm, box)) {
                return false;
            }
        }

        return true;
    }
}