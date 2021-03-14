package draylar.intotheomega.client.entity.model;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

public abstract class MiniatureCrystalEntityModel<T extends Entity> extends EntityModel<T> {

    public final ModelPart outer;
    public final ModelPart inner;

    public MiniatureCrystalEntityModel() {
        textureWidth = 32;
        textureHeight = 32;

        outer = new ModelPart(this);
        outer.setPivot(0.0F, 20.0F, 0.0F);
        outer.setTextureOffset(0, 0).addCuboid(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);

        inner = new ModelPart(this);
        inner.setPivot(0.0F, 20.0F, 0.0F);
        inner.setTextureOffset(0, 16).addCuboid(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F, 0.0F, false);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        outer.render(matrixStack, buffer, packedLight, packedOverlay);
        inner.render(matrixStack, buffer, packedLight, packedOverlay);
    }
}