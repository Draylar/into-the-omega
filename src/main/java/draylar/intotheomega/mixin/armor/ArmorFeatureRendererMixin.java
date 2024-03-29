package draylar.intotheomega.mixin.armor;

import draylar.intotheomega.item.SkinArmorItem;
import draylar.intotheomega.item.ice.ChilledVoidArmorItem;
import draylar.intotheomega.registry.client.OmegaRenderLayers;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ArmorFeatureRenderer.class)
public abstract class ArmorFeatureRendererMixin {

    @Shadow protected abstract Identifier getArmorTexture(ArmorItem armorItem, boolean bl, @Nullable String string);

    @Inject(
            method = "renderArmorParts",
            at = @At("HEAD"), cancellable = true)
    private void storeContext(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, ArmorItem armorItem, boolean bl, BipedEntityModel bipedEntityModel, boolean bl2, float f, float g, float h, String string, CallbackInfo ci) {
        if(armorItem instanceof ChilledVoidArmorItem) {
            VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumerProvider, OmegaRenderLayers.getChilledVoidArmorLayer(this.getArmorTexture(armorItem, bl2, string)), false, bl);
            bipedEntityModel.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, f, g, h, 1.0F);
            ci.cancel();
        }
    }

    @Inject(method = "renderArmor", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/model/BipedEntityModel;setAttributes(Lnet/minecraft/client/render/entity/model/BipedEntityModel;)V"), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private void cancelArmorModelRendering(MatrixStack matrices, VertexConsumerProvider vertexConsumers, LivingEntity entity, EquipmentSlot armorSlot, int light, BipedEntityModel model, CallbackInfo ci, ItemStack itemStack, ArmorItem item) {
        if(item instanceof SkinArmorItem) {
            ci.cancel();
        }
    }
}
