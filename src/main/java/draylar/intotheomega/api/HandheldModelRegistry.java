package draylar.intotheomega.api;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class HandheldModelRegistry {

    private static final Map<Item, ModelIdentifier> vals = new HashMap<>();

    public static boolean contains(Item item) {
        return vals.containsKey(item);
    }

    public static void register(Item item, ModelIdentifier id) {
        vals.put(item, id);
    }

    public static void registerWithVariant(Item item, ModelIdentifier id, Identifier variantLocation) {
        vals.put(item, id);

        // extra models
        ModelLoadingRegistry.INSTANCE.registerAppender((manager, out) -> out.accept(id));

        ModelLoadingRegistry.INSTANCE.registerVariantProvider(manager -> (variantID, context) -> {
            if(variantID.equals(id)) {
                return context.loadModel(variantLocation);
            }

            return null;
        });
    }

    public static ModelIdentifier get(Item item) {
        return vals.get(item);
    }

    private HandheldModelRegistry() {
        // NO-OP
    }
}
