package draylar.intotheomega.mixin.transformation;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import draylar.intotheomega.api.client.TransformationOverride;
import draylar.intotheomega.impl.JsonUnbakedModelTransformationOverrideManipulator;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.util.JsonHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.lang.reflect.Type;
import java.util.List;

@Mixin(JsonUnbakedModel.Deserializer.class)
public class JsonUnbakedModelDeserializerMixin {

    @Inject(
            method = "deserialize",
            at = @At("RETURN"), cancellable = true)
    private void addTransformationOverrides(JsonElement jsonElement, Type type, JsonDeserializationContext context, CallbackInfoReturnable<JsonUnbakedModel> cir) {
        JsonUnbakedModel currentRet = cir.getReturnValue();
        List<TransformationOverride> overrides = deserializeOverrides(context, jsonElement.getAsJsonObject());

        if(!overrides.isEmpty()) {
            ((JsonUnbakedModelTransformationOverrideManipulator) currentRet).setTransformationOverrides(overrides);
        }

        cir.setReturnValue(currentRet);
    }

    @Unique
    private List<TransformationOverride> deserializeOverrides(JsonDeserializationContext context, JsonObject object) {
        List<TransformationOverride> list = Lists.newArrayList();

        if (object.has("transformation_overrides")) {
            JsonArray jsonArray = JsonHelper.getArray(object, "transformation_overrides");
            for (JsonElement jsonElement : jsonArray) {
                list.add(context.deserialize(jsonElement, TransformationOverride.class));
            }
        }

        return list;
    }
}
