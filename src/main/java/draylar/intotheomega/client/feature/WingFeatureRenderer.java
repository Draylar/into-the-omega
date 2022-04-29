package draylar.intotheomega.client.feature;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketsApi;
import draylar.intotheomega.registry.OmegaItems;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
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
import net.minecraft.util.Pair;
import net.minecraft.util.math.Vec3f;

import java.util.List;

public class WingFeatureRenderer extends FeatureRenderer<LivingEntity, BipedEntityModel<LivingEntity>> {

    public WingFeatureRenderer(FeatureRendererContext<LivingEntity, BipedEntityModel<LivingEntity>> context) {
        super(context);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, LivingEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        TrinketsApi.getTrinketComponent(entity).ifPresent(component -> {
            List<Pair<SlotReference, ItemStack>> wings = component.getEquipped(OmegaItems.PHOENIX_STARDUST_WINGS);
            if(!wings.isEmpty()) {
                ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();

                matrices.push();
                matrices.translate(0.0f, -0.0f, 0.5f);
                matrices.translate(0.0f, Math.sin(RenderSystem.getShaderGameTime() * 1000f) * 0.2f, 0.0f);
                matrices.scale(3.0f, 3.0f, 0.2f);
                matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(180));
                itemRenderer.renderItem(wings.get(0).getRight(), ModelTransformation.Mode.FIXED, LightmapTextureManager.pack(15, 15), OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, 0);
                matrices.pop();
            }
        });
    }
}
