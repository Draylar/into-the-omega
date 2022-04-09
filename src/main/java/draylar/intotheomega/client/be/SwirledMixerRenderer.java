package draylar.intotheomega.client.be;

import draylar.intotheomega.entity.block.SwirledMixerBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;

public class SwirledMixerRenderer implements BlockEntityRenderer<SwirledMixerBlockEntity> {

    public SwirledMixerRenderer(BlockEntityRendererFactory.Context context) {

    }

    @Override
    public void render(SwirledMixerBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        MinecraftClient client = MinecraftClient.getInstance();
        ItemRenderer renderer = client.getItemRenderer();
        ClientPlayerEntity player = client.player;

        float lerpedAge = MathHelper.lerp(tickDelta, player.age, player.age + 1);

        // Render the catalyst slot
        ItemStack catalyst = entity.getCatalyst();
        if(!catalyst.isEmpty()) {
            matrices.push();
            matrices.translate(0.5f, 1f + Math.sin(lerpedAge / 20f) * 0.025, 0.5f);
            matrices.scale(0.35f, 0.35f, 0.35f);
            matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(lerpedAge * 2));
            renderer.renderItem(catalyst, ModelTransformation.Mode.FIXED, light, overlay, matrices, vertexConsumers, 0);
            matrices.pop();
        }

        // Render potions
        double offset = 0.0f;
        for (ItemStack potion : entity.getPotions()) {
            if(!potion.isEmpty()) {
                offset += Math.PI / 1.5f;

                // Render potion
                matrices.push();
                matrices.translate(0.5f + Math.sin(lerpedAge / 20f + offset) * 0.35f, 1f + Math.sin(lerpedAge / 20f) * 0.025, 0.5f + Math.cos(lerpedAge / 20f + offset) * 0.35f);
                matrices.scale(0.35f, 0.35f, 0.35f);
                matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(lerpedAge * 5));
                renderer.renderItem(potion, ModelTransformation.Mode.FIXED, light, overlay, matrices, vertexConsumers, 0);
                matrices.pop();
            }
        }
    }
}
