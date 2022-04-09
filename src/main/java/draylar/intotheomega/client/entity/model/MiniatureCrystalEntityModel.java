package draylar.intotheomega.client.entity.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

public class MiniatureCrystalEntityModel<T extends Entity> extends EntityModel<T> {

    public final ModelPart outer;
    public final ModelPart inner;

    public MiniatureCrystalEntityModel(ModelPart root) {
        outer = root.getChild("outer");
        inner = root.getChild("inner");
    }

    public static TexturedModelData createTexturedModelData() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        root.addChild("outer", ModelPartBuilder.create().uv(0, 0)
                        .cuboid(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F)),
                ModelTransform.pivot(0.0F, 20.0F, 0.0F));

        root.addChild("inner", ModelPartBuilder.create().uv(0, 16)
                        .cuboid(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F, new Dilation(0.0F)),
                ModelTransform.pivot(0.0F, 20.0F, 0.0F));

        return TexturedModelData.of(data, 32, 32);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        outer.yaw = (float) Math.cos(animationProgress);
        outer.pitch = (float) Math.sin(animationProgress);

        inner.pitch = (float) -Math.tan(animationProgress);
        inner.roll = (float) Math.cos(animationProgress);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        outer.render(matrices, vertices, light, overlay);
        inner.render(matrices, vertices, light, overlay);
    }
}
