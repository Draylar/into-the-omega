package draylar.intotheomega.api;

import draylar.intotheomega.IntoTheOmegaClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.ai.goal.PrioritizedGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.math.Matrix4f;

import java.util.List;

public class DebugAIRenderer {

    public static void render(MobEntity mob, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light) {
        List<PrioritizedGoal> strings = IntoTheOmegaClient.DEVELOPMENT_AI_SYNC.get(mob.getEntityId());

        if(strings != null) {
            for (int i = strings.size() - 1; i >= 0; i--) {
                renderText(mob, new LiteralText(strings.get(i).getGoal().toString()), matrixStack, vertexConsumerProvider, light, i / 2f);
            }
        }
    }

    private static void renderText(MobEntity entity, Text text, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, float offset) {
        double d = MinecraftClient.getInstance().getEntityRenderDispatcher().getSquaredDistanceToCamera(entity);

        if (d <= 4096.0D) {
            boolean notSneaking = !entity.isSneaky();
            float labelPosition = entity.getHeight() + 0.5F + offset;

            matrices.push();
            matrices.translate(0.0D, labelPosition, 0.0D);
            matrices.multiply(MinecraftClient.getInstance().getEntityRenderDispatcher().getRotation());
            matrices.scale(-0.025F, -0.025F, 0.025F);
            Matrix4f matrix4f = matrices.peek().getModel();
            float g = MinecraftClient.getInstance().options.getTextBackgroundOpacity(0.25F);
            int j = (int)(g * 255.0F) << 24;
            TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
            float h = (float)(-textRenderer.getWidth(text) / 2);
            textRenderer.draw(text, h, (float) 0, 553648127, false, matrix4f, vertexConsumers, notSneaking, j, light);

            if (notSneaking) {
                textRenderer.draw(text, h, (float) 0, -1, false, matrix4f, vertexConsumers, false, 0, light);
            }

            matrices.pop();
        }
    }
}
