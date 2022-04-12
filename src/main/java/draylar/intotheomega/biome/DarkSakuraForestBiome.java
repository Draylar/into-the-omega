package draylar.intotheomega.biome;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.registry.OmegaParticles;
import draylar.intotheomega.registry.world.OmegaPlacedFeatures;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;

public class DarkSakuraForestBiome {

    public static final RegistryKey<Biome> KEY = RegistryKey.of(Registry.BIOME_KEY, IntoTheOmega.id("dark_sakura_forest"));

    public static Biome create() {
        GenerationSettings.Builder generationSettings = new GenerationSettings
                .Builder()
                .feature(GenerationStep.Feature.VEGETAL_DECORATION, OmegaPlacedFeatures.DARK_SAKURA_TREE);

        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        DefaultBiomeFeatures.addEndMobs(spawnSettings);

        return new Biome.Builder()
                .precipitation(Biome.Precipitation.NONE)
                .category(Biome.Category.THEEND)
                .temperature(0.25f)
                .downfall(0.0f)
                .effects(new BiomeEffects.Builder()
                        .skyColor(0x424242)
                        .fogColor(0x424242)
                        .waterColor(0xffffff)
                        .waterFogColor(0xffffff)
                        .particleConfig(new BiomeParticleConfig(OmegaParticles.DARK_SAKURA_PETAL, 0.001f))
                        .build())
                .spawnSettings(spawnSettings.build())
                .generationSettings(generationSettings.build())
                .build();
    }
}
