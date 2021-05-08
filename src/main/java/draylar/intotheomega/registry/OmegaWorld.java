package draylar.intotheomega.registry;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.world.abyss_flower.AbyssFlowerIslandStructure;
import draylar.intotheomega.world.api.BaseIslandStructure;
import draylar.intotheomega.world.area.slime.SlimePillarFeature;
import draylar.intotheomega.world.chorus_island.ChorusIslandStructure;
import draylar.intotheomega.world.feature.ObsidianSpikeFeature;
import draylar.intotheomega.world.feature.OmegaCrystalOreFeature;
import draylar.intotheomega.world.ice_island.IceIslandStructure;
import draylar.intotheomega.world.island.GenericIslandStructure;
import draylar.intotheomega.world.spike.SpikeStructure;
import draylar.intotheomega.world.structure.*;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.fabricmc.fabric.api.structure.v1.FabricStructureBuilder;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.chunk.StructureConfig;
import net.minecraft.world.gen.feature.*;

import java.util.Arrays;
import java.util.List;

public class OmegaWorld {

    // Structure Features
    public static final StructureFeature<DefaultFeatureConfig> EYE_ALTAR = new EyeAltarStructure(DefaultFeatureConfig.CODEC);
    public static final StructureFeature<DefaultFeatureConfig> SMALL_PHANTOM_TOWER = new SmallPhantomTowerStructure(DefaultFeatureConfig.CODEC);
    public static final StructureFeature<DefaultFeatureConfig> MEDIUM_PHANTOM_TOWER = new MediumPhantomTowerStructure(DefaultFeatureConfig.CODEC);
    public static final StructureFeature<DefaultFeatureConfig> SPIKE = new SpikeStructure(DefaultFeatureConfig.CODEC);
    public static final StructureFeature<DefaultFeatureConfig> MATRIX_PEDESTAL = new MatrixPedestalStructure(DefaultFeatureConfig.CODEC);
    public static final StructureFeature<DefaultFeatureConfig> BEJEWELED_DUNGEON = new BejeweledDungeonStructure(DefaultFeatureConfig.CODEC);

    // Island Structure Features
    // These islands are pooled up when spawning to prevent overlapping/too-close islands.
    public static final StructureFeature<DefaultFeatureConfig> GENERIC_ISLAND = new GenericIslandStructure(DefaultFeatureConfig.CODEC);
    public static final StructureFeature<DefaultFeatureConfig> ICE_ISLAND = new IceIslandStructure(DefaultFeatureConfig.CODEC);
    public static final StructureFeature<DefaultFeatureConfig> CHORUS_ISLAND = new ChorusIslandStructure(DefaultFeatureConfig.CODEC);


    public static final StructureFeature<DefaultFeatureConfig> ABYSS_FLOWER_ISLAND = new AbyssFlowerIslandStructure(DefaultFeatureConfig.CODEC);

    // TODO: phantom tower -> phantom outpost -> phantom [?]

    // Standard Features
    public static final Feature<DefaultFeatureConfig> OBSIDIAN_SPIKE = register("obsidian_spike", new ObsidianSpikeFeature(DefaultFeatureConfig.CODEC));
    public static final Feature<DefaultFeatureConfig> OMEGA_ORE = Registry.register(Registry.FEATURE, IntoTheOmega.id("ore"), new OmegaCrystalOreFeature(DefaultFeatureConfig.CODEC));
    public static final Feature<DiskFeatureConfig> END_PATCH = register("cracked_end_stone_patch", new DiskFeature(DiskFeatureConfig.CODEC));
    public static final Feature<DefaultFeatureConfig> SLIME_PILLAR = register("slime_pillar", new SlimePillarFeature(DefaultFeatureConfig.CODEC));

    // Config / Biome Cache
    public static final List<RegistryKey<Biome>> VALID_EYE_ALTAR_BIOMES = Arrays.asList(BiomeKeys.END_HIGHLANDS);

    public static void init() {
        registerStructures();
        registerAdditions();
    }

    public static void registerStructures() {
        FabricStructureBuilder
                .create(IntoTheOmega.id("eye_altar"), EYE_ALTAR)
                .step(GenerationStep.Feature.SURFACE_STRUCTURES)
                .defaultConfig(new StructureConfig(20, 11, 18591581))
                .adjustsSurface()
                .register();

        FabricStructureBuilder
                .create(IntoTheOmega.id("small_phantom_tower"), SMALL_PHANTOM_TOWER)
                .step(GenerationStep.Feature.SURFACE_STRUCTURES)
                .defaultConfig(new StructureConfig(20, 11, 5115234))
                .adjustsSurface()
                .register();

        FabricStructureBuilder
                .create(IntoTheOmega.id("medium_phantom_tower"), MEDIUM_PHANTOM_TOWER)
                .step(GenerationStep.Feature.SURFACE_STRUCTURES)
                .defaultConfig(new StructureConfig(25, 15, 76141))
                .adjustsSurface()
                .register();

        FabricStructureBuilder
                .create(IntoTheOmega.id("spike"), SPIKE)
                .step(GenerationStep.Feature.SURFACE_STRUCTURES)
                .defaultConfig(10, 8, 1112223)
                .adjustsSurface()
                .register();

        FabricStructureBuilder
                .create(IntoTheOmega.id("abyss_flower_island"), ABYSS_FLOWER_ISLAND)
                .step(GenerationStep.Feature.TOP_LAYER_MODIFICATION)
                .defaultConfig(1, 0, 0)
                .register();

        FabricStructureBuilder
                .create(IntoTheOmega.id("matrix_pedestal"), MATRIX_PEDESTAL)
                .step(GenerationStep.Feature.SURFACE_STRUCTURES)
                .defaultConfig(new StructureConfig(20, 11, 5145242))
                .adjustsSurface()
                .register();

        FabricStructureBuilder
                .create(IntoTheOmega.id("bejeweled_dungeon"), BEJEWELED_DUNGEON)
                .step(GenerationStep.Feature.SURFACE_STRUCTURES)
                .defaultConfig(new StructureConfig(15, 10, 151245))
                .adjustsSurface()
                .register();

        BaseIslandStructure.ALL_ISLANDS.forEach(island -> FabricStructureBuilder
                .create(IntoTheOmega.id(island.getId()), island)
                .step(GenerationStep.Feature.SURFACE_STRUCTURES)
                .defaultConfig(new StructureConfig(1, 0, 82508))
                .register());
    }

    public static void registerAdditions() {
        BiomeModifications
                .create(IntoTheOmega.id("eye_altar_addition"))
                .add(ModificationPhase.ADDITIONS,
                        context -> VALID_EYE_ALTAR_BIOMES.contains(context.getBiomeKey()),
                        context -> context.getGenerationSettings().addBuiltInStructure(OmegaConfiguredFeatures.EYE_ALTAR));

        BiomeModifications
                .create(IntoTheOmega.id("matrix_pedestal"))
                .add(ModificationPhase.ADDITIONS,
                        context -> VALID_EYE_ALTAR_BIOMES.contains(context.getBiomeKey()),
                        context -> context.getGenerationSettings().addBuiltInStructure(OmegaConfiguredFeatures.MATRIX_PEDESTAL));

        BiomeModifications
                .create(IntoTheOmega.id("bejeweled_dungeon"))
                .add(ModificationPhase.ADDITIONS,
                        context -> VALID_EYE_ALTAR_BIOMES.contains(context.getBiomeKey()),
                        context -> context.getGenerationSettings().addBuiltInStructure(OmegaConfiguredFeatures.BEJEWELED_DUNGEON));

        BiomeModifications
                .create(IntoTheOmega.id("small_phantom_tower_addition"))
                .add(ModificationPhase.ADDITIONS,
                        context -> VALID_EYE_ALTAR_BIOMES.contains(context.getBiomeKey()),
                        context -> context.getGenerationSettings().addBuiltInStructure(OmegaConfiguredFeatures.SMALL_PHANTOM_TOWER));

        BiomeModifications
                .create(IntoTheOmega.id("medium_phantom_tower_addition"))
                .add(ModificationPhase.ADDITIONS,
                        context -> VALID_EYE_ALTAR_BIOMES.contains(context.getBiomeKey()),
                        context -> context.getGenerationSettings().addBuiltInStructure(OmegaConfiguredFeatures.MEDIUM_PHANTOM_TOWER));

        BiomeModifications
                .create(IntoTheOmega.id("ice_island"))
                .add(ModificationPhase.ADDITIONS,
                        BiomeSelectors.foundInTheEnd(),
                        context -> context.getGenerationSettings().addBuiltInStructure(OmegaConfiguredFeatures.CONFIGURED_ICE_ISLAND));

        BiomeModifications
                .create(IntoTheOmega.id("chorus_island"))
                .add(ModificationPhase.ADDITIONS,
                        BiomeSelectors.foundInTheEnd(),
                        context -> context.getGenerationSettings().addBuiltInStructure(OmegaConfiguredFeatures.CONFIGURED_CHORUS_ISLAND));

        BiomeModifications
                .create(IntoTheOmega.id("generic_island"))
                .add(ModificationPhase.ADDITIONS,
                        BiomeSelectors.foundInTheEnd(),
                        context -> context.getGenerationSettings().addBuiltInStructure(OmegaConfiguredFeatures.CONFIGURED_GENERIC_ISLAND));

//        BiomeModifications
//                .create(IntoTheOmega.id("spike"))
//                .add(ModificationPhase.ADDITIONS,
//                        BiomeSelectors.foundInTheEnd(),
//                        context -> context.getGenerationSettings().addBuiltInStructure(OmegaConfiguredFeatures.CONFIGURED_SPIKE));
//
        BiomeModifications
                .create(IntoTheOmega.id("abyss_flower_island"))
                .add(ModificationPhase.ADDITIONS,
                        BiomeSelectors.includeByKey(OmegaBiomes.ABYSSAL_CORE_KEY),
                        context -> context.getGenerationSettings().addBuiltInStructure(OmegaConfiguredFeatures.ABYSS_FLOWER_ISLAND));
    }

    private static <C extends FeatureConfig, F extends Feature<C>> F register(String name, F feature) {
        return Registry.register(Registry.FEATURE, IntoTheOmega.id(name), feature);
    }

    private OmegaWorld() {
        // no-op
    }
}
