package draylar.intotheomega.mixin;

import net.minecraft.client.render.model.json.Transformation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(Transformation.Deserializer.class)
public class TransformationDeserializerMixin {

    @ModifyConstant(method = "deserialize(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Lnet/minecraft/client/render/model/json/Transformation;", constant = @Constant(floatValue = 4.0f))
    private float modifyMaxTransformationScaleMaximum(float defaultMaximum) {
        return 16.0f;
    }

    @ModifyConstant(method = "deserialize(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Lnet/minecraft/client/render/model/json/Transformation;", constant = @Constant(floatValue = -4.0f))
    private float modifyMaxTransformationScaleMinimum(float defaultMinimum) {
        return -16.0f;
    }
}
