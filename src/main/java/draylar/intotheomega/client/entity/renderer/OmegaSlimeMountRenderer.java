package draylar.intotheomega.client.entity.renderer;

import draylar.intotheomega.entity.OmegaSlimeMountEntity;
import draylar.intotheomega.registry.OmegaItems;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class OmegaSlimeMountRenderer extends EntityRenderer<OmegaSlimeMountEntity> {

    public OmegaSlimeMountRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public void render(OmegaSlimeMountEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);

        matrices.push();
        matrices.scale(1.5f, 1.5f, 1.5f);
        matrices.translate(0, 0.25f, 0);
        MinecraftClient.getInstance().getItemRenderer()
                .renderItem(
                        new ItemStack(OmegaItems.OMEGA_SLIME_BLOCK),
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
    public Identifier getTexture(OmegaSlimeMountEntity entity) {
        return null;
    }
}
