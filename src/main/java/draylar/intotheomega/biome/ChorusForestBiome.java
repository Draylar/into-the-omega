package draylar.intotheomega.biome;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.registry.OmegaBlocks;
import draylar.intotheomega.registry.OmegaEntities;
import draylar.intotheomega.registry.world.OmegaPlacedFeatures;
import net.minecraft.block.Blocks;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.math.VerticalSurfaceType;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.surfacebuilder.MaterialRules;

public class ChorusForestBiome {

    public static final RegistryKey<Biome> KEY = RegistryKey.of(Registry.BIOME_KEY, IntoTheOmega.id("chorus_forest"));

    public static Biome create() {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        DefaultBiomeFeatures.addEndMobs(spawnSettings);
        spawnSettings.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(OmegaEntities.CHORUS_COW, 5, 3, 6));

        GenerationSettings.Builder generationSettings = new GenerationSettings.Builder()
                .feature(GenerationStep.Feature.VEGETAL_DECORATION, OmegaPlacedFeatures.TALL_CHORUS_PLANT)
                .feature(GenerationStep.Feature.SURFACE_STRUCTURES, OmegaPlacedFeatures.ENDSTONE_PATCH)
                .feature(GenerationStep.Feature.UNDERGROUND_ORES, OmegaPlacedFeatures.OMEGA_ORE);

        return new Biome.Builder()
                .precipitation(Biome.Precipitation.NONE)
                .category(Biome.Category.THEEND)
                .temperature(0.5f)
                .downfall(0.0f)
                .effects(new BiomeEffects.Builder()
                        .waterColor(0x821080)
                        .waterFogColor(0x821080)
                        .fogColor(0x821080)
                        .skyColor(0x821080)
                        .build())
                .spawnSettings(spawnSettings.build())
                .generationSettings(generationSettings.build())
                .build();
    }

    public static MaterialRules.MaterialRule createSurfaceRule() {
        return MaterialRules.condition(
                MaterialRules.biome(KEY),
                MaterialRules.condition(
                        MaterialRules.stoneDepth(1, true, VerticalSurfaceType.FLOOR),
                        MaterialRules.block(OmegaBlocks.CHORUS_GRASS.getDefaultState())
                )
        );
    }
}
