package draylar.intotheomega.registry;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.enchantment.BaneOfTheEndEnchantment;
import draylar.intotheomega.impl.OmegaManipulator;
import net.minecraft.enchantment.*;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class OmegaEnchantments {

    private static final EquipmentSlot[] ALL_ARMOR = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};

    // vanilla omega dupes
    public static final Enchantment PROTECTION = registerOmega("protection", new ProtectionEnchantment(Enchantment.Rarity.COMMON, ProtectionEnchantment.Type.ALL, ALL_ARMOR));
    public static final Enchantment FIRE_PROTECTION = registerOmega("fire_protection", new ProtectionEnchantment(Enchantment.Rarity.UNCOMMON, ProtectionEnchantment.Type.FIRE, ALL_ARMOR));
    public static final Enchantment FEATHER_FALLING = registerOmega("feather_falling", new ProtectionEnchantment(Enchantment.Rarity.UNCOMMON, ProtectionEnchantment.Type.FALL, ALL_ARMOR));
    public static final Enchantment BLAST_PROTECTION = registerOmega("blast_protection", new ProtectionEnchantment(Enchantment.Rarity.RARE, ProtectionEnchantment.Type.EXPLOSION, ALL_ARMOR));
    public static final Enchantment PROJECTILE_PROTECTION = registerOmega("projectile_protection", new ProtectionEnchantment(Enchantment.Rarity.UNCOMMON, ProtectionEnchantment.Type.PROJECTILE, ALL_ARMOR));
    public static final Enchantment RESPIRATION = registerOmega("respiration", new RespirationEnchantment(Enchantment.Rarity.RARE, ALL_ARMOR));
    public static final Enchantment AQUA_AFFINITY = registerOmega("aqua_affinity", new AquaAffinityEnchantment(Enchantment.Rarity.RARE, ALL_ARMOR));
    public static final Enchantment THORNS = registerOmega("thorns", new ThornsEnchantment(Enchantment.Rarity.VERY_RARE, ALL_ARMOR));
    public static final Enchantment DEPTH_STRIDER = registerOmega("depth_strider", new DepthStriderEnchantment(Enchantment.Rarity.RARE, ALL_ARMOR));
    public static final Enchantment FROST_WALKER = registerOmega("frost_walker", new FrostWalkerEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.FEET));
    public static final Enchantment BINDING_CURSE = registerOmega("binding_curse", new BindingCurseEnchantment(Enchantment.Rarity.VERY_RARE, ALL_ARMOR));
    public static final Enchantment SOUL_SPEED = registerOmega("soul_speed", new SoulSpeedEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.FEET));
    public static final Enchantment SHARPNESS = registerOmega("sharpness", new DamageEnchantment(Enchantment.Rarity.COMMON, 0, EquipmentSlot.MAINHAND));
    public static final Enchantment SMITE = registerOmega("smite", new DamageEnchantment(Enchantment.Rarity.UNCOMMON, 1, EquipmentSlot.MAINHAND));
    public static final Enchantment BANE_OF_ARTHROPODS = registerOmega("bane_of_arthropods", new DamageEnchantment(Enchantment.Rarity.UNCOMMON, 2, EquipmentSlot.MAINHAND));
    public static final Enchantment KNOCKBACK = registerOmega("knockback", new KnockbackEnchantment(Enchantment.Rarity.UNCOMMON, EquipmentSlot.MAINHAND));
    public static final Enchantment FIRE_ASPECT = registerOmega("fire_aspect", new FireAspectEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.MAINHAND));
    public static final Enchantment LOOTING = registerOmega("looting", new LuckEnchantment(Enchantment.Rarity.RARE, EnchantmentTarget.WEAPON, EquipmentSlot.MAINHAND));
    public static final Enchantment SWEEPING = registerOmega("sweeping", new SweepingEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.MAINHAND));
    public static final Enchantment EFFICIENCY = registerOmega("efficiency", new EfficiencyEnchantment(Enchantment.Rarity.COMMON, EquipmentSlot.MAINHAND));
    public static final Enchantment SILK_TOUCH = registerOmega("silk_touch", new SilkTouchEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.MAINHAND));
    public static final Enchantment UNBREAKING = registerOmega("unbreaking", new UnbreakingEnchantment(Enchantment.Rarity.UNCOMMON, EquipmentSlot.MAINHAND));
    public static final Enchantment FORTUNE = registerOmega("fortune", new LuckEnchantment(Enchantment.Rarity.RARE, EnchantmentTarget.DIGGER, EquipmentSlot.MAINHAND));
    public static final Enchantment POWER = registerOmega("power", new PowerEnchantment(Enchantment.Rarity.COMMON, EquipmentSlot.MAINHAND));
    public static final Enchantment PUNCH = registerOmega("punch", new PunchEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.MAINHAND));
    public static final Enchantment FLAME = registerOmega("flame", new FlameEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.MAINHAND));
    public static final Enchantment INFINITY = registerOmega("infinity", new InfinityEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.MAINHAND));
    public static final Enchantment LUCK_OF_THE_SEA = registerOmega("luck_of_the_sea", new LuckEnchantment(Enchantment.Rarity.RARE, EnchantmentTarget.FISHING_ROD, EquipmentSlot.MAINHAND));
    public static final Enchantment LURE = registerOmega("lure", new LureEnchantment(Enchantment.Rarity.RARE, EnchantmentTarget.FISHING_ROD, EquipmentSlot.MAINHAND));
    public static final Enchantment LOYALTY = registerOmega("loyalty", new LoyaltyEnchantment(Enchantment.Rarity.UNCOMMON, EquipmentSlot.MAINHAND));
    public static final Enchantment IMPALING = registerOmega("impaling", new ImpalingEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.MAINHAND));
    public static final Enchantment RIPTIDE = registerOmega("riptide", new RiptideEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.MAINHAND));
    public static final Enchantment CHANNELING = registerOmega("channeling", new ChannelingEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.MAINHAND));
    public static final Enchantment MULTISHOT = registerOmega("multishot", new MultishotEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.MAINHAND));
    public static final Enchantment QUICK_CHARGE = registerOmega("quick_charge", new QuickChargeEnchantment(Enchantment.Rarity.UNCOMMON, EquipmentSlot.MAINHAND));
    public static final Enchantment PIERCING = registerOmega("piercing", new PiercingEnchantment(Enchantment.Rarity.COMMON, EquipmentSlot.MAINHAND));
    public static final Enchantment MENDING = registerOmega("mending", new MendingEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.values()));
    public static final Enchantment VANISHING_CURSE = registerOmega("vanishing_curse", new VanishingCurseEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.values()));

    // custom enchantments
    public static final Enchantment BANE_OF_THE_END = register("bane_of_the_end", new BaneOfTheEndEnchantment());

    private static Enchantment register(String name, Enchantment enchantment) {
        return Registry.register(Registry.ENCHANTMENT, IntoTheOmega.id(name), enchantment);
    }

    private static Enchantment registerOmega(String name, Enchantment enchantment) {
        ((OmegaManipulator) enchantment).setOmega(true);
        ((OmegaManipulator) enchantment).setVanilla(Registry.ENCHANTMENT.get(new Identifier(name)));
        return Registry.register(Registry.ENCHANTMENT, IntoTheOmega.id(String.format("omega_%s", name)), enchantment);
    }

    public static void init() {

    }
}
