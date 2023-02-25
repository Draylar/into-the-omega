package draylar.intotheomega.entity.void_matrix.beam;

import com.mojang.blaze3d.systems.RenderSystem;
import draylar.intotheomega.api.TextureConstants;
import draylar.intotheomega.api.client.ShapeRendering;
import draylar.intotheomega.api.client.VertexWrapper;
import draylar.intotheomega.registry.client.OmegaRenderLayers;
import draylar.intotheomega.registry.client.OmegaShaders;
import net.fabricmc.fabric.impl.client.indigo.renderer.render.AbstractQuadRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3f;

public class VoidMatrixBeamRenderer extends EntityRenderer<VoidMatrixBeamEntity> {

    private static final RenderLayer GLOWING_BEAM = OmegaRenderLayers.entityShader(TextureConstants.WHITE, OmegaShaders.GLOWING_BEAM);

    public VoidMatrixBeamRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public void render(VoidMatrixBeamEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        RenderSystem.enableDepthTest();

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
        matrices.translate(0.0f, 500.0f, 0.0f);
        matrices.scale(scale, 1000.0f, scale);
        matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(90));
        matrices.multiply(Vec3f.POSITIVE_Z.getRadialQuaternion((float) towardsCamera));
        ShapeRendering.quad(
                LightmapTextureManager.MAX_LIGHT_COORDINATE,
                1.0f,
                1.0f,
                1.0f,
                matrices,
                VertexWrapper.wrap(vertexConsumers.getBuffer(GLOWING_BEAM), GLOWING_BEAM.getVertexFormat())
        );

        matrices.pop();
    }

    @Override
    public Identifier getTexture(VoidMatrixBeamEntity entity) {
        return TextureConstants.WHITE;
    }
}
