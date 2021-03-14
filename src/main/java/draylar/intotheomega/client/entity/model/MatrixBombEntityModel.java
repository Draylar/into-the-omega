package draylar.intotheomega.client.entity.model;

import draylar.intotheomega.entity.MatrixBombEntity;

public class MatrixBombEntityModel extends MiniatureCrystalEntityModel<MatrixBombEntity> {

    @Override
    public void setAngles(MatrixBombEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        outer.yaw = (float) Math.cos(animationProgress);
        outer.pitch = (float) Math.sin(animationProgress);

        inner.pitch = (float) -Math.tan(animationProgress);
        inner.roll = (float) Math.cos(animationProgress);
    }
}
