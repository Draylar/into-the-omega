package draylar.intotheomega.biome;

import draylar.intotheomega.IntoTheOmega;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;

public class CrystaliteBiome {

    public static final RegistryKey<Biome> KEY = RegistryKey.of(Registry.BIOME_KEY, IntoTheOmega.id("crystalite"));

    public static Biome create() {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        DefaultBiomeFeatures.addEndMobs(spawnSettings);

        GenerationSettings.Builder generationSettings = new GenerationSettings.Builder();
//        generationSettings.surfaceBuilder(OmegaSurfaceBuilders.CRYSTALITE_SURFACE_BUILDER);
//        generationSettings
//                .feature(GenerationStep.Feature.UNDERGROUND_STRUCTURES, OmegaConfiguredFeatures.CRYSTALITE_CAVERN)
//                .feature(GenerationStep.Feature.SURFACE_STRUCTURES, OmegaConfiguredFeatures.CRYSTALITE_SPIKE);

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
