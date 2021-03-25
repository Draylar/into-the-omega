package draylar.intotheomega.registry;

import com.google.common.collect.ImmutableList;
import draylar.intotheomega.IntoTheOmega;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.UniformIntDistribution;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placer.SimpleBlockPlacer;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;

public class OmegaConfiguredFeatures {

    // Structure Features
    public static final ConfiguredStructureFeature<DefaultFeatureConfig, ? extends StructureFeature<DefaultFeatureConfig>> EYE_ALTAR = register("eye_altar", OmegaWorld.EYE_ALTAR.configure(new DefaultFeatureConfig()));
    public static final ConfiguredStructureFeature<DefaultFeatureConfig, ? extends StructureFeature<DefaultFeatureConfig>> SMALL_PHANTOM_TOWER = register("small_phantom_tower", OmegaWorld.SMALL_PHANTOM_TOWER.configure(new DefaultFeatureConfig()));
    public static final ConfiguredStructureFeature<DefaultFeatureConfig, ? extends StructureFeature<DefaultFeatureConfig>> MEDIUM_PHANTOM_TOWER = register("medium_phantom_tower", OmegaWorld.MEDIUM_PHANTOM_TOWER.configure(new DefaultFeatureConfig()));
    public static final ConfiguredStructureFeature<?, ?> CONFIGURED_SPIKE = register("spike", OmegaWorld.SPIKE.configure(DefaultFeatureConfig.INSTANCE));

    public static final ConfiguredStructureFeature<?, ?> CONFIGURED_BASE_ISLAND = register("base_island", OmegaWorld.BASE_ISLAND.configure(DefaultFeatureConfig.INSTANCE));
    public static final ConfiguredStructureFeature<?, ?> CONFIGURED_GENERIC_ISLAND = register("generic_island", OmegaWorld.GENERIC_ISLAND.configure(DefaultFeatureConfig.INSTANCE));
    public static final ConfiguredStructureFeature<?, ?> CONFIGURED_ICE_ISLAND = register("ice_island", OmegaWorld.ICE_ISLAND.configure(DefaultFeatureConfig.INSTANCE));
    public static final ConfiguredStructureFeature<?, ?> CONFIGURED_CHORUS_ISLAND = register("chorus_island", OmegaWorld.CHORUS_ISLAND.configure(DefaultFeatureConfig.INSTANCE));

    public static final ConfiguredStructureFeature<?, ?> ABYSS_FLOWER_ISLAND = register("abyss_flower_island", OmegaWorld.ABYSS_FLOWER_ISLAND.configure(DefaultFeatureConfig.INSTANCE));

    // Standard Features
    public static final ConfiguredFeature<?, ?> OBISDIAN_SPIKE = register("obsidian_spike", OmegaWorld.OBSIDIAN_SPIKE.configure(FeatureConfig.DEFAULT).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP).repeat(1));
    public static final ConfiguredFeature<?, ?> OMEGA_ORE = register("ore_diamond", OmegaWorld.OMEGA_ORE.configure(DefaultFeatureConfig.INSTANCE).method_30377(8).spreadHorizontally());
    public static final ConfiguredFeature<?, ?> END_PATCH = register("cracked_end_stone_patch", OmegaWorld.END_PATCH.configure(new DiskFeatureConfig(Blocks.COARSE_DIRT.getDefaultState(), UniformIntDistribution.of(4, 3), 3, ImmutableList.of(Blocks.END_STONE.getDefaultState()))).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP).repeat(2));
    public static final ConfiguredFeature<?, ?> OBSIDISHROOM_PATCH = register("obisishroom_patch", Feature.RANDOM_PATCH.configure(new RandomPatchFeatureConfig.Builder(new SimpleBlockStateProvider(OmegaBlocks.OBSIDISHROOM.getDefaultState()), SimpleBlockPlacer.INSTANCE).tries(64).build()).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP_SPREAD_DOUBLE).applyChance(8));
    public static final ConfiguredFeature<?, ?> ENDERSHROOM_PATCH = register("endershroom_patch", Feature.RANDOM_PATCH.configure(new RandomPatchFeatureConfig.Builder(new SimpleBlockStateProvider(OmegaBlocks.ENDERSHROOM.getDefaultState()), SimpleBlockPlacer.INSTANCE).tries(64).build()).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP_SPREAD_DOUBLE).applyChance(8));

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
