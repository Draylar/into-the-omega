package draylar.intotheomega.registry;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.item.*;
import draylar.intotheomega.item.abyss.AbyssWalkersItem;
import draylar.intotheomega.item.core.DragonEyeItem;
import draylar.intotheomega.item.core.DragonslayerArmorItem;
import draylar.intotheomega.item.crimson.CrimsonMajestyArmorItem;
import draylar.intotheomega.item.crimson.StarfallArmorItem;
import draylar.intotheomega.item.generic.*;
import draylar.intotheomega.item.dragon.*;
import draylar.intotheomega.item.enigma.*;
import draylar.intotheomega.item.ice.*;
import draylar.intotheomega.item.matrix.*;
import draylar.intotheomega.item.memory.MemoryItem;
import draylar.intotheomega.item.slime.*;
import draylar.intotheomega.item.starfall.StardustWingsItem;
import draylar.intotheomega.item.starfall.StarfallTomeItem;
import draylar.intotheomega.item.weapon.bow.LevitationBlasterItem;
import draylar.intotheomega.item.weapon.sword.ShulkerSlicerItem;
import draylar.intotheomega.material.*;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.registry.Registry;

import static draylar.intotheomega.IntoTheOmega.GROUP;
import static net.minecraft.entity.EquipmentSlot.*;
import static net.minecraft.util.Rarity.EPIC;
import static net.minecraft.util.Rarity.RARE;

public class OmegaItems {

    // omega tools
    public static final Item OMEGA_SWORD = register("omega_sword", new OmegaSwordItem(OmegaToolMaterial.INSTANCE, 3, -2.8f, new Item.Settings().group(GROUP).rarity(EPIC)));
    public static final Item OMEGA_DAGGER = register("omega_dagger", new OmegaDaggerItem(OmegaToolMaterial.INSTANCE, 0, -1f, new Item.Settings().group(GROUP).rarity(EPIC)));
    public static final Item OMEGA_PICKAXE = register("omega_pickaxe", new PickaxeItem(OmegaToolMaterial.INSTANCE, 0, -2.2f, new Item.Settings().group(GROUP).rarity(EPIC)));
    public static final Item OMEGA_HOE = register("omega_hoe", new HoeItem(OmegaToolMaterial.INSTANCE, -1, -2.2f, new Item.Settings().group(GROUP).rarity(EPIC)));
    public static final Item OMEGA_SHOVEL = register("omega_shovel", new ShovelItem(OmegaToolMaterial.INSTANCE, -1, -2.2f, new Item.Settings().group(GROUP).rarity(EPIC)));
    public static final Item OMEGA_AXE = register("omega_axe", new AxeItem(OmegaToolMaterial.INSTANCE, 4, -3.2f, new Item.Settings().group(GROUP).rarity(EPIC)));

    // themed end loot
    public static final Item INANIS = register("inanis", new InanisItem(new Item.Settings().group(GROUP).rarity(EPIC).maxCount(1), "inanis"));
    public static final Item DUAL_INANIS = register("dual_inanis", new DualInanisItem(new Item.Settings().group(GROUP).rarity(EPIC).maxCount(1), "dual_inanis"));
    public static final Item DUAL_CORE = register("dual_core", new Item(new Item.Settings().group(GROUP).rarity(EPIC)));
    public static final Item SECOND_FUSION = register("second_fusion", new SecondFusionItem(new Item.Settings().group(GROUP).rarity(EPIC).maxCount(1)));
    public static final Item VIOLET_UNION = register("violet_union", new VioletUnionItem(new Item.Settings().group(GROUP).rarity(EPIC).maxCount(1)));
    public static final Item VARIANT_SPARK = register("variant_spark", new VariantSparkItem(new Item.Settings().group(GROUP).rarity(EPIC).maxDamage(2000)));
    public static final Item DUAL_DRAGON_HELMET = register("dual_dragon_helmet", new Item(new Item.Settings().group(GROUP).rarity(EPIC)));
    public static final Item DUAL_DRAGON_CHESTPLATE = register("dual_dragon_chestplate", new Item(new Item.Settings().group(GROUP).rarity(EPIC)));
    public static final Item DUAL_DRAGON_LEGGINGS = register("dual_dragon_leggings", new Item(new Item.Settings().group(GROUP).rarity(EPIC)));
    public static final Item DUAL_DRAGON_BOOTS = register("dual_dragon_boots", new Item(new Item.Settings().group(GROUP).rarity(EPIC)));

    public static final Item FORGOTTEN_EYE = register("forgotten_eye", new ForgottenEyeItem(new Item.Settings().group(GROUP).rarity(EPIC).maxCount(1)));
    public static final Item FERLIOUS = register("ferlious", new FerliousItem(new Item.Settings().group(GROUP).rarity(EPIC).maxCount(1)));
    public static final Item EMPEREAL_FERLIOUS = register("empereal_ferlious", new EmperealFerliousItem(new Item.Settings().group(GROUP).rarity(EPIC).maxCount(1)));
    public static final Item HORIZON = register("horizon", new Item(new Item.Settings().group(GROUP).rarity(EPIC).maxCount(1)));
    public static final Item TRIUMPHANT_HORIZON = register("triumphant_horizon", new Item(new Item.Settings().group(GROUP).rarity(EPIC).maxCount(1)));
//    public static final Item DRAZELIA = register("drazelia", new Item(new Item.Settings().group(IntoTheOmega.GROUP).rarity(Rarity.EPIC).maxCount(1)));
    public static final Item ABYSS_WALKERS = register("abyss_boots", new AbyssWalkersItem(new Item.Settings().group(GROUP).rarity(EPIC)));

    // matrix
    public static final Item CRYSTALIA = register("crystalia", new CrystaliaItem(new Item.Settings().group(GROUP).rarity(EPIC).maxCount(1)));
    public static final Item BENEATH_CRYSTALIA = register("beneath_crystalia", new BeneathCrystaliaItem(new Item.Settings().group(GROUP).rarity(EPIC).maxCount(1)));
    public static final Item MATRIX_BOMB = register("matrix_bomb", new MatrixBombItem(new Item.Settings().group(GROUP).rarity(EPIC).maxCount(8)));
    public static final Item VOID_MATRIX_SPAWN_EGG = register("void_matrix_spawn_egg", new UncoloredSpawnEggItem(OmegaEntities.VOID_MATRIX, new Item.Settings().group(GROUP).rarity(EPIC)));
//    public static final Item MATRITE_SPAWN_eGG = register("matrite_spawn_egg", new UncoloredSpawnEggItem(OmegaEntities.MATRITE, new Item.Settings().group(IntoTheOmega.GROUP).rarity(Rarity.EPIC)));
    public static final Item MATRIX_BLASTER = register("matrix_blaster", new MatrixBlasterItem(new Item.Settings().group(GROUP).rarity(EPIC).maxCount(1)));
    public static final Item MATRITE_ORBITAL = register("matrite_orbital", new MatriteOrbitalItem(new Item.Settings().group(GROUP).rarity(EPIC).maxCount(1)));
    public static final Item MATRIX_RUNE = register("matrix_rune", new Item(new Item.Settings().group(GROUP).rarity(EPIC).maxCount(16)));
    public static final Item MATRIX_CHARM = register("matrix_charm", new MatrixCharmItem(new Item.Settings().rarity(EPIC).group(GROUP).maxCount(1)));
    public static final Item EXPLOSIVE_FOCUS = register("explosive_focus", new ExplosiveFocusItem(new Item.Settings().group(GROUP).rarity(EPIC).maxCount(1)));
    public static final Item MATRIX_LENS = register("matrix_lens", new MatrixLensItem(new Item.Settings().group(GROUP).rarity(EPIC).maxCount(1)));
    public static final Item EXPLOSION_IN_A_BOTTLE = register("explosion_in_a_bottle", new ExplosiveDoubleJumpItem(new Item.Settings().group(GROUP).rarity(EPIC).maxCount(1), 2));
    // todo: rename explosion in a bottle
    public static final Item MATRIX_PICKAXE = register("matrix_pickaxe", new PickaxeItem(MatrixToolMaterial.INSTANCE, 3, -2.3f, new Item.Settings().group(GROUP).rarity(EPIC).maxCount(1)));


    // obsidian spike
    public static final Item SEVENTH_PILLAR = register("seventh_pillar", new SeventhPillarItem(new Item.Settings().group(GROUP).rarity(EPIC).maxCount(1), 14));
    public static final Item AWAKENED_SEVENTH_PILLAR = register("awakened_seventh_pillar", new SeventhPillarItem(new Item.Settings().group(GROUP).rarity(EPIC).maxCount(1), 18));
    public static final Item DARK_SOUL = register("dark_soul", new Item(new Item.Settings().group(GROUP).rarity(EPIC).maxCount(1)));
    public static final Item OBSIDIAN_CORE = register("obsidian_core", new ObsidianCoreItem(new Item.Settings().group(GROUP).rarity(EPIC).maxCount(1)));
    public static final Item MIDNIGHT_HEART = register("midnight_heart", new MidnightHeartItem(new Item.Settings().group(GROUP).rarity(EPIC).maxCount(1), 8)); // todo: custom heart trinket slot?
    public static final Item ETERNAL_STAND = register("eternal_stand", new Item(new Item.Settings().group(GROUP).rarity(EPIC).maxCount(1)));
    public static final Item EBONY_EYE = register("ebony_eye", new EbonyEyeItem(new Item.Settings().group(GROUP).rarity(EPIC).maxCount(1)));
    public static final Item DARK_FLAME_BLASTER = register("dark_flame_blaster", new DarkFlameBlasterItem(new Item.Settings().group(GROUP).rarity(EPIC).maxCount(1).maxDamage(5)));
    public static final Item DARK_SOUL_HELMET = register("dark_soul_helmet", new ArmorItem(OmegaArmorMaterial.INSTANCE, HEAD, new Item.Settings().group(GROUP).rarity(EPIC)));
    public static final Item DARK_SOUL_CHESTPLATE = register("dark_soul_chestplate", new ArmorItem(OmegaArmorMaterial.INSTANCE, CHEST, new Item.Settings().group(GROUP).rarity(EPIC)));
    public static final Item DARK_SOUL_LEGGINGS = register("dark_soul_leggings", new ArmorItem(OmegaArmorMaterial.INSTANCE, LEGS, new Item.Settings().group(GROUP).rarity(EPIC)));
    public static final Item DARK_SOUL_BOOTS = register("dark_soul_boots", new ArmorItem(OmegaArmorMaterial.INSTANCE, FEET, new Item.Settings().group(GROUP).rarity(EPIC)));
    public static final Item DARK_ENIGMA = register("dark_enigma", new Item(new Item.Settings().group(GROUP).rarity(EPIC)));
    public static final Item ENIGMA_KING_SPAWN_EGG = register("enigma_king_spawn_egg", new UncoloredSpawnEggItem(OmegaEntities.ENIGMA_KING, new Item.Settings().group(GROUP).rarity(EPIC)));

    // standard dragonslayer set
    public static final Item DRAGONSLAYER_HELMET = register("dragonslayer_helmet", new DragonslayerArmorItem(HEAD, new Item.Settings().group(GROUP).rarity(EPIC)));
    public static final Item DRAGONSLAYER_CHESTPLATE = register("dragonslayer_chestplate", new DragonslayerArmorItem(CHEST, new Item.Settings().group(GROUP).rarity(EPIC)));
    public static final Item DRAGONSLAYER_LEGGINGS = register("dragonslayer_leggings", new DragonslayerArmorItem(LEGS, new Item.Settings().group(GROUP).rarity(EPIC)));
    public static final Item DRAGONSLAYER_BOOTS = register("dragonslayer_boots", new DragonslayerArmorItem(FEET, new Item.Settings().group(GROUP).rarity(EPIC)));
    public static final Item DRAGONSLAYER_SWORD = register("dragonslayer_sword", new SwordItem(DragonslayerToolMaterial.INSTANCE, 3, -2.8f, new Item.Settings().group(GROUP).rarity(EPIC)));
    public static final Item DRAGONSLAYER_PICKAXE = register("dragonslayer_pickaxe", new PickaxeItem(DragonslayerToolMaterial.INSTANCE, 0, -2.2f, new Item.Settings().group(GROUP).rarity(EPIC)));
    public static final Item DRAGONSLAYER_HOE = register("dragonslayer_hoe", new HoeItem(DragonslayerToolMaterial.INSTANCE, -1, -2.2f, new Item.Settings().group(GROUP).rarity(EPIC)));
    public static final Item DRAGONSLAYER_SHOVEL = register("dragonslayer_shovel", new ShovelItem(DragonslayerToolMaterial.INSTANCE, -1, -2.2f, new Item.Settings().group(GROUP).rarity(EPIC)));
    public static final Item DRAGONSLAYER_AXE = register("dragonslayer_axe", new AxeItem(DragonslayerToolMaterial.INSTANCE, 4, -3.2f, new Item.Settings().group(GROUP).rarity(EPIC)));

    // armor
    public static final Item OMEGA_HELMET = register("omega_helmet", new ArmorItem(OmegaArmorMaterial.INSTANCE, HEAD, new Item.Settings().group(GROUP).rarity(EPIC)));
    public static final Item OMEGA_CHESTPLATE = register("omega_chestplate", new ArmorItem(OmegaArmorMaterial.INSTANCE, CHEST, new Item.Settings().group(GROUP).rarity(EPIC)));
    public static final Item OMEGA_LEGGINGS = register("omega_leggings", new ArmorItem(OmegaArmorMaterial.INSTANCE, LEGS, new Item.Settings().group(GROUP).rarity(EPIC)));
    public static final Item OMEGA_BOOTS = register("omega_boots", new ArmorItem(OmegaArmorMaterial.INSTANCE, FEET, new Item.Settings().group(GROUP).rarity(EPIC)));

    // chorus
    public static final Item CHORUS_HELMET = register("chorus_helmet", new ChorusArmorItem(ChorusArmorMaterial.INSTANCE, HEAD, new Item.Settings().group(GROUP)));
    public static final Item CHORUS_CHESTPLATE = register("chorus_chestplate", new ChorusArmorItem(ChorusArmorMaterial.INSTANCE, CHEST, new Item.Settings().group(GROUP)));
    public static final Item CHORUS_LEGGINGS = register("chorus_leggings", new ChorusArmorItem(ChorusArmorMaterial.INSTANCE, LEGS, new Item.Settings().group(GROUP)));
    public static final Item CHORUS_BOOTS = register("chorus_boots", new ChorusArmorItem(ChorusArmorMaterial.INSTANCE, FEET, new Item.Settings().group(GROUP)));
    public static final Item CHORUS_SWORD = register("chorus_sword", new SwordItem(ToolMaterials.IRON, 5, -2.4f, new Item.Settings().group(GROUP)));
    public static final Item KNOWLEDGE_OF_THE_END = register("knowledge_of_the_end", new KnowledgeOfEndItem(new Item.Settings().group(GROUP).rarity(EPIC)));

    // ender-pearl content
    public static final Item PEARL_HELMET = register("pearl_helmet", new ArmorItem(PearlArmorMaterial.INSTANCE, HEAD, new Item.Settings().group(GROUP).rarity(EPIC)));
    public static final Item PEARL_CHESTPLATE = register("pearl_chestplate", new ArmorItem(PearlArmorMaterial.INSTANCE, CHEST, new Item.Settings().group(GROUP).rarity(EPIC)));
    public static final Item PEARL_LEGGINGS = register("pearl_leggings", new ArmorItem(PearlArmorMaterial.INSTANCE, LEGS, new Item.Settings().group(GROUP).rarity(EPIC)));
    public static final Item PEARL_BOOTS = register("pearl_boots", new ArmorItem(PearlArmorMaterial.INSTANCE, FEET, new Item.Settings().group(GROUP).rarity(EPIC)));
    public static final Item PEARL_SWORD = register("pearl_sword", new SwordItem(ToolMaterials.DIAMOND, 5, -2.2f, new Item.Settings().group(GROUP).rarity(EPIC)));
    public static final Item BOUND_EYE = register("bound_eye", new BoundEyeItem(new Item.Settings().group(GROUP).maxCount(1)));


    // materials
    public static final Item OMEGA_CRYSTAL = register("omega_crystal", new Item(new Item.Settings().rarity(EPIC).group(GROUP)));
    public static final Item DRAGON_SCALE = register("dragon_scale", new Item(new Item.Settings().rarity(EPIC).group(GROUP)));

    // accessories
    public static final Item WARDING_OMEGA = register("warding_omega", new WardingOmegaItem(new Item.Settings().rarity(EPIC).group(GROUP).maxCount(1)));
    public static final Item OMEGA_SHIELD = register("omega_shield", new OmegaShieldItem(new Item.Settings().rarity(EPIC).group(GROUP).maxCount(1), 20 * 2, 1250, OMEGA_CRYSTAL));
    public static final Item VOID_AIR_IN_A_BOTTLE = register("void_air_in_a_bottle", new DoubleJumpTrinketItem(new Item.Settings().rarity(EPIC).group(GROUP).maxCount(1), 2));
    public static final Item CLOAK_OF_VOIDING = register("cloak_of_voiding", new CloakOfVoidingItem(new Item.Settings().rarity(EPIC).group(GROUP).maxCount(1)));
//    public static final Item BREATH_OF_THE_END = register("breath_of_the_end", new Item(new Item.Settings().rarity(Rarity.EPIC).group(IntoTheOmega.GROUP).maxCount(1)));
    public static final Item DRAGON_EYE = register("dragon_eye", new DragonEyeItem(new Item.Settings().rarity(EPIC).group(GROUP).maxCount(1))); // crit chance + 10%

    // spawn eggs
    public static final Item CHORUS_COW_SPAWN_eGG = register("chorus_cow_spawn_egg", new UncoloredSpawnEggItem(OmegaEntities.CHORUS_COW, new Item.Settings().group(GROUP)));
    public static final Item FROSTED_EYE_SPAWN_EGG = register("frosted_eye_spawn_egg", new UncoloredSpawnEggItem(OmegaEntities.FROSTED_EYE, new Item.Settings().group(GROUP)));
    public static final Item FROSTED_ENDERMAN_SPAWN_EGG = register("frosted_enderman_spawn_egg", new UncoloredSpawnEggItem(OmegaEntities.FROSTED_ENDERMAN, new Item.Settings().group(GROUP)));
    public static final Item VOID_BEETLE_SPAWN_EGG = register("void_beetle_spawn_egg", new SpawnEggItem(OmegaEntities.VOID_BEETLE, 0xffffff, 0xffffff, new Item.Settings().group(GROUP)));

    // omega slime
    public static final Item OMEGA_SLIME = register("omega_slime", new Item(new Item.Settings().group(GROUP).rarity(EPIC)));
    public static final Item SLIMANIUS = register("slimanius", new Item(new Item.Settings().group(GROUP).rarity(EPIC)));
    public static final Item OMEGA_SLIME_HELMET = register("omega_slime_helmet", new ArmorItem(OmegaSlimeArmorMaterial.INSTANCE, HEAD, new Item.Settings().group(GROUP).rarity(EPIC)));
    public static final Item OMEGA_SLIME_CHESTPLATE = register("omega_slime_chestplate", new ArmorItem(OmegaSlimeArmorMaterial.INSTANCE, CHEST, new Item.Settings().group(GROUP).rarity(EPIC)));
    public static final Item OMEGA_SLIME_LEGGINGS = register("omega_slime_leggings", new ArmorItem(OmegaSlimeArmorMaterial.INSTANCE, LEGS, new Item.Settings().group(GROUP).rarity(EPIC)));
    public static final Item OMEGA_SLIME_BOOTS = register("omega_slime_boots", new ArmorItem(OmegaSlimeArmorMaterial.INSTANCE, FEET, new Item.Settings().group(GROUP).rarity(EPIC)));
    public static final Item SLIMEY_OMEGA_BALLOON = register("slimey_omega_balloon", new DoubleJumpTrinketItem(new Item.Settings().group(GROUP).rarity(EPIC), 3));
    public static final Item SWIRLED_OMEGA_JAM = register("swirled_omega_jam", new SwirledOmegaJamItem(new Item.Settings().group(GROUP).rarity(EPIC)));
    public static final Item OMEGA_SLIME_SQUIRTER = register("omega_slime_squirter", new SlimeSoakerItem(new Item.Settings().group(GROUP).rarity(EPIC).maxCount(1).maxDamage(1000)));
    public static final Item OMEGA_SLIME_SLAYER = register("omega_slime_slayer", new OmegaSlimeSlayerItem(ToolMaterials.DIAMOND, 10, -2.4f, new Item.Settings().group(GROUP).rarity(EPIC)));
    public static final Item OMEGA_SLIME_BLOCK = register("omega_slime_block", new BlockItem(OmegaBlocks.OMEGA_SLIME_BLOCK, new Item.Settings().group(GROUP).rarity(EPIC)));
    public static final Item OMEGA_SLIME_EMPEROR_SPAWN_EGG = register("omega_slime_emperor_spawn_egg", new UncoloredSpawnEggItem(OmegaEntities.OMEGA_SLIME_EMPEROR, new Item.Settings().group(GROUP).rarity(EPIC)));
    public static final Item OMEGA_SLIME_SPAWN_EGG = register("omega_slime_spawn_egg", new UncoloredSpawnEggItem(OmegaEntities.OMEGA_SLIME, new Item.Settings().group(GROUP).rarity(EPIC)));
    public static final Item SLIME_MOUNT = register("slime_mount", new SlimeMountItem(new Item.Settings().group(GROUP).rarity(EPIC)));
    public static final Item SLIME_TIME = register("slime_time", new MusicDiscItem(1, SoundEvents.BLOCK_SLIME_BLOCK_BREAK, new Item.Settings().group(GROUP).rarity(EPIC).maxCount(1)) {});
    public static final Item DESTINY_SWIRL = register("destiny_swirl", new DestinySwirlItem(new Item.Settings().group(GROUP).rarity(EPIC).maxCount(1)));
    public static final Item AURA_OF_SLIME = register("aura_of_slime", new Item(new Item.Settings().group(GROUP).rarity(EPIC).maxCount(1)));
    public static final Item OMEGA_SLIME_BUCKET = register("omega_slime_bucket", new BucketItem(OmegaFluids.OMEGA_SLIME_STILL, new Item.Settings().group(GROUP).maxCount(1)));
    public static final Item SWIRL_GRENADE = register("swirl_grenade", new SwirlGrenadeItem(new Item.Settings().maxCount(16).group(GROUP).rarity(EPIC)));
    public static final Item SLIMEFALL = register("slimefall", new SlimefallItem(new Item.Settings().group(GROUP).rarity(EPIC)));
    public static final Item SLIME_CROWN = register("slime_crown", new ArmorItem(ArmorMaterials.GOLD, HEAD, new Item.Settings().group(GROUP).rarity(EPIC)));

    // ice
    public static final Item ZERO_ABYSS = register("zero_abyss", new ZeroAbyssItem(ToolMaterials.DIAMOND, 10, -2.4f, new Item.Settings().group(GROUP).rarity(RARE)));
    public static final Item BLANK_BLIZZARD = register("blank_blizzard", new BowItem(new Item.Settings().group(GROUP).rarity(RARE).maxCount(1)));
    public static final Item FROSTBUSTER = register("frostbuster", new FrostbusterItem(ChilledVoidToolMaterial.INSTANCE, new Item.Settings().group(GROUP).rarity(RARE).maxCount(1)));
    public static final Item BLIZZARD_ABYSS_CORE = register("blizzard_abyss_core", new Item(new Item.Settings().group(GROUP).rarity(EPIC).maxCount(16)));
    public static final Item CHILLED_VOID_HELMET = register("chilled_void_helmet", new ChilledVoidArmorItem(ChilledVoidArmorMaterial.INSTANCE, HEAD, new Item.Settings().group(GROUP).rarity(RARE)));
    public static final Item CHILLED_VOID_CHESTPLATE = register("chilled_void_chestplate", new ChilledVoidArmorItem(ChilledVoidArmorMaterial.INSTANCE, CHEST, new Item.Settings().group(GROUP).rarity(RARE)));
    public static final Item CHILLED_VOID_LEGGINGS = register("chilled_void_leggings", new ChilledVoidArmorItem(ChilledVoidArmorMaterial.INSTANCE, LEGS, new Item.Settings().group(GROUP).rarity(RARE)));
    public static final Item CHILLED_VOID_BOOTS = register("chilled_void_boots", new ChilledVoidArmorItem(ChilledVoidArmorMaterial.INSTANCE, FEET, new Item.Settings().group(GROUP).rarity(RARE)));
    public static final Item PIERCING_BITE = register("piercing_bite", new PiercingBiteItem(new Item.Settings().group(GROUP).rarity(RARE).maxCount(1)));
    public static final Item HEART_OF_ICE = register("heart_of_ice", new HeartOfIceItem(new Item.Settings().group(GROUP).rarity(RARE).maxCount(1)));
    public static final Item FROSTED_RESOLVE = register("frosted_resolve", new Item(new Item.Settings().group(GROUP).rarity(RARE).maxCount(1)));

    // the end
    public static final Item PIERCING_END = register("piercing_end", new Item(new Item.Settings().group(GROUP).maxCount(1)));
    public static final Item VOIDS_EDGE = register("voids_edge", new Item(new Item.Settings().group(GROUP).maxCount(1)));
    public static final Item VOIDING_END_GEODE = register("voiding_end_geode", new Item(new Item.Settings().group(GROUP).maxCount(64)));
    public static final Item VOID_PULSE = register("void_pulse", new Item(new Item.Settings().group(GROUP).maxCount(1)));
    public static final Item VOIDING_END_HELMET = register("voiding_end_helmet", new ArmorItem(ArmorMaterials.DIAMOND, HEAD, new Item.Settings().group(GROUP).maxCount(1)));
    public static final Item VOIDING_END_CHESTPLATE = register("voiding_end_chestplate", new ArmorItem(ArmorMaterials.DIAMOND, CHEST, new Item.Settings().group(GROUP).maxCount(1)));
    public static final Item VOIDING_END_LEGGINGS = register("voiding_end_leggings", new ArmorItem(ArmorMaterials.DIAMOND, LEGS, new Item.Settings().group(GROUP).maxCount(1)));
    public static final Item VOIDING_END_BOOTS = register("voiding_end_boots", new ArmorItem(ArmorMaterials.DIAMOND, FEET, new Item.Settings().group(GROUP).maxCount(1)));

    // ruin warden
    public static final Item NEBULA_GEAR = register("nebula_gear", new Item(new Item.Settings().group(GROUP)));
    public static final Item RUIN_BRINGER = register("ruin_bringer", new PickaxeItem(ToolMaterials.IRON, 4, -2.4f, new Item.Settings().group(GROUP)));
    public static final Item APOCALYPSE = register("apocalypse", new Item(new Item.Settings().group(GROUP)));
    public static final Item CATACLYSM = register("cataclysm", new SwordItem(ToolMaterials.IRON, 4, -2.4f, new Item.Settings().group(GROUP)));
    public static final Item RUINATION = register("ruination", new CrossbowItem(new Item.Settings().group(GROUP)));
    public static final Item NEBULA_GEAR_HELMET = register("nebula_gear_helmet", new ArmorItem(ArmorMaterials.IRON, HEAD, new Item.Settings().group(GROUP)));
    public static final Item NEBULA_GEAR_CHESTPLATE = register("nebula_gear_chestplate", new ArmorItem(ArmorMaterials.IRON, CHEST, new Item.Settings().group(GROUP)));
    public static final Item NEBULA_GEAR_LEGGINGS = register("nebula_gear_leggings", new ArmorItem(ArmorMaterials.IRON, LEGS, new Item.Settings().group(GROUP)));
    public static final Item NEBULA_GEAR_BOOTS = register("nebula_gear_boots", new ArmorItem(ArmorMaterials.IRON, FEET, new Item.Settings().group(GROUP)));

    // fire
    public static final Item INFERNAL_END = register("infernal_end", new SwordItem(ToolMaterials.DIAMOND, 5, -2.4f, new Item.Settings().group(GROUP)));
    public static final Item BLAZEBUSTER = register("blazebuster", new Item(new Item.Settings().group(GROUP)));
    public static final Item FIRST_FLAME = register("first_flame", new Item(new Item.Settings().group(GROUP)));
    public static final Item BLAZING_VOID_EMBLEM = register("blazing_void_emblem", new Item(new Item.Settings().group(GROUP)));
    public static final Item ORIGIN_BLAZE = register("origin_blaze", new Item(new Item.Settings().group(GROUP)));
    public static final Item BLAZING_VOID_HELMET = register("blazing_void_helmet", new Item(new Item.Settings().group(GROUP)));
    public static final Item BLAZING_VOID_CHESTPLATE = register("blazing_void_chestplate", new Item(new Item.Settings().group(GROUP)));
    public static final Item BLAZING_VOID_LEGGINGS = register("blazing_void_leggings", new Item(new Item.Settings().group(GROUP)));
    public static final Item BLAZING_VOID_BOOTS = register("blazing_void_boots", new Item(new Item.Settings().group(GROUP)));

    // Oblivion Temple
    public static final Item SHATTERED_OBLIVION = register("shattered_oblivion", new SwordItem(ToolMaterials.DIAMOND, 7, -2.4f, new Item.Settings().group(GROUP)));
    public static final Item TEARS_OF_OBLIVION = register("tears_of_oblivion", new BowItem(new Item.Settings().group(GROUP)));
    public static final Item CHALICE_OF_NULLITY = register("chalice_of_nullity", new Item(new Item.Settings().group(GROUP)));
    public static final Item CORRUPTED_CRIMSON_THIRST = register("corrupted_crimson_thirst", new Item(new Item.Settings().group(GROUP)));

    // dungeons
    public static final Item BEJEWELED_CHARM = register("bejeweled_totem", new Item(new Item.Settings().group(GROUP).rarity(RARE).maxCount(1)));
    public static final Item BEJEWELED_ARROW = register("bejeweled_arrow", new ArrowItem(new Item.Settings().group(GROUP).rarity(RARE)));
    public static final Item BEJEWELED_MIRROR = register("bejeweled_mirror", new BejeweledMirrorItem(new Item.Settings().group(GROUP).rarity(RARE).maxCount(1)));

    // Dark Sakura
    public static final Item DARK_CRIMSON = register("dark_crimson", new Item(new Item.Settings().group(GROUP)));
    public static final Item SAKURA_EDGE = register("sakura_edge", new Item(new Item.Settings().group(GROUP)));
    public static final Item CHERRY_FALL = register("cherry_fall", new Item(new Item.Settings().group(GROUP)));

    // Shulker
    public static final Item LEVITATION_BLASTER = register("levitation_blaster", new LevitationBlasterItem(new Item.Settings().group(GROUP).rarity(RARE).maxDamage(1250)));
    public static final Item SHULKER_SLICER = register("shulker_slicer", new ShulkerSlicerItem(ShulkerToolMaterial.INSTANCE, 7, -2.0f, new Item.Settings().group(GROUP).rarity(RARE)));
    public static final Item FLOATING_CROSS = register("floating_cross", new Item(new Item.Settings().group(GROUP).rarity(RARE)));

    // Crimson Majesty
    public static final Item CRIMSON_MAJESTY_HELMET = register("crimson_majesty_helmet", new CrimsonMajestyArmorItem(CrimsonMajestyArmorMaterial.INSTANCE, HEAD, new Item.Settings().group(GROUP).rarity(RARE)));
    public static final Item CRIMSON_MAJESTY_CHESTPLATE = register("crimson_majesty_chestplate", new CrimsonMajestyArmorItem(CrimsonMajestyArmorMaterial.INSTANCE, CHEST, new Item.Settings().group(GROUP).rarity(RARE)));
    public static final Item CRIMSON_MAJESTY_LEGGINGS = register("crimson_majesty_leggings", new CrimsonMajestyArmorItem(CrimsonMajestyArmorMaterial.INSTANCE, LEGS, new Item.Settings().group(GROUP).rarity(RARE)));
    public static final Item CRIMSON_MAJESTY_BOOTS = register("crimson_majesty_boots", new CrimsonMajestyArmorItem(CrimsonMajestyArmorMaterial.INSTANCE, FEET, new Item.Settings().group(GROUP).rarity(RARE)));
    public static final Item CRIMSON_MAJESTY_SWORD = register("crimson_majesty_sword", new Item(new Item.Settings().group(GROUP).rarity(RARE)));

    // Starlight - Dungeon items
    public static final Item STARLIGHT_BATHED_DAGGER = register("starlight_bathed_dagger", new StarlightDaggerItem(new Item.Settings().group(GROUP).maxCount(1).maxDamage(1500).rarity(RARE)));
    public static final Item PIERCING_STARLIGHT = register("piercing_starlight", new PiercingStarlightItem(new Item.Settings().group(GROUP).maxCount(1).maxDamage(1500).rarity(RARE)));
    public static final Item LAST_GLITTER = register("last_glitter", new Item(new Item.Settings().group(GROUP).maxCount(16).rarity(RARE)));
    public static final Item STARLIT_PASSION = register("starlit_passion", new StarlightPassionItem(new Item.Settings().group(GROUP).maxCount(1).rarity(RARE)));
    public static final Item STARLIGHT_WINGS = register("starlight_wings", new StarlightWingsItem(new Item.Settings().group(GROUP).rarity(RARE)));
    public static final Item STARLIGHT_HELMET = register("starlight_helmet", new StarlightArmorItem(ArmorMaterials.IRON, HEAD, new Item.Settings().group(GROUP).rarity(RARE)));
    public static final Item STARLIGHT_CHESTPLATE = register("starlight_chestplate", new StarlightArmorItem(ArmorMaterials.IRON, CHEST, new Item.Settings().group(GROUP).rarity(RARE)));
    public static final Item STARLIGHT_LEGGINGS = register("starlight_leggings", new StarlightArmorItem(ArmorMaterials.IRON, LEGS, new Item.Settings().group(GROUP).rarity(RARE)));
    public static final Item STARLIGHT_BOOTS = register("starlight_boots", new StarlightArmorItem(ArmorMaterials.IRON, FEET, new Item.Settings().group(GROUP).rarity(RARE)));

    // Starfall
    public static final Item FALLEN_LIGHT = register("fallen_light", new SwordItem(ToolMaterials.IRON, 27, -2.4f, new Item.Settings().group(GROUP).rarity(RARE)));
    public static final Item PHOENIX_STARDUST_WINGS = register("phoenix_stardust_wings", new StardustWingsItem(new Item.Settings().group(GROUP).rarity(RARE)));
    public static final Item STARFALL_CORE = register("starfall_core", new Item(new Item.Settings().group(GROUP).rarity(RARE)));
    public static final Item WISH_OF_STARFALL = register("wish_of_starfall", new StarfallTomeItem(new Item.Settings().group(GROUP).rarity(RARE).maxDamage(5)));
    public static final Item PHOENIX_EMBER = register("phoenix_ember", new Item(new Item.Settings().group(GROUP).rarity(RARE)));
    public static final Item STARFALL_BREW = register("starfall_brew", new BrewItem(new Item.Settings().group(GROUP).rarity(RARE)
            .food(new FoodComponent.Builder().snack()
                    .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 20 * 60, 2), 1.0f)
                    .statusEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST, 20 * 60, 2), 1.0f)
                    .statusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 20 * 60, 0), 1.0f)
                    .statusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 20 * 60, 0), 1.0f).build()), 20 * 120));
    public static final Item STARFALL_HELMET = register("starfall_helmet", new StarfallArmorItem(CrimsonMajestyArmorMaterial.INSTANCE, HEAD, new Item.Settings().group(GROUP).rarity(RARE)));
    public static final Item STARFALL_CHESTPLATE = register("starfall_chestplate", new StarfallArmorItem(CrimsonMajestyArmorMaterial.INSTANCE, CHEST, new Item.Settings().group(GROUP).rarity(RARE)));
    public static final Item STARFALL_LEGGINGS = register("starfall_leggings", new StarfallArmorItem(CrimsonMajestyArmorMaterial.INSTANCE, LEGS, new Item.Settings().group(GROUP).rarity(RARE)));
    public static final Item STARFALL_BOOTS = register("starfall_boots", new StarfallArmorItem(CrimsonMajestyArmorMaterial.INSTANCE, FEET, new Item.Settings().group(GROUP).rarity(RARE)));

    // stuff
    // misc
//    public static final Item TRUE_EOE_SPAWN_EGG = register("true_eye_of_ender_spawn_egg", new SpawnEggItem(OmegaEntities.TRUE_EYE_OF_ENDER, 0, 0, new Item.Settings().group(IntoTheOmega.GROUP)));
    public static final Item VOIDS_REFUGE = register("voids_refuge", new Item(new Item.Settings().group(GROUP).rarity(EPIC)));
    public static final Item ENDERANG = register("enderang", new Item(new Item.Settings().group(GROUP).rarity(EPIC)));

    // creative items
    public static final Item INFINITE_OMEGA_GOD_SEAL = register("infinite_omega_god_seal", new InvincibleTrinketItem(new Item.Settings().rarity(EPIC)));
    public static final Item SLIME_STRUCTURE_GENERATOR = register("slime_structure_generator", new SlimeStructureGeneratorItem(new Item.Settings().rarity(EPIC)));
    public static final Item FLOODING_STRUCTURE_VOID = register("flooding_structure_void", new FloodingStructureVoidItem(new Item.Settings().maxCount(1).rarity(EPIC)));

    // Memory Fragments
    public static final MemoryItem MEMORY_OF_DISTORTION = register("memory_of_distortion", new MemoryItem(new Item.Settings().rarity(EPIC).group(GROUP)));
    public static final MemoryItem MEMORY_OF_DUALITY = register("memory_of_duality", new MemoryItem(new Item.Settings().rarity(EPIC).group(GROUP)));
    public static final MemoryItem MEMORY_OF_FLAMES = register("memory_of_flames", new MemoryItem(new Item.Settings().rarity(EPIC).group(GROUP)));
    public static final MemoryItem MEMORY_OF_SHATTERING = register("memory_of_shattering", new MemoryItem(new Item.Settings().rarity(EPIC).group(GROUP)));
    public static final MemoryFocusItem MEMORY_FOCUS = register("memory_focus", new MemoryFocusItem(new Item.Settings().rarity(EPIC).group(GROUP)));

    // ???
    public static final Item EMERGENT_HYPERION = register("emergent_hyperion", new Item(new Item.Settings().rarity(EPIC).group(GROUP)));

    private static <T extends Item> T register(String name, T item) {
        return Registry.register(Registry.ITEM, IntoTheOmega.id(name), item);
    }

    public static void init() {
//        ShieldRegistry.register(OMEGA_SHIELD, new ShieldEvent(true, true, true) {
//            @Override
//            public void whileHolding(LivingEntity defender, int level, Hand hand, ItemStack shield) {
//                defender.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 22 * 5, 0, false, false));
//            }
//        });
    }

    private OmegaItems() {
        // NO-OP
    }
}
