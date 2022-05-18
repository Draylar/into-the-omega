package draylar.intotheomega.registry.world;

import com.google.common.collect.ImmutableList;
import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.registry.OmegaBlocks;
import draylar.intotheomega.registry.world.OmegaWorld;
import net.minecraft.block.Blocks;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

import java.util.List;

public class OmegaConfiguredFeatures {

    // Standard Features
    public static final RegistryEntry<ConfiguredFeature<DefaultFeatureConfig, ?>> OBISDIAN_SPIKE = register("obsidian_spike", OmegaWorld.OBSIDIAN_SPIKE);
    public static final RegistryEntry<ConfiguredFeature<OreFeatureConfig, ?>> OMEGA_ORE = register("omega_ore", OmegaWorld.OMEGA_ORE, new OreFeatureConfig(List.of(OreFeatureConfig.createTarget(new BlockMatchRuleTest(Blocks.END_STONE), Blocks.DIAMOND_ORE.getDefaultState())), 4, 1.0f));
    public static final RegistryEntry<ConfiguredFeature<DiskFeatureConfig, ?>> COARSE_PATCH = register("coarse_dirt_patch", OmegaWorld.END_PATCH, new DiskFeatureConfig(Blocks.COARSE_DIRT.getDefaultState(), UniformIntProvider.create(5, 8), 3, ImmutableList.of(Blocks.END_STONE.getDefaultState())));
    public static final RegistryEntry<ConfiguredFeature<DiskFeatureConfig, ?>> ENDSTONE_PATCH = register("end_stone_patch", OmegaWorld.END_PATCH, new DiskFeatureConfig(Blocks.END_STONE.getDefaultState(), UniformIntProvider.create(3, 4), 3, ImmutableList.of(OmegaBlocks.CHORUS_GRASS.getDefaultState())));
    public static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> OBSIDISHROOM_PATCH = register("obisishroom_patch", Feature.RANDOM_PATCH, ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(OmegaBlocks.OBSIDISHROOM))));
    public static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> ENDERSHROOM_PATCH = register("endershroom_patch", Feature.RANDOM_PATCH, ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(OmegaBlocks.ENDERSHROOM))));
    public static final RegistryEntry<ConfiguredFeature<DefaultFeatureConfig, ?>> SLIME_PILLAR = register("slime_pillar", OmegaWorld.SLIME_PILLAR);
    public static final RegistryEntry<ConfiguredFeature<LakeFeature.Config, ?>> SLIME_LAKE = register("slime_lake", Feature.LAKE, new LakeFeature.Config(BlockStateProvider.of(OmegaBlocks.OMEGA_SLIME_FLUID.getDefaultState()), BlockStateProvider.of(Blocks.END_STONE.getDefaultState())));
    public static final RegistryEntry<ConfiguredFeature<DefaultFeatureConfig, ?>> SLIME_DUNGEON = register("slime_dungeon", OmegaWorld.SLIME_DUNGEON);
    public static final RegistryEntry<ConfiguredFeature<DefaultFeatureConfig, ?>> END_ISLAND_RIVER = register("end_river", OmegaWorld.END_ISLAND_RIVER);
    public static final RegistryEntry<ConfiguredFeature<DefaultFeatureConfig, ?>> FOREST_CHORUS_PLANT = register("forest_chorus_plant", Feature.CHORUS_PLANT);
    public static final RegistryEntry<ConfiguredFeature<DefaultFeatureConfig, ?>> CRYSTALITE_SPIKE = register("crystalite_spike", OmegaWorld.CRYSTALITE_SPIKE);
    public static final RegistryEntry<ConfiguredFeature<DefaultFeatureConfig, ?>> CRYSTALITE_CAVERN = register("crystalite_cavern", OmegaWorld.CRYSTALITE_CAVERN);
    public static final RegistryEntry<ConfiguredFeature<DefaultFeatureConfig, ?>> DARK_SAKURA_TREE = register("dark_sakura_tree", OmegaWorld.DARK_SAKURA_TREE);
    public static final RegistryEntry<ConfiguredFeature<DefaultFeatureConfig, ?>> TALL_CHORUS_PLANT = register("tall_chorus_plant", OmegaWorld.TALL_CHORUS_PLANT);

    public static <FC extends FeatureConfig, F extends Feature<FC>> RegistryEntry<ConfiguredFeature<FC, ?>> register(String id, F feature, FC config) {
        return BuiltinRegistries.method_40360(BuiltinRegistries.CONFIGURED_FEATURE, IntoTheOmega.id(id).toString(), new ConfiguredFeature<>(feature, config));
    }

    public static RegistryEntry<ConfiguredFeature<DefaultFeatureConfig, ?>> register(String id, Feature<DefaultFeatureConfig> feature) {
        return ConfiguredFeatures.register(IntoTheOmega.id(id).toString(), feature);
    }

    public static void init() {

    }

    private OmegaConfiguredFeatures() {

    }
}
