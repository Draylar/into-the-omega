package draylar.intotheomega.entity.void_matrix.ai.look;

import draylar.intotheomega.entity.ai.FreeLookControl;
import draylar.intotheomega.entity.void_matrix.VoidMatrixEntity;

public class VoidMatrixLookControl extends FreeLookControl {

    private final VoidMatrixEntity entity;

    public VoidMatrixLookControl(VoidMatrixEntity entity) {
        super(entity);
        this.entity = entity;
    }

    @Override
    public boolean shouldStayHorizontal() {
        return entity.getStage().equals(VoidMatrixEntity.Stage.ONE);
    }
}
