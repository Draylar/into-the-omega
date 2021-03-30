package draylar.intotheomega.entity.void_matrix.ai;

import draylar.intotheomega.entity.void_matrix.VoidMatrixEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;

import java.util.EnumSet;

public class StunnedGoal extends Goal {

    private static final int MAX_TICKS = 100;
    private final VoidMatrixEntity vm;
    private int ticks = 0;

    public StunnedGoal(VoidMatrixEntity vm) {
        this.vm = vm;
        setControls(EnumSet.of(Control.MOVE, Control.LOOK, Control.JUMP, Control.TARGET));
    }

    @Override
    public boolean canStart() {
        return vm.isStunned();
    }

    @Override
    public void start() {
        super.start();
        System.out.println("STUNNED");
        vm.world.playSoundFromEntity(null, vm, SoundEvents.BLOCK_BEACON_DEACTIVATE, SoundCategory.HOSTILE, 3.0f, 0.0f);
        ticks = 0;
    }

    @Override
    public boolean shouldContinue() {
        return ticks <= MAX_TICKS;
    }

    @Override
    public void tick() {
        super.tick();
        ticks++;
        vm.setVelocity(0, -.1, 0);
        vm.velocityModified = true;
        vm.velocityDirty = true;
    }

    @Override
    public void stop() {
        vm.stun(false);
        super.stop();
    }
}
