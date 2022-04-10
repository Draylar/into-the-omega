package draylar.intotheomega.biome;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.registry.OmegaEntities;
import draylar.intotheomega.registry.world.OmegaPlacedFeatures;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.GenerationStep;

public class OmegaSlimeWasteBiome {

    public static final RegistryKey<Biome> KEY = RegistryKey.of(Registry.BIOME_KEY, IntoTheOmega.id("omega_slime_wastes"));

    public static Biome create() {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        spawnSettings.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.SLIME, 10, 1, 4));
        spawnSettings.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(OmegaEntities.OMEGA_SLIME, 10, 1, 4));

        GenerationSettings.Builder generationSettings = new GenerationSettings.Builder();
//        generationSettings.surfaceBuilder(OmegaSurfaceBuilders.CONFIGURED_SLIME_WASTES);
        generationSettings.feature(GenerationStep.Feature.RAW_GENERATION, OmegaPlacedFeatures.SLIME_PILLAR);
        generationSettings.feature(GenerationStep.Feature.LAKES, OmegaPlacedFeatures.SLIME_LAKE);
        generationSettings.feature(GenerationStep.Feature.UNDERGROUND_STRUCTURES, OmegaPlacedFeatures.SLIME_DUNGEON);
        generationSettings.feature(GenerationStep.Feature.SURFACE_STRUCTURES, OmegaPlacedFeatures.SLIME_DUNGEON);

        return new Biome.Builder()
                .precipitation(Biome.Precipitation.NONE)
                .category(Biome.Category.THEEND)
                .temperature(1.0f)
                .downfall(0.0f)
                .effects(new BiomeEffects.Builder()
                        .waterColor(0x3f76e4)
                        .waterFogColor(0x050533)
                        .fogColor(0xc0d8ff)
                        .skyColor(0x424242)
                        .build())
                .spawnSettings(spawnSettings.build())
                .generationSettings(generationSettings.build())
                .build();
    }

}
