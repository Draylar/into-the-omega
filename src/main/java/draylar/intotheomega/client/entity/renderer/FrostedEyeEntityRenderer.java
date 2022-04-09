package draylar.intotheomega.client.entity.renderer;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.client.entity.model.FrostedEyeModel;
import draylar.intotheomega.entity.FrostedEyeEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class FrostedEyeEntityRenderer extends GeoEntityRenderer<FrostedEyeEntity> {

    private static final Identifier TEXTURE = IntoTheOmega.id("textures/entity/frosted_eye.png");

    public FrostedEyeEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new FrostedEyeModel());
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