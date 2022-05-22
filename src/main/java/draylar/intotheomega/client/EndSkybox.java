package draylar.intotheomega.client;

import com.mojang.blaze3d.systems.RenderSystem;
import draylar.intotheomega.IntoTheOmega;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3f;
import org.lwjgl.glfw.GLFW;

public class EndSkybox {

    private static final Identifier SKYBOX = IntoTheOmega.id("textures/cubemaps_skybox.png");

    public static void renderEndSky(MatrixStack matrices) {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.depthMask(false);
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        RenderSystem.setShaderTexture(0, SKYBOX);

        matrices.push();
        matrices.multiply(Vec3f.POSITIVE_Y.getRadialQuaternion((float) GLFW.glfwGetTime() / 100f));

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        for (int i = 0; i < 6; ++i) {
            matrices.push();

            // down
            float u = 0.25f;
            float v = 2 / 3f;
            float endU = 0.5f;
            float endV = 1.0f;

            // North
            if (i == 1) {
                v = 1 / 3f;
                endV = 2 / 3f;
                matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(90.0f));
            }

            // South
            if (i == 2) {
                u = 0.75f;
                endU = 1.0f;
                v = 1 / 3f;
                endV = 2 / 3f;
                matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(180.0f));
                matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(-90.0f));
            }

            // Up
            if (i == 3) {
                u = 0.25f;
                endU = 0.5f;
                v = 0.0f;
                endV = 1 / 3f;
                matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(180.0f));
            }

            // East
            if (i == 4) {
                u = 0.5f;
                endU = 0.75f;
                v = 1 / 3f;
                endV = 2 / 3f;
                matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(90));
                matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(90.0f));
            }

            // West
            if (i == 5) {

                u = 0.0f;
                endU = 0.25f;
                v = 1 / 3f;
                endV = 2 / 3f;
                matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(90));
                matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(-90.0f));
            }

            Matrix4f matrix4f = matrices.peek().getPositionMatrix();
            bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
            bufferBuilder.vertex(matrix4f, -100.0f, -100.0f, -100.0f).texture(u, v).color(200, 200, 200, 200).next();
            bufferBuilder.vertex(matrix4f, -100.0f, -100.0f, 100.0f).texture(u, endV).color(200, 200, 200, 200).next();
            bufferBuilder.vertex(matrix4f, 100.0f, -100.0f, 100.0f).texture(endU, endV).color(200, 200, 200, 200).next();
            bufferBuilder.vertex(matrix4f, 100.0f, -100.0f, -100.0f).texture(endU, v).color(200, 200, 200, 200).next();
            tessellator.draw();
            matrices.pop();
        }

        matrices.pop();

        RenderSystem.depthMask(true);
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }
}
