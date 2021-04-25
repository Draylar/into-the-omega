package draylar.intotheomega.client.entity.renderer;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.client.entity.model.FrostedEyeModel;
import draylar.intotheomega.entity.FrostedEyeEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class FrostedEyeEntityRenderer extends MobEntityRenderer<FrostedEyeEntity, FrostedEyeModel> {

    private static final Identifier TEXTURE = IntoTheOmega.id("textures/entity/frosted_eye.png");

    public FrostedEyeEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new FrostedEyeModel(), 0.25F);
    }

    @Override
    public void render(FrostedEyeEntity eye, float f, float delta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
        matrixStack.translate(0, Math.sin(MathHelper.lerp(delta, eye.age, eye.age + 1) / 10f) / 5f, 0);
        super.render(eye, f, delta, matrixStack, vertexConsumerProvider, i);
        matrixStack.pop();
    }

    @Override
    public Identifier getTexture(FrostedEyeEntity eye) {
        return TEXTURE;
    }
}