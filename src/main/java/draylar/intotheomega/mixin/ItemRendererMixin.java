package draylar.intotheomega.mixin;

import draylar.intotheomega.api.HandheldModelRegistry;
import draylar.intotheomega.registry.OmegaItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.*;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.render.item.ItemModels;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Environment(EnvType.CLIENT)
@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {

    @Shadow
    @Final
    private ItemModels models;


    @Shadow
    protected abstract void renderBakedItemModel(BakedModel model, ItemStack stack, int light, int overlay, MatrixStack matrices, VertexConsumer vertices);

    @Shadow
    public static VertexConsumer getDirectItemGlintConsumer(VertexConsumerProvider provider, RenderLayer layer, boolean solid, boolean glint) {
        return null;
    }

    @Shadow @Final private BuiltinModelItemRenderer builtinModelItemRenderer;

    @ModifyVariable(
            method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformation$Mode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/model/BakedModel;getTransformation()Lnet/minecraft/client/render/model/json/ModelTransformation;"
            ),
            index = 8
    )
    private BakedModel go(BakedModel model, ItemStack stack, ModelTransformation.Mode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, BakedModel modeley) {
        boolean bl = renderMode == ModelTransformation.Mode.GUI;
        boolean bl2 = bl || renderMode == ModelTransformation.Mode.GROUND || renderMode == ModelTransformation.Mode.FIXED;

        // return 16x16 inventory sprite
        if (HandheldModelRegistry.contains(stack.getItem()) && bl2) {
            return this.models.getModelManager().getModel(HandheldModelRegistry.get(stack.getItem()));
        }

        return model;
    }

    @Inject(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformation$Mode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;translate(DDD)V", shift = At.Shift.AFTER)
    )
    private void yeet(ItemStack stack, ModelTransformation.Mode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, BakedModel model, CallbackInfo ci) {
        boolean guiRenderType = renderMode == ModelTransformation.Mode.GUI;
        boolean bl2 = guiRenderType || renderMode == ModelTransformation.Mode.GROUND || renderMode == ModelTransformation.Mode.FIXED;

        if (!model.isBuiltin() && (!HandheldModelRegistry.contains(stack.getItem()) || bl2)) {
            RenderLayer renderLayer = RenderLayers.getItemLayer(stack, false);
            RenderLayer renderLayer3;
            if (guiRenderType && Objects.equals(renderLayer, TexturedRenderLayers.getEntityTranslucentCull())) {
                renderLayer3 = TexturedRenderLayers.getEntityTranslucentCull();
            } else {
                renderLayer3 = renderLayer;
            }

            VertexConsumer vertexConsumer = getDirectItemGlintConsumer(vertexConsumers, renderLayer3, true, stack.hasGlint());
            this.renderBakedItemModel(model, stack, light, overlay, matrices, vertexConsumer);
        } else {
            builtinModelItemRenderer.render(stack, renderMode, matrices, vertexConsumers, light, overlay);
        }
    }
}
