package draylar.intotheomega.client.entity.model;

import draylar.intotheomega.entity.matrite.MatriteEntity;

public class MatriteEntityModel extends MiniatureCrystalEntityModel<MatriteEntity> {

    @Override
    public void setAngles(MatriteEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        outer.yaw = (float) Math.cos(animationProgress);
        outer.pitch = (float) Math.sin(animationProgress);

        inner.pitch = (float) -Math.tan(animationProgress);
        inner.roll = (float) Math.cos(animationProgress);
    }
}