package draylar.intotheomega.entity.void_matrix.beam;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import draylar.intotheomega.api.TextureConstants;
import draylar.intotheomega.api.client.ShapeRendering;
import draylar.intotheomega.api.client.VertexWrapper;
import draylar.intotheomega.registry.client.OmegaRenderLayers;
import draylar.intotheomega.registry.client.OmegaShaders;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;

public class VoidMatrixBeamRenderer extends EntityRenderer<VoidMatrixBeamEntity> {

    public static final RenderPhase.Transparency TRANSPARENCY = new RenderPhase.Transparency("additive_transparency", () -> {
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(
                GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE,
                GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ONE
        );
    }, () -> {
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
    });

    private static final RenderLayer GLOWING_BEAM = OmegaRenderLayers.entityShader(
            TextureConstants.WHITE,
            OmegaShaders.GLOWING_BEAM,
            builder -> builder.transparency(TRANSPARENCY)
    );

    public VoidMatrixBeamRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public void render(VoidMatrixBeamEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        Entity camera = MinecraftClient.getInstance().getCameraEntity();
        double diffX = camera.getX() - entity.getX();
        double diffZ = camera.getZ() - entity.getZ();
        double towardsCamera = Math.atan2(diffZ, diffX) + Math.PI / 2f;

        float scale = 0.0f;
        float age = entity.age + tickDelta;
        if(age <= 20.0f) {
            scale = (1.0f - age / 20.0f) * 5.0f;
        }

        if(age >= 25.0f) {
            float tage = age - 25f;
            scale = Math.max(0.0f, (1.0f - tage / 20.0f) * 30.0f);
        }

        matrices.push();
        matrices.translate(0, 0.01, 0);
        matrices.scale(0.5f, 0.5f, 0.5f);
        matrices.scale(scale, scale, scale);
        ShapeRendering.quad(
                LightmapTextureManager.MAX_LIGHT_COORDINATE,
                1.0f,
                1.0f,
                1.0f,
                matrices,
                VertexWrapper.wrap(vertexConsumers.getBuffer(OmegaRenderLayers.getEntityTrueTranslucent(new Identifier("intotheomega", "textures/particle/bloom_mid.png"))), GLOWING_BEAM.getVertexFormat())
        );
        matrices.pop();

        matrices.push();
        matrices.translate(0.0f, 500.0f, 0.0f);
        matrices.scale(scale, 1000.0f, scale);
        matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(90));
        matrices.multiply(Vec3f.POSITIVE_Z.getRadialQuaternion((float) towardsCamera));

        VertexConsumer buffer = vertexConsumers.getBuffer(GLOWING_BEAM);
        ShapeRendering.quad(
                LightmapTextureManager.MAX_LIGHT_COORDINATE,
                1.0f,
                1.0f,
                1.0f,
                matrices,
                VertexWrapper.wrap(buffer, GLOWING_BEAM.getVertexFormat())
        );

        matrices.pop();
    }

    @Override
    public Identifier getTexture(VoidMatrixBeamEntity entity) {
        return TextureConstants.WHITE;
    }
}
