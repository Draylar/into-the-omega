package draylar.intotheomega.client.entity.renderer;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.entity.nova.NovaNodeEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3f;
import org.lwjgl.glfw.GLFW;

public class NovaNodeRenderer extends EntityRenderer<NovaNodeEntity> {

    private static final Identifier TEXTURE = IntoTheOmega.id("textures/particle/origin_nova.png");

    public NovaNodeRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(NovaNodeEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);

        VertexConsumer buffer = vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(getTexture(entity)));

        double camDiffX = entity.getX() - MinecraftClient.getInstance().getCameraEntity().getX();
        double camDiffY = entity.getY() - MinecraftClient.getInstance().getCameraEntity().getY();
        double camDiffZ = entity.getZ() - MinecraftClient.getInstance().getCameraEntity().getZ();
        double distance = entity.distanceTo(MinecraftClient.getInstance().getCameraEntity());

        matrices.push();
        matrices.scale(0.25f, 0.25f, 0.25f);

        for (int i = 0; i < 10; i++) {
            float scale = 1.0f + (i / 10f) * 3;
            matrices.push();

            matrices.multiply(Vec3f.POSITIVE_Y.getRadialQuaternion((float) Math.toRadians(90f) - (float) Math.atan2(camDiffZ, camDiffX)));

            matrices.translate(0, 0, i / 100f);
            matrices.scale(scale, scale, scale);

            matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion((float) GLFW.glfwGetTime() * (i + 2) * 15));

            Matrix4f pos = matrices.peek().getPositionMatrix();
            Matrix3f normal = matrices.peek().getNormalMatrix();
            float color = Math.min(1.0f, i / 10f);
            buffer.vertex(pos, -1.0f, -1.0f, 0.0f).color(color, color * 0.3f, color, 1.0f).texture(0, 0).overlay(OverlayTexture.DEFAULT_UV).light(LightmapTextureManager.MAX_BLOCK_LIGHT_COORDINATE).normal(normal, 0, -1.0f, 0).next();
            buffer.vertex(pos, -1.0f, 1.0f, 0.0f).color(color, color * 0.3f, color, 1.0f).texture(0, 1).overlay(OverlayTexture.DEFAULT_UV).light(LightmapTextureManager.MAX_BLOCK_LIGHT_COORDINATE).normal(normal, 0, -1.0f, 0).next();
            buffer.vertex(pos, 1.0f, 1.0f, 0.0f).color(color, color * 0.3f, color, 1.0f).texture(1, 1).overlay(OverlayTexture.DEFAULT_UV).light(LightmapTextureManager.MAX_BLOCK_LIGHT_COORDINATE).normal(normal, 0, -1.0f, 0).next();
            buffer.vertex(pos, 1.0f, -1.0f, 0.0f).color(color, color * 0.3f, color, 1.0f).texture(1, 0).overlay(OverlayTexture.DEFAULT_UV).light(LightmapTextureManager.MAX_BLOCK_LIGHT_COORDINATE).normal(normal, 0, -1.0f, 0).next();
            matrices.pop();
        }

        matrices.pop();
    }

    @Override
    public Identifier getTexture(NovaNodeEntity entity) {
        return TEXTURE;
    }
}
