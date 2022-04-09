package draylar.intotheomega.registry;

import com.google.common.collect.ImmutableList;
import draylar.intotheomega.IntoTheOmega;
import net.minecraft.block.Blocks;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

import java.util.Arrays;
import java.util.List;

public class OmegaConfiguredFeatures {

    // Structure Features
    public static final ConfiguredStructureFeature<DefaultFeatureConfig, ? extends StructureFeature<DefaultFeatureConfig>> EYE_ALTAR = register("eye_altar", OmegaWorld.EYE_ALTAR.configure(new DefaultFeatureConfig()));
    public static final ConfiguredStructureFeature<DefaultFeatureConfig, ? extends StructureFeature<DefaultFeatureConfig>> SMALL_PHANTOM_TOWER = register("small_phantom_tower", OmegaWorld.SMALL_PHANTOM_TOWER.configure(new DefaultFeatureConfig()));
    public static final ConfiguredStructureFeature<DefaultFeatureConfig, ? extends StructureFeature<DefaultFeatureConfig>> MEDIUM_PHANTOM_TOWER = register("medium_phantom_tower", OmegaWorld.MEDIUM_PHANTOM_TOWER.configure(new DefaultFeatureConfig()));
//    public static final ConfiguredStructureFeature<?, ?> ENIGMA_KING_SPIKE = register("spike", OmegaWorld.SPIKE.configure(DefaultFeatureConfig.INSTANCE));
    public static final ConfiguredStructureFeature<DefaultFeatureConfig, ? extends StructureFeature<DefaultFeatureConfig>> MATRIX_PEDESTAL = register("matrix_pedestal", OmegaWorld.MATRIX_PEDESTAL.configure(new DefaultFeatureConfig()));
    public static final ConfiguredStructureFeature<DefaultFeatureConfig, ? extends StructureFeature<DefaultFeatureConfig>> BEJEWELED_DUNGEON = register("bejeweled_dungeon", OmegaWorld.BEJEWELED_DUNGEON.configure(new DefaultFeatureConfig()));

//    public static final ConfiguredStructureFeature<?, ?> CONFIGURED_GENERIC_ISLAND = register("generic_island", OmegaWorld.GENERIC_ISLAND.configure(DefaultFeatureConfig.INSTANCE));
//    public static final ConfiguredStructureFeature<?, ?> CONFIGURED_ICE_ISLAND = register("ice_island", OmegaWorld.ICE_ISLAND.configure(DefaultFeatureConfig.INSTANCE));
//    public static final ConfiguredStructureFeature<?, ?> CONFIGURED_CHORUS_ISLAND = register("chorus_island", OmegaWorld.CHORUS_ISLAND.configure(DefaultFeatureConfig.INSTANCE));

//    public static final ConfiguredStructureFeature<?, ?> ABYSS_FLOWER_ISLAND = register("abyss_flower_island", OmegaWorld.ABYSS_FLOWER_ISLAND.configure(DefaultFeatureConfig.INSTANCE));

    // Standard Features
    public static final ConfiguredFeature<?, ?> OBISDIAN_SPIKE = register("obsidian_spike", OmegaWorld.OBSIDIAN_SPIKE.configure(FeatureConfig.DEFAULT));
    public static final ConfiguredFeature<?, ?> OMEGA_ORE = register("omega_ore", OmegaWorld.OMEGA_ORE.configure(new OreFeatureConfig(List.of(OreFeatureConfig.createTarget(new BlockMatchRuleTest(Blocks.END_STONE), Blocks.DIAMOND_ORE.getDefaultState())), 4, 1.0f)));
    public static final ConfiguredFeature<?, ?> COARSE_PATCH = register("coarse_dirt_patch", OmegaWorld.END_PATCH.configure(new DiskFeatureConfig(Blocks.COARSE_DIRT.getDefaultState(), UniformIntProvider.create(3, 4), 3, ImmutableList.of(Blocks.END_STONE.getDefaultState()))));
    public static final ConfiguredFeature<?, ?> ENDSTONE_PATCH = register("end_stone_patch", OmegaWorld.END_PATCH.configure(new DiskFeatureConfig(Blocks.END_STONE.getDefaultState(), UniformIntProvider.create(3, 4), 3, ImmutableList.of(OmegaBlocks.CHORUS_GRASS.getDefaultState()))));
    public static final ConfiguredFeature<?, ?> OBSIDISHROOM_PATCH = register("obisishroom_patch", Feature.RANDOM_PATCH.configure(ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(BlockStateProvider.of(OmegaBlocks.OBSIDISHROOM))))));
    public static final ConfiguredFeature<?, ?> ENDERSHROOM_PATCH = register("endershroom_patch", Feature.RANDOM_PATCH.configure(ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(BlockStateProvider.of(OmegaBlocks.ENDERSHROOM))))));
    public static final ConfiguredFeature<?, ?> SLIME_PILLAR = register("slime_pillar", OmegaWorld.SLIME_PILLAR.configure(FeatureConfig.DEFAULT));
    public static final ConfiguredFeature<?, ?> SLIME_LAKE = register("slime_lake", Feature.LAKE.configure(new LakeFeature.Config(BlockStateProvider.of(OmegaBlocks.OMEGA_SLIME_FLUID.getDefaultState()), BlockStateProvider.of(Blocks.END_STONE.getDefaultState()))));
    public static final ConfiguredFeature<?, ?> SLIME_DUNGEON = register("slime_dungeon", OmegaWorld.SLIME_DUNGEON.configure(FeatureConfig.DEFAULT));
    public static final ConfiguredFeature<?, ?> END_ISLAND_RIVER = register("end_river", OmegaWorld.END_ISLAND_RIVER.configure(FeatureConfig.DEFAULT));
    public static final ConfiguredFeature<?, ?> FOREST_CHORUS_PLANT = register("forest_chorus_plant", Feature.CHORUS_PLANT.configure(FeatureConfig.DEFAULT));
    public static final ConfiguredFeature<?, ?> CRYSTALITE_SPIKE = register("crystalite_spike", OmegaWorld.CRYSTALITE_SPIKE.configure(FeatureConfig.DEFAULT));
    public static final ConfiguredFeature<?, ?> CRYSTALITE_CAVERN = register("crystalite_cavern", OmegaWorld.CRYSTALITE_CAVERN.configure(FeatureConfig.DEFAULT));
    public static final ConfiguredFeature<?, ?> DARK_SAKURA_TREE = register("dark_sakura_tree", OmegaWorld.DARK_SAKURA_TREE.configure(FeatureConfig.DEFAULT));

    private static <FC extends FeatureConfig, F extends StructureFeature<FC>> ConfiguredStructureFeature<FC, F> register(String id, ConfiguredStructureFeature<FC, F> configuredStructureFeature) {
        return BuiltinRegistries.add(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, IntoTheOmega.id(id), configuredStructureFeature);
    }

    private static <FC extends FeatureConfig> ConfiguredFeature<FC, ?> register(String id, ConfiguredFeature<FC, ?> configuredFeature) {
        return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, IntoTheOmega.id(id), configuredFeature);
    }

    public static void init() {

    }

    private OmegaConfiguredFeatures() {

    }
}
