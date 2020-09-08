package draylar.intotheomega.registry;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.item.*;
import draylar.intotheomega.material.OmegaArmorMaterial;
import draylar.intotheomega.material.OmegaToolMaterial;
import me.crimsondawn45.fabricshieldlib.object.TestEvent;
import me.crimsondawn45.fabricshieldlib.util.ShieldRegistry;
import me.crimsondawn45.fabricshieldlib.util.event.ShieldEvent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class OmegaItems {

    // omega tools
    public static final Item OMEGA_SWORD = register("omega_sword", new OmegaSwordItem(OmegaToolMaterial.INSTANCE, 3, -2.8f, new Item.Settings().group(IntoTheOmega.GROUP).rarity(Rarity.EPIC)));
    public static final Item OMEGA_DAGGER = register("omega_dagger", new OmegaDaggerItem(OmegaToolMaterial.INSTANCE, 0, -1f, new Item.Settings().group(IntoTheOmega.GROUP).rarity(Rarity.EPIC)));
    public static final Item OMEGA_PICKAXE = register("omega_pickaxe", new PickaxeItem(OmegaToolMaterial.INSTANCE, 0, -2.2f, new Item.Settings().group(IntoTheOmega.GROUP).rarity(Rarity.EPIC)));
    public static final Item OMEGA_HOE = register("omega_hoe", new HoeItem(OmegaToolMaterial.INSTANCE, -1, -2.2f, new Item.Settings().group(IntoTheOmega.GROUP).rarity(Rarity.EPIC)));
    public static final Item OMEGA_SHOVEL = register("omega_shovel", new ShovelItem(OmegaToolMaterial.INSTANCE, -1, -2.2f, new Item.Settings().group(IntoTheOmega.GROUP).rarity(Rarity.EPIC)));
    public static final Item OMEGA_AXE = register("omega_axe", new AxeItem(OmegaToolMaterial.INSTANCE, 4, -3.2f, new Item.Settings().group(IntoTheOmega.GROUP).rarity(Rarity.EPIC)));

    // themed end loot
    public static final Item CRYSTALIA = register("crystalia", new CrystaliaItem(new Item.Settings().group(IntoTheOmega.GROUP).rarity(Rarity.EPIC).maxCount(1)));
    public static final Item SEVENTH_PILLAR = register("seventh_pillar", new SeventhPillarItem(new Item.Settings().group(IntoTheOmega.GROUP).rarity(Rarity.EPIC).maxCount(1)));
    public static final Item INANIS = register("inanis", new InanisItem(new Item.Settings().group(IntoTheOmega.GROUP).rarity(Rarity.EPIC).maxCount(1)));
    public static final Item ABYSSAL_BOOTS = register("abyssal_boots", new AbyssalBoots(new Item.Settings()));

    // armor
    public static final Item OMEGA_HELMET = register("omega_helmet", new ArmorItem(OmegaArmorMaterial.INSTANCE, EquipmentSlot.HEAD, new Item.Settings().group(IntoTheOmega.GROUP).rarity(Rarity.EPIC)));
    public static final Item OMEGA_CHESTPLATE = register("omega_chestplate", new ArmorItem(OmegaArmorMaterial.INSTANCE, EquipmentSlot.CHEST, new Item.Settings().group(IntoTheOmega.GROUP).rarity(Rarity.EPIC)));
    public static final Item OMEGA_LEGGINGS = register("omega_leggings", new ArmorItem(OmegaArmorMaterial.INSTANCE, EquipmentSlot.LEGS, new Item.Settings().group(IntoTheOmega.GROUP).rarity(Rarity.EPIC)));
    public static final Item OMEGA_BOOTS = register("omega_boots", new ArmorItem(OmegaArmorMaterial.INSTANCE, EquipmentSlot.FEET, new Item.Settings().group(IntoTheOmega.GROUP).rarity(Rarity.EPIC)));

    // materials
    public static final Item OMEGA_CRYSTAL = register("omega_crystal", new Item(new Item.Settings().rarity(Rarity.EPIC).group(IntoTheOmega.GROUP)));
    public static final Item DRAGON_SCALE = register("dragon_scale", new Item(new Item.Settings().rarity(Rarity.EPIC).group(IntoTheOmega.GROUP)));

    // accessories
    public static final Item WARDING_OMEGA = register("warding_omega", new WardingOmegaItem(new Item.Settings().rarity(Rarity.EPIC).group(IntoTheOmega.GROUP).maxCount(1)));
    public static final Item OMEGA_SHIELD = register("omega_shield", new OmegaShieldItem(new Item.Settings().rarity(Rarity.EPIC).group(IntoTheOmega.GROUP).maxCount(1), 20 * 2, 1250, OMEGA_CRYSTAL));
    public static final Item VOID_AIR_IN_A_BOTTLE = register("void_air_in_a_bottle", new VoidAirBottleItem(new Item.Settings().rarity(Rarity.EPIC).group(IntoTheOmega.GROUP).maxCount(1)));
    public static final Item CLOAK_OF_VOIDING = register("cloak_of_voiding", new CloakOfVoidingItem(new Item.Settings().rarity(Rarity.EPIC).group(IntoTheOmega.GROUP).maxCount(1)));
//    public static final Item BREATH_OF_THE_END = register("breath_of_the_end", new Item(new Item.Settings().rarity(Rarity.EPIC).group(IntoTheOmega.GROUP).maxCount(1)));
    public static final Item DRAGON_EYE = register("dragon_eye", new DragonEyeItem(new Item.Settings().rarity(Rarity.EPIC).group(IntoTheOmega.GROUP).maxCount(1))); // crit chance + 10%

    // misc
//    public static final Item TRUE_EOE_SPAWN_EGG = register("true_eye_of_ender_spawn_egg", new SpawnEggItem(OmegaEntities.TRUE_EYE_OF_ENDER, 0, 0, new Item.Settings().group(IntoTheOmega.GROUP)));

    private static <T extends Item> T register(String name, T item) {
        return Registry.register(Registry.ITEM, IntoTheOmega.id(name), item);
    }

    public static void init() {
        ShieldRegistry.registerItemEvent(OMEGA_SHIELD, new ShieldEvent(true, true, true) {
            @Override
            public void whileHolding(LivingEntity defender, int level, Hand hand, ItemStack shield) {
                defender.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 22 * 5, 0, false, false));
            }
        });
    }

    private OmegaItems() {
        // NO-OP
    }
}
