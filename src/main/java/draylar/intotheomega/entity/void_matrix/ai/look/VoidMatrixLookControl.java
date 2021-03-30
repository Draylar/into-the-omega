package draylar.intotheomega.entity.void_matrix.ai.look;

import draylar.intotheomega.entity.ai.FreeLookControl;
import draylar.intotheomega.entity.void_matrix.VoidMatrixEntity;

public class VoidMatrixLookControl extends FreeLookControl {

    private final VoidMatrixEntity vm;

    public VoidMatrixLookControl(VoidMatrixEntity vm) {
        super(vm);
        this.vm = vm;
    }

    @Override
    public boolean shouldStayHorizontal() {
        return vm.getStage().equals(VoidMatrixEntity.Stage.ONE) || vm.isStunned() || vm.isFiringLaser();
    }
}
