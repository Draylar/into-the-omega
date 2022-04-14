package draylar.intotheomega.client.entity.renderer;

import draylar.intotheomega.entity.SlimefallEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;

public class SlimefallEntityRenderer extends EntityRenderer<SlimefallEntity> {

    private static final ItemStack SLIME = new ItemStack(Items.SLIME_BLOCK);

    public SlimefallEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public void render(SlimefallEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);

        float lerpedAge = entity.age + tickDelta;

        matrices.push();
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(lerpedAge));
        matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(lerpedAge));
        MinecraftClient.getInstance().getItemRenderer().renderItem(
                SLIME,
                ModelTransformation.Mode.FIXED,
                light,
                OverlayTexture.DEFAULT_UV,
                matrices,
                vertexConsumers,
                0
        );
        matrices.pop();
    }

    @Override
    public Identifier getTexture(SlimefallEntity entity) {
        return null;
    }
}
