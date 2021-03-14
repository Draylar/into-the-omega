package draylar.intotheomega.client.entity.model;

import draylar.intotheomega.entity.OmegaSlimeEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class OmegaSlimeModel extends EntityModel<OmegaSlimeEntity> {

    private final ModelPart bb_main;

    public OmegaSlimeModel() {
        textureWidth = 64;
        textureHeight = 64;

        bb_main = new ModelPart(this);
        bb_main.setPivot(0.0F, 24.0F, 0.0F);
        bb_main.setTextureOffset(0, 0).addCuboid(-8.0F, -16.0F, -7.0F, 16.0F, 16.0F, 16.0F, 0.0F, false);
        bb_main.setTextureOffset(0, 32).addCuboid(-6.0F, -14.0F, -5.0F, 12.0F, 12.0F, 12.0F, 0.0F, false);
        bb_main.setTextureOffset(0, 5).addCuboid(-6.5F, -12.0F, -5.5F, 4.0F, 4.0F, 1.0F, 0.0F, false);
        bb_main.setTextureOffset(0, 0).addCuboid(2.5F, -12.0F, -5.5F, 4.0F, 4.0F, 1.0F, 0.0F, false);
        bb_main.setTextureOffset(9, 9).addCuboid(0.5F, -6.0F, -5.5F, 2.0F, 2.0F, 1.0F, 0.0F, false);
    }

    @Override
    public void setAngles(OmegaSlimeEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha){
        bb_main.render(matrices, vertices, light, overlay);
    }
}
