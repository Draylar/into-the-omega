package draylar.intotheomega.client.entity.renderer;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.client.entity.model.EndSlimeModel;
import draylar.intotheomega.enchantment.EndSlimeEntity;
import draylar.intotheomega.entity.OmegaSlimeEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class EndSlimeRenderer extends GeoEntityRenderer<EndSlimeEntity> {

    private static final Identifier TEXTURE = IntoTheOmega.id("textures/entity/end_slime.png");

    public EndSlimeRenderer(EntityRendererFactory.Context context) {
        super(context, new EndSlimeModel());
    }

    @Override
    public void render(EndSlimeEntity slime, float delta, float yaw, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light) {
        shadowRadius = 0.25F * (float) slime.getSize();

        matrixStack.push();
        scale(slime, matrixStack, delta);
        super.render(slime, delta, yaw, matrixStack, vertexConsumerProvider, light);
        matrixStack.pop();
    }


    private void scale(EndSlimeEntity slimeEntity, MatrixStack matrixStack, float f) {
        matrixStack.scale(0.5f, 0.5f, 0.5f);
        matrixStack.translate(0.0D, 0.0010000000474974513D, 0.0D);
        matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(-90));
        float h = (float) slimeEntity.getSize();
        float i = MathHelper.lerp(f, slimeEntity.lastStretch, slimeEntity.stretch) / (h * 0.5F + 1.0F);
        float j = 1.0F / (i + 1.0F);
        matrixStack.scale(j * h, 1.0F / j * h, j * h);
    }

    @Override
    public Identifier getTexture(EndSlimeEntity slimeEntity) {
        return TEXTURE;
    }

    @Override
    public RenderLayer getRenderType(EndSlimeEntity slime, float partialTicks, MatrixStack stack, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, Identifier textureLocation) {
        return RenderLayer.getEntityTranslucent(getTexture(slime));
    }
}

