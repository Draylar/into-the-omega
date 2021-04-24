package draylar.intotheomega.client.entity.model;

import draylar.intotheomega.entity.FrostedEyeEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class FrostedEyeModel extends EntityModel<FrostedEyeEntity> {

    private final ModelPart main;

    public FrostedEyeModel() {
        textureWidth = 128;
        textureHeight = 128;

        main = new ModelPart(this);
        main.setPivot(0.0F, 24.0F, 0.0F);
        main.setTextureOffset(0, 0).addCuboid(-12.0F, -24.0F, -12.0F, 24.0F, 24.0F, 24.0F, 0.0F, false);
    }

    @Override
    public void setAngles(FrostedEyeEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        main.pitch = headPitch / 360;
        main.yaw = entity.yaw / 360;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha){
        main.render(matrices, vertices, light, overlay);
    }
}