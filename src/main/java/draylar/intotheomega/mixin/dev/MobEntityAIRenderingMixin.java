package draylar.intotheomega.mixin.dev;

import draylar.intotheomega.api.DebugAIRenderer;
import draylar.intotheomega.api.PathRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.MobEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MobEntityRenderer.class)
public abstract class MobEntityAIRenderingMixin extends LivingEntityRenderer {

    private MobEntityAIRenderingMixin(EntityRenderDispatcher dispatcher, EntityModel model, float shadowRadius) {
        super(dispatcher, model, shadowRadius);
    }

    @Inject(
            method = "render",
            at = @At("RETURN"))
    private void renderDebugInfo(MobEntity mob, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light, CallbackInfo ci) {
//        DebugAIRenderer.render(mob, matrixStack, vertexConsumerProvider, light);
//        PathRenderer.render(mob, matrixStack, vertexConsumerProvider, light);
    }
}
