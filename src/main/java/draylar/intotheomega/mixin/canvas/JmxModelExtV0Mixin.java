package draylar.intotheomega.mixin.canvas;

import draylar.intotheomega.api.client.TransformationOverrideList;
import draylar.intotheomega.impl.CanvasParentAssigner;
import draylar.intotheomega.impl.JsonUnbakedModelTransformationOverrideManipulator;
import draylar.intotheomega.impl.ModelTransformationOverrideManipulator;
import draylar.intotheomega.mixin.JsonUnbakedModelAccessor;
import grondag.jmx.json.model.JmxBakedModel;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BasicBakedModel;
import net.minecraft.client.render.model.ModelBakeSettings;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.client.render.model.json.ModelOverrideList;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Function;

@Pseudo
@Mixin(targets = "grondag.jmx.json.v0.JmxModelExtV0")
public class JmxModelExtV0Mixin implements CanvasParentAssigner {

    @Unique
    private JsonUnbakedModel parent = null;

    @Inject(
            method = "buildModel",
            at = @At("RETURN"), cancellable = true)
    private void onReturn(ModelOverrideList modelOverrideList, boolean hasDepth, Sprite particleSprite, ModelBakeSettings bakeProps, Identifier modelId, JsonUnbakedModel me, Function<SpriteIdentifier, Sprite> textureGetter, CallbackInfoReturnable<BakedModel> cir) {
        JmxBakedModel model = (JmxBakedModel) cir.getReturnValue();
        TransformationOverrideList transformationOverrides = new TransformationOverrideList(((JsonUnbakedModelTransformationOverrideManipulator) me).getTransformationOverrides(parent));
        ((ModelTransformationOverrideManipulator) model).setTransformationOverrides(transformationOverrides);
    }

    @Override
    @Unique
    public void setParent(JsonUnbakedModel parent) {
        this.parent = parent;
    }
}
