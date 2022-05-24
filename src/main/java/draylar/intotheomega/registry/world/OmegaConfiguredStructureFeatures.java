package draylar.intotheomega.registry.world;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.world.structure.island.ChorusIslandStructure;
import draylar.intotheomega.world.structure.jigsaw.EndLabyrinthData;
import net.minecraft.structure.DesertVillageData;
import net.minecraft.tag.BiomeTags;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.feature.*;

public class OmegaConfiguredStructureFeatures {

    // Entries
    // Structure Features
    public static final RegistryEntry<ConfiguredStructureFeature<?, ?>> EYE_ALTAR = register(key("eye_altar"), OmegaStructureFeatures.EYE_ALTAR.configure(new DefaultFeatureConfig(), BiomeTags.END_CITY_HAS_STRUCTURE, true));
    public static final RegistryEntry<ConfiguredStructureFeature<?, ?>> SMALL_PHANTOM_TOWER = register(key("small_phantom_tower"), OmegaStructureFeatures.SMALL_PHANTOM_TOWER.configure(new DefaultFeatureConfig(), BiomeTags.END_CITY_HAS_STRUCTURE, true));
    public static final RegistryEntry<ConfiguredStructureFeature<?, ?>> MEDIUM_PHANTOM_TOWER = register(key("medium_phantom_tower"), OmegaStructureFeatures.MEDIUM_PHANTOM_TOWER.configure(new DefaultFeatureConfig(), BiomeTags.END_CITY_HAS_STRUCTURE, true));
    //    public static final ConfiguredStructureFeature<?, ?> ENIGMA_KING_SPIKE = register("spike", OmegaWorld.SPIKE.configure(DefaultFeatureConfig.INSTANCE));
    public static final RegistryEntry<ConfiguredStructureFeature<?, ?>> MATRIX_PEDESTAL = register(key("matrix_pedestal"), OmegaStructureFeatures.MATRIX_PEDESTAL.configure(new DefaultFeatureConfig(), BiomeTags.END_CITY_HAS_STRUCTURE, true));
    public static final RegistryEntry<ConfiguredStructureFeature<?, ?>> BEJEWELED_DUNGEON = register(key("bejeweled_dungeon"), OmegaStructureFeatures.BEJEWELED_DUNGEON.configure(new DefaultFeatureConfig(), BiomeTags.END_CITY_HAS_STRUCTURE));
//    public static final RegistryEntry<ConfiguredStructureFeature<?, ?>> TEST_SF = register(key("test_sf"), OmegaStructureFeatures.TEST_SF.configure(new DefaultFeatureConfig(), BiomeTags.IS_FOREST));
    public static final RegistryEntry<ConfiguredStructureFeature<?, ?>> OMEGA_SLIME_SPIRAL = register(key("omega_slime_spiral"), OmegaStructureFeatures.OMEGA_SLIME_SPIRAL.configure(new DefaultFeatureConfig(), OmegaBiomeTags.OMEGA_SLIME_WASTES));
    public static final RegistryEntry<ConfiguredStructureFeature<?, ?>> SLIME_TENDRIL = register(key("slime_tendril"), OmegaStructureFeatures.SLIME_TENDRIL.configure(new DefaultFeatureConfig(), OmegaBiomeTags.OMEGA_SLIME_WASTES));
    public static final RegistryEntry<ConfiguredStructureFeature<?, ?>> SLIME_CEILING = register(key("slime_ceiling"), OmegaStructureFeatures.SLIME_CEILING.configure(new DefaultFeatureConfig(), OmegaBiomeTags.OMEGA_SLIME_WASTES));
    public static final RegistryEntry<ConfiguredStructureFeature<?, ?>> SLIME_CAVE = register(key("slime_cave"), OmegaStructureFeatures.SLIME_CAVE.configure(new DefaultFeatureConfig(), OmegaBiomeTags.OMEGA_SLIME_WASTES));
    public static final RegistryEntry<ConfiguredStructureFeature<?, ?>> END_THORN = register(key("end_thorn"), OmegaStructureFeatures.END_THORN.configure(new DefaultFeatureConfig(), OmegaBiomeTags.END_ISLAND));
    public static final RegistryEntry<ConfiguredStructureFeature<?, ?>> STARFIELD = register(key("starfield"), OmegaStructureFeatures.STARFIELD.configure(new DefaultFeatureConfig(), OmegaBiomeTags.STARFALL_VALLEY));
    public static final RegistryEntry<ConfiguredStructureFeature<?, ?>> SPIRAL_DUNGEON = register(key("spiral_dungeon"), OmegaStructureFeatures.SPIRAL_DUNGEON.configure(new DefaultFeatureConfig(), OmegaBiomeTags.GENERIC_END_STRUCTURE, true));


    // Islands
    public static final RegistryEntry<ConfiguredStructureFeature<?, ?>> END_ISLAND = register(key("end_island"), OmegaStructureFeatures.END_ISLAND.configure(new DefaultFeatureConfig(), OmegaBiomeTags.GENERIC_END_STRUCTURE));
    public static final RegistryEntry<ConfiguredStructureFeature<?, ?>> ICE_ISLAND = register(key("ice_island"), OmegaStructureFeatures.ICE_ISLAND.configure(new DefaultFeatureConfig(), OmegaBiomeTags.GENERIC_END_STRUCTURE));
    public static final RegistryEntry<ConfiguredStructureFeature<?, ?>> CHORUS_ISLAND = register(key("chorus_island"), OmegaStructureFeatures.CHORUS_ISLAND.configure(new DefaultFeatureConfig(), OmegaBiomeTags.GENERIC_END_STRUCTURE, ChorusIslandStructure.getMonsterSpawns()));


//    public static final ConfiguredStructureFeature<?, ?> CONFIGURED_GENERIC_ISLAND = register("generic_island", OmegaWorld.GENERIC_ISLAND.configure(DefaultFeatureConfig.INSTANCE));
//    public static final ConfiguredStructureFeature<?, ?> CONFIGURED_ICE_ISLAND = register("ice_island", OmegaWorld.ICE_ISLAND.configure(DefaultFeatureConfig.INSTANCE));
//    public static final ConfiguredStructureFeature<?, ?> CONFIGURED_CHORUS_ISLAND = register("chorus_island", OmegaWorld.CHORUS_ISLAND.configure(DefaultFeatureConfig.INSTANCE));

//    public static final ConfiguredStructureFeature<?, ?> ABYSS_FLOWER_ISLAND = register("abyss_flower_island", OmegaWorld.ABYSS_FLOWER_ISLAND.configure(DefaultFeatureConfig.INSTANCE));

    public static final RegistryEntry<ConfiguredStructureFeature<?, ?>> SMALL_CHORUS_MONUMENT = register(key("small_chorus_monument"), OmegaStructureFeatures.SMALL_CHORUS_MONUMENT.configure(DefaultFeatureConfig.INSTANCE, BiomeTags.END_CITY_HAS_STRUCTURE));

    // JIGSAW
    public static final RegistryEntry<ConfiguredStructureFeature<?, ?>> END_LABYRINTH = register(key("end_labyrinth"), OmegaStructureFeatures.END_LABYRINTH.configure(new StructurePoolFeatureConfig(EndLabyrinthData.START_POOL, 7), OmegaBiomeTags.GENERIC_END_STRUCTURE));

    private static <FC extends FeatureConfig, F extends StructureFeature<FC>> RegistryEntry<ConfiguredStructureFeature<?, ?>> register(RegistryKey<ConfiguredStructureFeature<?, ?>> key, ConfiguredStructureFeature<FC, F> configuredStructureFeature) {
        return BuiltinRegistries.add(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, key, configuredStructureFeature);
    }

    private static RegistryKey<ConfiguredStructureFeature<?, ?>> key(String id) {
        return RegistryKey.of(Registry.CONFIGURED_STRUCTURE_FEATURE_KEY, IntoTheOmega.id(id));
    }

    public static void init() {

    }
}
