package draylar.intotheomega.api.client;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.*;

public class BoxRendering {

    private static final ImmutableMap<Direction, Quaternion> OFFSETS = ImmutableMap.<Direction, Quaternion>builder()
            .put(Direction.UP, Vec3f.POSITIVE_Z.getDegreesQuaternion(90))
            .put(Direction.DOWN, Vec3f.POSITIVE_Z.getDegreesQuaternion(-90))
            .put(Direction.NORTH, Vec3f.POSITIVE_X.getDegreesQuaternion(90))
            .put(Direction.EAST, Vec3f.POSITIVE_X.getDegreesQuaternion(180))
            .put(Direction.SOUTH, Vec3f.POSITIVE_X.getDegreesQuaternion(-90))
            .put(Direction.WEST, Vec3f.POSITIVE_X.getDegreesQuaternion(0))
            .build();

    public static void renderBox(MatrixStack matrices, VertexConsumer buffer, float size, int light) {
        renderBox(matrices, buffer, size, light, 1.0f, 1.0f, 1.0f);
    }

    public static void renderBox(MatrixStack matrices, VertexConsumer buffer, float size, int light, float red, float green, float blue) {
        RenderSystem.enableDepthTest();

        for(Direction direction : Direction.values()) {
            matrices.push();
            matrices.multiply(OFFSETS.get(direction));
            Matrix4f pos = matrices.peek().getPositionMatrix();
            Matrix3f normal = matrices.peek().getNormalMatrix();
            buffer.vertex(pos, -size, -size, -size).color(red, green, blue, 1.0f).texture(0, 0).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(normal, 0.0f, -1.0f, 0.0f).next();
            buffer.vertex(pos, -size, -size, size).color(red, green, blue, 1.0f).texture(1, 0).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(normal, 0.0f, -1.0f, 0.0f).next();
            buffer.vertex(pos, size, -size, size).color(red, green, blue, 1.0f).texture(1, 1).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(normal, 0.0f, -1.0f, 0.0f).next();
            buffer.vertex(pos, size, -size, -size).color(red, green, blue, 1.0f).texture(0, 1).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(normal, 0.0f, -1.0f, 0.0f).next();
            matrices.pop();
        }

        RenderSystem.disableDepthTest();
    }
}
