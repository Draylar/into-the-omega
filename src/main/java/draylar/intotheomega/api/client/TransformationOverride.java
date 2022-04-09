package draylar.intotheomega.api.client;

import com.google.common.collect.Maps;
import com.google.gson.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.ModelPredicateProvider;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.render.model.json.Transformation;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class TransformationOverride {

    private final ModelTransformation transformation;
    private final Map<Identifier, Float> predicateToThresholds;

    public TransformationOverride(ModelTransformation transformation, Map<Identifier, Float> predicateToThresholds) {
        this.transformation = transformation;
        this.predicateToThresholds = predicateToThresholds;
    }

    public ModelTransformation getTransformation() {
        return this.transformation;
    }

    boolean matches(ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity entity) {
        Item item = stack.getItem();
        Iterator<Map.Entry<Identifier, Float>> var5 = this.predicateToThresholds.entrySet().iterator();

        Map.Entry entry;
        ModelPredicateProvider modelPredicateProvider;
        do {
            if (!var5.hasNext()) {
                return true;
            }

            entry = var5.next();
            modelPredicateProvider = ModelPredicateProviderRegistry.get(item, (Identifier)entry.getKey());
        } while(modelPredicateProvider != null && !(modelPredicateProvider.call(stack, world, entity, 0) < (Float)entry.getValue()));

        return false;
    }

    @Environment(EnvType.CLIENT)
    public static class Deserializer implements JsonDeserializer<TransformationOverride> {

        public Deserializer() {

        }

        @Override
        public TransformationOverride deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            ModelTransformation transformation = JsonHelper.deserialize(jsonObject, "display", context, ModelTransformation.class);
            Map<Identifier, Float> map = this.deserializePredicateRequirements(jsonObject);
            return new TransformationOverride(transformation, map);
        }

        protected Map<Identifier, Float> deserializePredicateRequirements(JsonObject object) {
            Map<Identifier, Float> map = Maps.newLinkedHashMap();
            JsonObject jsonObject = JsonHelper.getObject(object, "predicate");

            for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                map.put(new Identifier(entry.getKey()), JsonHelper.asFloat(entry.getValue(), entry.getKey()));
            }

            return map;
        }
    }
}
