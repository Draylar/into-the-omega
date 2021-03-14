package draylar.intotheomega.client.entity.renderer;

import draylar.intotheomega.entity.FrostedEyeEntity;
import draylar.intotheomega.registry.OmegaItems;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class FrostedEyeEntityRenderer extends EntityRenderer<FrostedEyeEntity> {

    public FrostedEyeEntityRenderer(EntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(FrostedEyeEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();

        float lerpedAge = MathHelper.lerp(tickDelta, entity.age - 1, entity.age);
        matrices.scale(2, 2, 2);
        matrices.translate(0, .25f, 0);
        matrices.translate(0, Math.sin(lerpedAge / 25f) * .25f, 0);

        MinecraftClient.getInstance().getItemRenderer()
                .renderItem(
                        new ItemStack(OmegaItems.OMEGA_SLIME_BLOCK),
                        ModelTransformation.Mode.FIXED,
                        light,
                        OverlayTexture.DEFAULT_UV,
                        matrices,
                        vertexConsumers
                );

        matrices.pop();
    }

    @Override
    public Identifier getTexture(FrostedEyeEntity entity) {
        return null;
    }
}
