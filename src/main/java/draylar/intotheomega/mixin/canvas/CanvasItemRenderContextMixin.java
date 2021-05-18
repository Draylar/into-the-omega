package draylar.intotheomega.mixin.canvas;

import draylar.intotheomega.api.client.TransformationOverrideList;
import draylar.intotheomega.impl.ModelTransformationOverrideManipulator;
import grondag.jmx.json.model.JmxBakedModel;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemModels;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BasicBakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.render.model.json.Transformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// TODO: only load this when Canvas is present!
@Pseudo
@Mixin(targets = "grondag.canvas.apiimpl.rendercontext.ItemRenderContext")
public class CanvasItemRenderContextMixin {

    @Unique private ItemStack ito_stack = ItemStack.EMPTY;
    @Unique private BakedModel ito_model;
    @Unique private ModelTransformation.Mode ito_mode;

    @Inject(
            method = "renderItem",
            at = @At("HEAD"))
    private void storeContext(ItemModels models, ItemStack stack, ModelTransformation.Mode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, BakedModel model, CallbackInfo ci) {
        this.ito_stack = stack;
        this.ito_model = model;
        this.ito_mode = renderMode;
    }

    @Redirect(
            method = "renderItem",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/model/json/Transformation;apply(ZLnet/minecraft/client/util/math/MatrixStack;)V"))
    private void applyTransformationOverrides(Transformation transformation, boolean leftHanded, MatrixStack matrices) {
        if(ito_model instanceof JmxBakedModel) {
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
