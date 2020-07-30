package draylar.intotheomega.registry;

import draylar.intotheomega.IntoTheOmega;
import net.minecraft.item.Item;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class OmegaItems {

    public static final Item OMEGA_CRYSTAL = register("omega_crystal", new Item(new Item.Settings().rarity(Rarity.EPIC).group(IntoTheOmega.GROUP)));

    private static <T extends Item> T register(String name, T item) {
        return Registry.register(Registry.ITEM, IntoTheOmega.id(name), item);
    }

    public static void init() {
        // NO-OP
    }

    private OmegaItems() {
        // NO-OP
    }
}
