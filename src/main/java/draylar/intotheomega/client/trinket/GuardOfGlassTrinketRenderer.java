package draylar.intotheomega.client.trinket;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.client.TrinketRenderer;
import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.api.data.player.PlayerDataAccess;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3f;
import org.lwjgl.glfw.GLFW;

public class GuardOfGlassTrinketRenderer implements TrinketRenderer {

    @Override
    public void render(ItemStack stack, SlotReference slotReference, EntityModel<? extends LivingEntity> contextModel, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, LivingEntity entity, float limbAngle, float limbDistance, float delta, float animationProgress, float headYaw, float headPitch) {
        if(!(entity instanceof PlayerEntity player)) {
            return;
        }

        matrices.scale(0.75F, 0.75F, 0.75f);

        // todo: global matrix stack that moves to player so we aren't impacted by player rotation
        matrices.push();
        matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(180));

        int distance = 2;
        int speed = 2;

        // 0 = back
        // 90 = right
        // 180 = front
        // 270 = left

        // 0, 120, 240
        int max = player.getPlayerData(IntoTheOmega.VOID_MATRIX_SHIELD_STATUS);
        for (int i = 0; i < max; i++) {
            matrices.push();

            int progressAmount = (max == 3 ? 120 : max == 2 ? 180 : 0);
            matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(progressAmount * i));
            matrices.translate(0.0f, -0.75f, -0.5f);
            matrices.translate(0, Math.sin((entity.age + delta) * 0.05) * 0.1, 0);

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

        matrices.pop();
    }
}
