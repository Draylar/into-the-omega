package draylar.intotheomega.client.trinket;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.client.TrinketRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3f;
import org.lwjgl.glfw.GLFW;

public class OrbitalTrinketRenderer implements TrinketRenderer {

    @Override
    public void render(ItemStack stack, SlotReference slotReference, EntityModel<? extends LivingEntity> model, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, LivingEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if(entity instanceof ClientPlayerEntity player) {
            matrices.push();
            matrices.translate(0.0F, 0.4F, -0.16F);
            matrices.scale(0.5f, 0.5f, 0.5f);
            matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(180));

            int distance = 2;
            int speed = 2;

            matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion((float) GLFW.glfwGetTime() * 25));
            matrices.translate(Math.cos(GLFW.glfwGetTime() * speed) * distance, Math.sin(GLFW.glfwGetTime()) * 0.25f, Math.sin(GLFW.glfwGetTime() * speed) * distance);

            MinecraftClient.getInstance().getItemRenderer().renderItem(
                    stack,
                    ModelTransformation.Mode.FIXED,
                    light,
                    OverlayTexture.DEFAULT_UV,
                    matrices,
                    vertexConsumers,
                    0
            );

            matrices.pop();
        }
    }
}
