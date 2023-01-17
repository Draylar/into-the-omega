package draylar.intotheomega.mixin.model;

import com.google.gson.JsonObject;
import net.minecraft.client.render.model.json.ModelElement;
import net.minecraft.util.JsonHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(ModelElement.Deserializer.class)
public class ModelElementPatchMixin {

    @Overwrite
    private float deserializeRotationAngle(JsonObject object) {
        return JsonHelper.getFloat(object, "angle");
    }
}
