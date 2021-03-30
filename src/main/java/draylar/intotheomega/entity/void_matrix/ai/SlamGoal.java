package draylar.intotheomega.entity.void_matrix.ai;

import draylar.intotheomega.entity.void_matrix.VoidMatrixEntity;
import net.minecraft.util.math.Vec3d;

import java.util.EnumSet;

public class SlamGoal extends StageGoal {

    private static final int MAX_TICKS = 100;
    private int ticks = 0;
    private Vec3d position = null;
    private long lastSlam = 0;

    public SlamGoal(VoidMatrixEntity vm, VoidMatrixEntity.Stage stage) {
        super(vm, stage);
        setControls(EnumSet.of(Control.MOVE));
    }

    @Override
    public boolean canStart() {
        return super.canStart() && world.random.nextInt(100) == 0 && vm.getTarget() != null && System.currentTimeMillis() - lastSlam > 10_000;
    }

    @Override
    public void start() {
        assert vm.getTarget() != null;

        super.start();
        ticks = 0;
        world.sendEntityStatus(vm, VoidMatrixEntity.QUICK_SINGLE_SPIN);
        vm.setSlamming(true);
        position = vm.getTarget().getPos();
    }

    @Override
    public boolean shouldContinue() {
        return ticks <= MAX_TICKS;
    }

    @Override
    public void stop() {
        super.stop();

        // don't make vm go flying into the horizon
        vm.setVelocity(0, 0, 0);
        vm.velocityDirty = true;
        vm.velocityModified = true;
        lastSlam = System.currentTimeMillis();
    }

    @Override
    public void tick() {
        super.tick();
        ticks++;

        // extra slam detection, sometimes block/entity collision does not catch it
        if(vm.isSlamming() && !world.isClient && vm.isOnGround()) {
            vm.slam();
        }

        if(ticks > 40 && vm.getTarget() != null && !vm.isStunned() && !vm.isOnGround()) {
            world.sendEntityStatus(vm, VoidMatrixEntity.STOP);
            Vec3d dist = vm.getPos().subtract(position).normalize().multiply(-2);
            vm.setVelocity(dist.getX(), dist.getY(), dist.getZ());
            vm.velocityDirty = true;
            vm.velocityModified = true;
        }
    }
}
