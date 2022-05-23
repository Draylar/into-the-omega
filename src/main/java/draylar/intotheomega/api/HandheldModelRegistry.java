package draylar.intotheomega.api;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

/**
 * Allows for the registration of minimized 16x inventory/GUI model variants for items.
 *
 * <p>
 * When designing art for larger weapons, using a larger canvas size (32x, 64x) alongside a larger model scale is preferred.
 * This has the negative effect of poor looking, non-fitting GUI item textures.
 * To solve this problem, developers can sprite a separate 16x icon for their item which only applies
 *   when the item is rendered on the ground or in a GUI.
 *
 * <p>
 * To register an item to this registry, see {@link HandheldModelRegistry#registerWithVariant(Item, ModelIdentifier, Identifier)}:
 *
 * <pre>{@code
 * // We assume the given item has a "default" model sprite that is larger than 16x, such as 32x or 64x.
 * // This sprite will still apply when rendered in a mode such as ModelTransformation#thirdPersonRightHand.
 * HandheldModelRegistry.registerWithVariant(ModItems.EXAMPLE_ITEM, new ModelIdentifier("mod:example_item#gui_variant"), Mod.id("item/example_item_gui"));
 * }</pre>
 */
@Environment(EnvType.CLIENT)
public class HandheldModelRegistry {

    private static final Map<Item, ModelIdentifier> HANDHELD_MODEL_IDS = new HashMap<>();

    public static boolean contains(Item item) {
        return HANDHELD_MODEL_IDS.containsKey(item);
    }

    public static void register(Item item, ModelIdentifier id) {
        HANDHELD_MODEL_IDS.put(item, id);
    }

    public static void registerWithVariant(Item item, ModelIdentifier id, Identifier variantModelLocation) {
        HANDHELD_MODEL_IDS.put(item, id);

        // extra models
        ModelLoadingRegistry.INSTANCE.registerAppender((manager, out) -> out.accept(id));

        ModelLoadingRegistry.INSTANCE.registerVariantProvider(manager -> (variantID, context) -> {
            if(variantID.equals(id)) {
                return context.loadModel(variantModelLocation);
            }

            return null;
        });
    }

    public static ModelIdentifier get(Item item) {
        return HANDHELD_MODEL_IDS.get(item);
    }

    private HandheldModelRegistry() {
        // NO-OP
    }
}
