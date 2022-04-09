package draylar.intotheomega.api;

import draylar.intotheomega.IntoTheOmegaClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.text.LiteralText;

public class PathRenderer {

    public static void render(MobEntity mob, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light) {
        Path path = IntoTheOmegaClient.DEVELOPMENT_PATH_SYNC.get(mob.getId());

        if(path != null) {
            matrixStack.push();

            matrixStack.translate(-mob.getX(), -mob.getY(), -mob.getZ());

            //
            matrixStack.translate(path.getTarget().getX(), path.getTarget().getY(), path.getTarget().getZ());

            matrixStack.push();
            matrixStack.scale(.1f, -.1f, .1f);
            matrixStack.translate(-10, -15, 0);
            MinecraftClient.getInstance().textRenderer.draw(matrixStack, new LiteralText("Target"), 0, 0, 0xffffff);
            matrixStack.pop();
            VertexConsumer buffer = MinecraftClient.getInstance().getBufferBuilders().getEffectVertexConsumers().getBuffer(RenderLayer.getLines());
            WorldRenderer.drawBox(matrixStack, buffer, 0, 0, 0, 1, 1, 1, 0.9f, 0.9f, 0.9f, 1.0f, 0.5F, 0.5F, 0.5F);

            matrixStack.pop();
        }
    }
}