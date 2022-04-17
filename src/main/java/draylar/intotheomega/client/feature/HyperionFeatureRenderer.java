package draylar.intotheomega.client.feature;

import com.mojang.blaze3d.systems.RenderSystem;
import draylar.intotheomega.registry.OmegaItems;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3f;

public class HyperionFeatureRenderer extends FeatureRenderer<LivingEntity, BipedEntityModel<LivingEntity>> {

    private static final ItemStack HYPERION = new ItemStack(OmegaItems.EMERGENT_HYPERION);

    public HyperionFeatureRenderer(FeatureRendererContext<LivingEntity, BipedEntityModel<LivingEntity>> context) {
        super(context);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, LivingEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if(entity.getStackInHand(Hand.MAIN_HAND).getItem().equals(OmegaItems.EMERGENT_HYPERION)) {
            ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();

            matrices.push();
            matrices.translate(0.0f, -1.5f, 1.0f);
            matrices.translate(0.0f, Math.sin(RenderSystem.getShaderGameTime() * 1000f) * 0.2f, 0.0f);
            matrices.scale(10.0f, 10.0f, 2.0f);
            matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(180));
            itemRenderer.renderItem(HYPERION, ModelTransformation.Mode.FIXED, light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, 0);
            matrices.pop();
        }
    }
}
