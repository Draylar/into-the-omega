package draylar.intotheomega.client.entity.renderer;

import com.google.common.collect.ImmutableMap;
import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.entity.starfall.StarfallProjectileEntity;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.*;
import org.lwjgl.glfw.GLFW;

public class StarfallEntityRenderer extends EntityRenderer<StarfallProjectileEntity> {

    private static final Identifier TEXTURE = IntoTheOmega.id("textures/entity/starfall_projectile_core.png");
    private static final ImmutableMap<Direction, Quaternion> OFFSETS = ImmutableMap.<Direction, Quaternion>builder()
            .put(Direction.UP, Vec3f.POSITIVE_Z.getDegreesQuaternion(90))
            .put(Direction.DOWN, Vec3f.POSITIVE_Z.getDegreesQuaternion(-90))
            .put(Direction.NORTH, Vec3f.POSITIVE_X.getDegreesQuaternion(90))
            .put(Direction.EAST, Vec3f.POSITIVE_X.getDegreesQuaternion(180))
            .put(Direction.SOUTH, Vec3f.POSITIVE_X.getDegreesQuaternion(-90))
            .put(Direction.WEST, Vec3f.POSITIVE_X.getDegreesQuaternion(0))
            .build();

    public StarfallEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(StarfallProjectileEntity starfall, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        super.render(starfall, yaw, tickDelta, matrices, vertexConsumers, light);

        // POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL
        VertexConsumer buffer = vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(getTexture(starfall)));
        matrices.push();

        float scale = Math.min(1.0f, (starfall.age + tickDelta) / 20f);
        float size = scale;
        float opaque = 1.0f;
        int death = starfall.hasCollided() ? starfall.getCollisionTicks() : -1;

        matrices.translate(0, size, 0);
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion((float) GLFW.glfwGetTime() * 10));
        matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion((float) GLFW.glfwGetTime() * 50));
        matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion((float) GLFW.glfwGetTime() * 50));

        if(death >= 0) {
            float deathScale = 1.0f + death / 5f;
            matrices.scale(deathScale, deathScale, deathScale);
            opaque = 1.0f - Math.min(1.0f, death / 10f);
        }

        for(Direction direction : Direction.values()) {
            matrices.push();
            matrices.multiply(OFFSETS.get(direction));
            Matrix4f pos = matrices.peek().getPositionMatrix();
            Matrix3f normal = matrices.peek().getNormalMatrix();
            buffer.vertex(pos, -size, -size, -size).color(1.0f, 1.0f, 1.0f * opaque, scale * opaque).texture(0, 0).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(normal, 0.0f, -1.0f, 0.0f).next();
            buffer.vertex(pos, -size, -size, size).color(1.0f, 1.0f, 1.0f, scale * opaque).texture(1, 0).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(normal, 0.0f, -1.0f, 0.0f).next();
            buffer.vertex(pos, size, -size, size).color(1.0f, 1.0f, 1.0f, scale * opaque).texture(1, 1).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(normal, 0.0f, -1.0f, 0.0f).next();
            buffer.vertex(pos, size, -size, -size).color(1.0f, 1.0f, 1.0f, scale * opaque).texture(0, 1).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(normal, 0.0f, -1.0f, 0.0f).next();
            matrices.pop();
        }

        matrices.pop();
    }

    @Override
    public Identifier getTexture(StarfallProjectileEntity entity) {
        return TEXTURE;
    }
}
