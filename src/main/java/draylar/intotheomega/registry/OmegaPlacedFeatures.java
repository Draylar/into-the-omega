package draylar.intotheomega.registry;

import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.gen.decorator.BiomePlacementModifier;
import net.minecraft.world.gen.decorator.CountPlacementModifier;
import net.minecraft.world.gen.decorator.RarityFilterPlacementModifier;
import net.minecraft.world.gen.decorator.SquarePlacementModifier;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;

public class OmegaPlacedFeatures {

    public static final PlacedFeature DARK_SAKURA_TREE = PlacedFeatures.register("dark_sakura_tree", OmegaConfiguredFeatures.DARK_SAKURA_TREE.withPlacement(VegetationPlacedFeatures.modifiers(PlacedFeatures.createCountExtraModifier(10, 0.1f, 1))));
    public static final PlacedFeature SLIME_PILLAR = PlacedFeatures.register("slime_pillar", OmegaConfiguredFeatures.SLIME_PILLAR.withPlacement(SquarePlacementModifier.of()));
    public static final PlacedFeature SLIME_LAKE = PlacedFeatures.register("slime_lake", OmegaConfiguredFeatures.SLIME_LAKE.withPlacement(RarityFilterPlacementModifier.of(100), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of()));
    public static final PlacedFeature SLIME_DUNGEON = PlacedFeatures.register("slime_dungeon", OmegaConfiguredFeatures.SLIME_DUNGEON.withPlacement(RarityFilterPlacementModifier.of(100), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of()));
    public static final PlacedFeature OBSIDIAN_SPIKE = PlacedFeatures.register("obsidian_spike", OmegaConfiguredFeatures.OBISDIAN_SPIKE.withPlacement(RarityFilterPlacementModifier.of(100), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of()));
    public static final PlacedFeature OMEGA_ORE = PlacedFeatures.register("omega_ore", OmegaConfiguredFeatures.OMEGA_ORE.withPlacement(RarityFilterPlacementModifier.of(100), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of()));
    public static final PlacedFeature COARSE_PATCH = PlacedFeatures.register("coarse_patch", OmegaConfiguredFeatures.COARSE_PATCH.withPlacement(RarityFilterPlacementModifier.of(100), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of()));
    public static final PlacedFeature OBSIDISHROOM_PATCH = PlacedFeatures.register("obsidishroom_patch", OmegaConfiguredFeatures.OBSIDISHROOM_PATCH.withPlacement(RarityFilterPlacementModifier.of(100), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of()));
    public static final PlacedFeature ENDERSHROOM_PATCH = PlacedFeatures.register("endershroom_patch", OmegaConfiguredFeatures.ENDERSHROOM_PATCH.withPlacement(RarityFilterPlacementModifier.of(100), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of()));
    public static final PlacedFeature ENDSTONE_PATCH = PlacedFeatures.register("endstone_patch", OmegaConfiguredFeatures.ENDSTONE_PATCH.withPlacement(SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of()));
    public static final PlacedFeature FOREST_CHORUS_PLANT = PlacedFeatures.register("forest_chorus_plant", OmegaConfiguredFeatures.FOREST_CHORUS_PLANT.withPlacement(CountPlacementModifier.of(UniformIntProvider.create(4, 8)), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of()));

    public static void init() {

    }
}
