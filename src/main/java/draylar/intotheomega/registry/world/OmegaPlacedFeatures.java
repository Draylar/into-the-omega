package draylar.intotheomega.registry.world;

import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.EndConfiguredFeatures;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;
import net.minecraft.world.gen.placementmodifier.BiomePlacementModifier;
import net.minecraft.world.gen.placementmodifier.CountPlacementModifier;
import net.minecraft.world.gen.placementmodifier.RarityFilterPlacementModifier;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;

public class OmegaPlacedFeatures {

    public static final RegistryEntry<PlacedFeature> DARK_SAKURA_TREE = PlacedFeatures.register("dark_sakura_tree", OmegaConfiguredFeatures.DARK_SAKURA_TREE, VegetationPlacedFeatures.modifiers(PlacedFeatures.createCountExtraModifier(10, 0.1f, 1)));
    public static final RegistryEntry<PlacedFeature> SLIME_PILLAR = PlacedFeatures.register("slime_pillar", OmegaConfiguredFeatures.SLIME_PILLAR, SquarePlacementModifier.of());
    public static final RegistryEntry<PlacedFeature> SLIME_LAKE = PlacedFeatures.register("slime_lake", OmegaConfiguredFeatures.SLIME_LAKE, RarityFilterPlacementModifier.of(100), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
    public static final RegistryEntry<PlacedFeature> SLIME_DUNGEON = PlacedFeatures.register("slime_dungeon", OmegaConfiguredFeatures.SLIME_DUNGEON, RarityFilterPlacementModifier.of(100), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
    public static final RegistryEntry<PlacedFeature> OBSIDIAN_SPIKE = PlacedFeatures.register("obsidian_spike", OmegaConfiguredFeatures.OBISDIAN_SPIKE, RarityFilterPlacementModifier.of(1), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
    public static final RegistryEntry<PlacedFeature> OMEGA_ORE = PlacedFeatures.register("omega_ore", OmegaConfiguredFeatures.OMEGA_ORE, RarityFilterPlacementModifier.of(100), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
    public static final RegistryEntry<PlacedFeature> COARSE_PATCH = PlacedFeatures.register("coarse_patch", OmegaConfiguredFeatures.COARSE_PATCH, RarityFilterPlacementModifier.of(1), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
    public static final RegistryEntry<PlacedFeature> OBSIDISHROOM_PATCH = PlacedFeatures.register("obsidishroom_patch", OmegaConfiguredFeatures.OBSIDISHROOM_PATCH, RarityFilterPlacementModifier.of(100), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
    public static final RegistryEntry<PlacedFeature> ENDERSHROOM_PATCH = PlacedFeatures.register("endershroom_patch", OmegaConfiguredFeatures.ENDERSHROOM_PATCH, RarityFilterPlacementModifier.of(100), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
    public static final RegistryEntry<PlacedFeature> ENDSTONE_PATCH = PlacedFeatures.register("endstone_patch", OmegaConfiguredFeatures.ENDSTONE_PATCH, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
    public static final RegistryEntry<PlacedFeature> FOREST_CHORUS_PLANT = PlacedFeatures.register("forest_chorus_plant", OmegaConfiguredFeatures.FOREST_CHORUS_PLANT, CountPlacementModifier.of(UniformIntProvider.create(4, 8)), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());

    // Dark Thorn Forest
    public static final RegistryEntry<PlacedFeature> DARK_THORN_CHORUS_PLANT = PlacedFeatures.register("dark_thorn_chorus_plant", EndConfiguredFeatures.CHORUS_PLANT, CountPlacementModifier.of(UniformIntProvider.create(0, 3)), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());

    public static void init() {

    }
}
