package draylar.intotheomega.client.entity.renderer;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.api.GeoBaseEntityRenderer;
import draylar.intotheomega.entity.envoy.EnvoySegmentEntity;
import draylar.intotheomega.registry.client.OmegaRenderLayers;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;
import org.lwjgl.glfw.GLFW;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class EnvoyGenericSectionRenderer extends GeoBaseEntityRenderer<EnvoySegmentEntity> {

    public EnvoyGenericSectionRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new EnvoyModel());
    }

    @Override
    public void render(EnvoySegmentEntity entity, float entityYaw, float partialTicks, MatrixStack matrices, VertexConsumerProvider bufferIn, int packedLightIn) {
        matrices.push();

        matrices.translate(0, 2.5, 0);
        matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(entity.getPitch()));
        float scale = (50 - entity.getLinkIndex()) / 50f;
        matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion((float) GLFW.glfwGetTime() * scale * 100));
        matrices.scale(5f, 5f, 5f);
        matrices.translate(0, -2.5, 0);


        super.render(entity, entityYaw, partialTicks, matrices, bufferIn, packedLightIn);
        matrices.pop();
    }

    @Override
    public RenderLayer getRenderType(EnvoySegmentEntity animatable, float partialTicks, MatrixStack stack, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, Identifier textureLocation) {
        return OmegaRenderLayers.getEntityTranslucentFogless(getTexture(animatable), false);
    }

    public static class EnvoyModel extends AnimatedGeoModel<EnvoySegmentEntity> {

        private static final Identifier MODEL = IntoTheOmega.id("geo/envoy_generic_section.geo.json");
        private static final Identifier TEXTURE = IntoTheOmega.id("textures/entity/envoy_generic_section.png");

        @Override
        public Identifier getModelLocation(EnvoySegmentEntity object) {
            return MODEL;
        }

        @Override
        public Identifier getTextureLocation(EnvoySegmentEntity object) {
            return TEXTURE;
        }

        @Override
        public Identifier getAnimationFileLocation(EnvoySegmentEntity animatable) {
            return null;
        }
    }
}
