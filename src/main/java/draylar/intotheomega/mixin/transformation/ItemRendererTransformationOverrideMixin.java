package draylar.intotheomega.mixin.transformation;

import draylar.intotheomega.api.client.TransformationOverrideList;
import draylar.intotheomega.impl.ModelTransformationOverrideManipulator;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BasicBakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.render.model.json.Transformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public class ItemRendererTransformationOverrideMixin {

    @Unique private ItemStack ito_stack = ItemStack.EMPTY;
    @Unique private BakedModel ito_model;
    @Unique private ModelTransformation.Mode ito_mode;

    @Inject(
            method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformation$Mode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V",
            at = @At("HEAD"))
    private void storeContext(ItemStack stack, ModelTransformation.Mode mode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, BakedModel model, CallbackInfo ci) {
        this.ito_stack = stack;
        this.ito_model = model;
        this.ito_mode = mode;
    }

    @Redirect(
            method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformation$Mode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/model/json/Transformation;apply(ZLnet/minecraft/client/util/math/MatrixStack;)V"))
    private void applyTransformationOverrides(Transformation transformation, boolean leftHanded, MatrixStack matrices) {
        if(ito_model instanceof BasicBakedModel) {
            TransformationOverrideList transformationOverrides = ((ModelTransformationOverrideManipulator) ito_model).getTransformationOverrides();
            if(transformationOverrides != null && !transformationOverrides.isEmpty()) {
                // TODO: should this always be the player? Probably not.
                if (!transformationOverrides.apply(ito_model, ito_stack, MinecraftClient.getInstance().world, MinecraftClient.getInstance().player, leftHanded, matrices, ito_mode)) {
                    transformation.apply(leftHanded, matrices);
                }

                return;
            }
        }

        transformation.apply(leftHanded, matrices);
    }
}
