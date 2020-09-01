package draylar.intotheomega.registry;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.item.*;
import draylar.intotheomega.material.OmegaArmorMaterial;
import draylar.intotheomega.material.OmegaToolMaterial;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class OmegaItems {

    // tools
    public static final Item OMEGA_SWORD = register("omega_sword", new OmegaSwordItem(OmegaToolMaterial.INSTANCE, 3, -2.8f, new Item.Settings().group(IntoTheOmega.GROUP).rarity(Rarity.EPIC)));
    public static final Item OMEGA_PICKAXE = register("omega_pickaxe", new PickaxeItem(OmegaToolMaterial.INSTANCE, 0, -2.2f, new Item.Settings().group(IntoTheOmega.GROUP).rarity(Rarity.EPIC)));
    public static final Item OMEGA_HOE = register("omega_hoe", new HoeItem(OmegaToolMaterial.INSTANCE, -1, -2.2f, new Item.Settings().group(IntoTheOmega.GROUP).rarity(Rarity.EPIC)));
    public static final Item OMEGA_SHOVEL = register("omega_shovel", new ShovelItem(OmegaToolMaterial.INSTANCE, -1, -2.2f, new Item.Settings().group(IntoTheOmega.GROUP).rarity(Rarity.EPIC)));
    public static final Item OMEGA_AXE = register("omega_axe", new AxeItem(OmegaToolMaterial.INSTANCE, 4, -3.2f, new Item.Settings().group(IntoTheOmega.GROUP).rarity(Rarity.EPIC)));
    public static final Item OMEGA_SPEAR = register("omega_spear", new OmegaSpearItem(new Item.Settings().group(IntoTheOmega.GROUP).rarity(Rarity.EPIC)));

    // non-standard tools
    public static final Item OMEGA_SCYTHE = register("final_slice", new OmegaScytheItem(OmegaToolMaterial.INSTANCE, 0, 0, new Item.Settings().group(IntoTheOmega.GROUP)));

    // armor
    public static final Item OMEGA_HELMET = register("omega_helmet", new ArmorItem(OmegaArmorMaterial.INSTANCE, EquipmentSlot.HEAD, new Item.Settings().group(IntoTheOmega.GROUP).rarity(Rarity.EPIC)));
    public static final Item OMEGA_CHESTPLATE = register("omega_chestplate", new ArmorItem(OmegaArmorMaterial.INSTANCE, EquipmentSlot.CHEST, new Item.Settings().group(IntoTheOmega.GROUP).rarity(Rarity.EPIC)));
    public static final Item OMEGA_LEGGINGS = register("omega_leggings", new ArmorItem(OmegaArmorMaterial.INSTANCE, EquipmentSlot.LEGS, new Item.Settings().group(IntoTheOmega.GROUP).rarity(Rarity.EPIC)));
    public static final Item OMEGA_BOOTS = register("omega_boots", new ArmorItem(OmegaArmorMaterial.INSTANCE, EquipmentSlot.FEET, new Item.Settings().group(IntoTheOmega.GROUP).rarity(Rarity.EPIC)));

    // materials
    public static final Item OMEGA_CRYSTAL = register("omega_crystal", new Item(new Item.Settings().rarity(Rarity.EPIC).group(IntoTheOmega.GROUP)));

    // accessories
    public static final Item WARDING_OMEGA = register("warding_omega", new WardingOmegaItem(new Item.Settings().rarity(Rarity.EPIC).group(IntoTheOmega.GROUP).maxCount(1)));
    public static final Item OMEGA_SHIELD = register("omega_shield", new OmegaShieldItem(new Item.Settings().rarity(Rarity.EPIC).group(IntoTheOmega.GROUP).maxCount(1), 20 * 2, 1250, OMEGA_CRYSTAL));

    // misc
//    public static final Item TRUE_EOE_SPAWN_EGG = register("true_eye_of_ender_spawn_egg", new SpawnEggItem(OmegaEntities.TRUE_EYE_OF_ENDER, 0, 0, new Item.Settings().group(IntoTheOmega.GROUP)));

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
