package draylar.intotheomega.mixin.armor;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.impl.CrimsonArmorContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import org.checkerframework.common.aliasing.qual.Unique;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntityRenderer.class)
public abstract class PlayerRenderLayerMixin<T extends LivingEntity, M extends EntityModel<T>>
        extends EntityRenderer<T>
        implements FeatureRendererContext<T, M>, CrimsonArmorContext {

    @Unique private static final Identifier CRIMSON_MAJESTY_TEXTURE = IntoTheOmega.id("textures/entity/crimson_majesty_skin.png");
    @Unique private boolean crimsonMajestySkin = false;

    protected PlayerRenderLayerMixin(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Inject(method = "getRenderLayer", at = @At("HEAD"), cancellable = true)
    private void adjustRenderLayer(T entity, boolean showBody, boolean translucent, boolean showOutline, CallbackInfoReturnable<@Nullable RenderLayer> cir) {
        if(crimsonMajestySkin) {
            cir.setReturnValue(RenderLayer.getEntityTranslucent(CRIMSON_MAJESTY_TEXTURE));
        }
    }

    @Override
    public void setCrimsonArmor(boolean c) {
        crimsonMajestySkin = c;
    }
}
