package draylar.intotheomega.registry.world;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.world.area.slime.SlimeDungeonFeature;
import draylar.intotheomega.world.area.slime.SlimePillarFeature;
import draylar.intotheomega.world.feature.*;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.*;

public class OmegaFeatures {

    // Structure Features
    //    public static final StructureFeature<DefaultFeatureConfig> SPIKE = new SpikeStructure(DefaultFeatureConfig.CODEC);


//    public static final StructureFeature<DefaultFeatureConfig> ABYSS_FLOWER_ISLAND = new AbyssFlowerIslandStructure(DefaultFeatureConfig.CODEC);

    // TODO: phantom tower -> phantom outpost -> phantom [?]

    // Standard Features
    public static final Feature<DefaultFeatureConfig> OBSIDIAN_SPIKE = register("obsidian_spike", new ObsidianSpikeFeature(DefaultFeatureConfig.CODEC));
    public static final Feature<DefaultFeatureConfig> CRYSTALITE_SPIKE = register("crystalite_spike", new CrystaliteSpikeFeature(DefaultFeatureConfig.CODEC));
    public static final Feature<OreFeatureConfig> OMEGA_ORE = Registry.register(Registry.FEATURE, IntoTheOmega.id("ore"), new OmegaCrystalOreFeature(OreFeatureConfig.CODEC));
    public static final Feature<DiskFeatureConfig> END_PATCH = register("cracked_end_stone_patch", new DiskFeature(DiskFeatureConfig.CODEC));
    public static final Feature<DefaultFeatureConfig> SLIME_PILLAR = register("slime_pillar", new SlimePillarFeature(DefaultFeatureConfig.CODEC));
    public static final Feature<DefaultFeatureConfig> SLIME_DUNGEON = register("slime_dungeon", new SlimeDungeonFeature(DefaultFeatureConfig.CODEC));
    public static final Feature<DefaultFeatureConfig> CRYSTALITE_CAVERN = register("crystalite_cavern", new CrystaliteCavernFeature(DefaultFeatureConfig.CODEC));
    public static final Feature<DefaultFeatureConfig> DARK_SAKURA_TREE = register("dark_sakura_tree", new DarkSakuraTreeFeature());
    public static final Feature<DefaultFeatureConfig> TALL_CHORUS_PLANT = register("tall_chorus_plant", new VariableChorusPlantFeature(DefaultFeatureConfig.CODEC, 16));

    public static void init() {
        registerStructures();
        registerAdditions();
    }

    public static void registerStructures() {
//        FabricStructureBuilder
//                .create(IntoTheOmega.id("spike"), SPIKE)
//                .step(GenerationStep.Feature.SURFACE_STRUCTURES)
//                .defaultConfig(10, 8, 1112223)
//                .adjustsSurface()
//                .register();

//        FabricStructureBuilder
//                .create(IntoTheOmega.id("abyss_flower_island"), ABYSS_FLOWER_ISLAND)
//                .step(GenerationStep.Feature.TOP_LAYER_MODIFICATION)
//                .defaultConfig(1, 0, 0)
//                .register();

//        BaseIslandStructure.ALL_ISLANDS.forEach(island -> FabricStructureBuilder
//                .create(IntoTheOmega.id(island.getId()), island)
//                .step(GenerationStep.Feature.SURFACE_STRUCTURES)
//                .defaultConfig(new StructureConfig(1, 0, 82508))
//                .register());
    }

    public static void registerAdditions() {
//        BiomeModifications
//                .create(IntoTheOmega.id("ice_island"))
//                .add(ModificationPhase.ADDITIONS,
//                        anyEndNotSpecial(),
//                        context -> context.getGenerationSettings().addBuiltInStructure(OmegaConfiguredFeatures.CONFIGURED_ICE_ISLAND));
//
//        BiomeModifications
//                .create(IntoTheOmega.id("chorus_island"))
//                .add(ModificationPhase.ADDITIONS,
//                        anyEndNotSpecial(),
//                        context -> context.getGenerationSettings().addBuiltInStructure(OmegaConfiguredFeatures.CONFIGURED_CHORUS_ISLAND));
//
//        BiomeModifications
//                .create(IntoTheOmega.id("generic_island"))
//                .add(ModificationPhase.ADDITIONS,
//                        anyEndNotSpecial(),
//                        context -> context.getGenerationSettings().addBuiltInStructure(OmegaConfiguredFeatures.CONFIGURED_GENERIC_ISLAND));

//        BiomeModifications
//                .create(IntoTheOmega.id("spike"))
//                .add(ModificationPhase.ADDITIONS,
//                        BiomeSelectors.foundInTheEnd(),
//                        context -> context.getGenerationSettings().addBuiltInStructure(OmegaConfiguredFeatures.CONFIGURED_SPIKE));
//
//        BiomeModifications
//                .create(IntoTheOmega.id("abyss_flower_island"))
//                .add(ModificationPhase.ADDITIONS,
//                        BiomeSelectors.includeByKey(OmegaBiomes.ABYSSAL_CORE_KEY),
//                        context -> context.getGenerationSettings().addBuiltInStructure(OmegaConfiguredFeatures.ABYSS_FLOWER_ISLAND));

//        BiomeModifications
//                .create(IntoTheOmega.id("end_island_river"))
//                .add(ModificationPhase.ADDITIONS,
//                        BiomeSelectors.foundInTheEnd(),
//                        context -> context.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.LAKES, OmegaConfiguredFeatures.END_ISLAND_RIVER));
    }

    private static <C extends FeatureConfig, F extends Feature<C>> F register(String name, F feature) {
        return Registry.register(Registry.FEATURE, IntoTheOmega.id(name), feature);
    }

    private OmegaFeatures() {
        // no-op
    }
}
