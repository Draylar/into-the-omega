package draylar.intotheomega.entity.void_matrix.ai;

import draylar.intotheomega.entity.void_matrix.VoidMatrixEntity;
import draylar.intotheomega.mixin.MoveControlAccessor;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.server.world.ServerWorld;

import java.util.EnumSet;

public class IdleGoal extends Goal {

    private final VoidMatrixEntity vm;
    private final ServerWorld world;

    public IdleGoal(VoidMatrixEntity vm) {
        this.vm = vm;
        this.world = (ServerWorld) vm.world;
        this.setControls(EnumSet.of(Goal.Control.MOVE));
    }

    @Override
    public boolean canStart() {
        return true;
    }

    @Override
    public void start() {
        ((MoveControlAccessor) vm.getMoveControl()).setState(MoveControl.State.WAIT);
    }

    @Override
    public boolean shouldContinue() {
        return false;
    }
}
