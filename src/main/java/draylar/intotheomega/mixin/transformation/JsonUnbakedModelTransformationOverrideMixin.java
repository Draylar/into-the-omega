package draylar.intotheomega.mixin.transformation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import draylar.intotheomega.api.client.TransformationOverride;
import draylar.intotheomega.api.client.TransformationOverrideList;
import draylar.intotheomega.impl.ModelTransformationOverrideManipulator;
import draylar.intotheomega.impl.JsonUnbakedModelTransformationOverrideManipulator;
import draylar.intotheomega.mixin.access.JsonUnbakedModelAccessor;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BasicBakedModel;
import net.minecraft.client.render.model.ModelBakeSettings;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.json.*;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

@Mixin(JsonUnbakedModel.class)
public class JsonUnbakedModelTransformationOverrideMixin implements JsonUnbakedModelTransformationOverrideManipulator {

    @Shadow @Final @Mutable
    static Gson GSON;

    static {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(JsonUnbakedModel.class, new JsonUnbakedModel.Deserializer());
        builder.registerTypeAdapter(ModelElement.class, new ModelElement.Deserializer());
        builder.registerTypeAdapter(ModelElementFace.class, new ModelElementFace.Deserializer());
        builder.registerTypeAdapter(ModelElementTexture.class, new ModelElementTexture.Deserializer());
        builder.registerTypeAdapter(Transformation.class, new Transformation.Deserializer());
        builder.registerTypeAdapter(ModelTransformation.class, new ModelTransformation.Deserializer());
        builder.registerTypeAdapter(ModelOverride.class, new ModelOverride.Deserializer());
        builder.registerTypeAdapter(TransformationOverride.class, new TransformationOverride.Deserializer());
        GSON = builder.create();
    }

    @Unique
    private List<TransformationOverride> transformationOverrides;

    @Override
    @Unique
    public void setTransformationOverrides(List<TransformationOverride> override) {
        this.transformationOverrides = override;
    }

    @Override
    @Unique
    public List<TransformationOverride> getTransformationOverrides(JsonUnbakedModel parent) {
        if(transformationOverrides != null && !transformationOverrides.isEmpty()) {
            return transformationOverrides;
        }

        return parent != null ? ((JsonUnbakedModelTransformationOverrideManipulator) parent).getTransformationOverrides(((JsonUnbakedModelAccessor) parent).getParent()) : Collections.emptyList();
    }

    @Inject(
            method = "bake(Lnet/minecraft/client/render/model/ModelLoader;Lnet/minecraft/client/render/model/json/JsonUnbakedModel;Ljava/util/function/Function;Lnet/minecraft/client/render/model/ModelBakeSettings;Lnet/minecraft/util/Identifier;Z)Lnet/minecraft/client/render/model/BakedModel;",
            at = @At("RETURN"), cancellable = true)
    private void onReturn(ModelLoader loader, JsonUnbakedModel parent, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings settings, Identifier id, boolean hasDepth, CallbackInfoReturnable<BakedModel> cir) {
        BakedModel model = cir.getReturnValue();
        if(model instanceof BasicBakedModel) {
            ((ModelTransformationOverrideManipulator) model).setTransformationOverrides(new TransformationOverrideList(getTransformationOverrides(parent)));
        }
    }
}
