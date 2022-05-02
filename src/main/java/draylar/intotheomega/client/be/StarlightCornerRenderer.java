package draylar.intotheomega.client.be;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.api.client.BoxRendering;
import draylar.intotheomega.block.ActivatedStarlightCornerBlock;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

public class StarlightCornerRenderer implements BlockEntityRenderer<ActivatedStarlightCornerBlock.Entity> {

    private static final Identifier TEXTURE = IntoTheOmega.id("textures/entity/solid_white.png");

    public StarlightCornerRenderer(BlockEntityRendererFactory.Context context) {

    }

    @Override
    public void render(ActivatedStarlightCornerBlock.Entity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        VertexConsumer buffer = vertexConsumers.getBuffer(RenderLayer.getEyes(TEXTURE));
        float purple = (float) (Math.sin(GLFW.glfwGetTime()) / 2) + 0.5f;
        matrices.push();
        matrices.translate(0.5, 5, 0.5);
        matrices.scale(0.1f, 5, 0.1f);
        BoxRendering.renderBox(matrices, buffer, -1.0f, LightmapTextureManager.MAX_LIGHT_COORDINATE, 1.0f, 0.25f + (purple * 0.75f), 1.0f);
        matrices.pop();
    }
}
