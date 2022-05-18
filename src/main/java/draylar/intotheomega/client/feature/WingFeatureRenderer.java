package draylar.intotheomega.client.feature;

import com.mojang.blaze3d.systems.RenderSystem;
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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Pair;
import net.minecraft.util.math.Vec3f;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class WingFeatureRenderer extends FeatureRenderer<LivingEntity, BipedEntityModel<LivingEntity>> {

    private static final List<Item> WINGS = Arrays.asList(OmegaItems.STARLIGHT_WINGS, OmegaItems.PHOENIX_STARDUST_WINGS, OmegaItems.DRAGON_WINGS);

    public WingFeatureRenderer(FeatureRendererContext<LivingEntity, BipedEntityModel<LivingEntity>> context) {
        super(context);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, LivingEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        TrinketsApi.getTrinketComponent(entity).ifPresent(component -> {
            Optional<ItemStack> wings = WINGS.stream().map(component::getEquipped).filter(it -> !it.isEmpty()).map(it -> it.get(0)).map(Pair::getRight).findFirst();
            wings.ifPresent(stack -> {
                ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();

                matrices.push();
                matrices.translate(0.0f, -0.0f, 0.5f);
                matrices.translate(0.0f, Math.sin(RenderSystem.getShaderGameTime() * 1000f) * 0.2f, 0.0f);
                matrices.scale(3.0f, 3.0f, 0.2f);
                matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(180));
                itemRenderer.renderItem(stack, ModelTransformation.Mode.FIXED, LightmapTextureManager.pack(15, 15), OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, 0);
                matrices.pop();
            });
        });
    }
}
