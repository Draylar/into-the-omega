package draylar.intotheomega.api.client;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Matrix4f;

public class ShapeRendering {

    public static void quad(int light, float r, float g, float b, Matrix3f normal, Matrix4f position, VertexWrapper buffer) {
        buffer.vertex(position, -0.5f, 0.0f, -0.5f).color(r, g, b, 1.0f).texture(0.0f, 0.0f).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(normal, 0.0f, 1.0f, 0.0f).next();
        buffer.vertex(position, 0.5f, 0.0f, -0.5f).color(r, g, b, 1.0f).texture(1.0f, 0.0f).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(normal, 0.0f, 1.0f, 0.0f).next();
        buffer.vertex(position, 0.5f, 0.0f, 0.5f).color(r, g, b, 1.0f).texture(1.0f, 1.0f).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(normal, 0.0f, 1.0f, 0.0f).next();
        buffer.vertex(position, -0.5f, 0.0f, 0.5f).color(r, g, b, 1.0f).texture(0.0f, 1.0f).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(normal, 0.0f, 1.0f, 0.0f).next();
    }
}
