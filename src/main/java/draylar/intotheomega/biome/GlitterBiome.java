package draylar.intotheomega.biome;

import draylar.intotheomega.IntoTheOmega;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.*;

public class GlitterBiome {

    public static final RegistryKey<Biome> KEY = RegistryKey.of(Registry.BIOME_KEY, IntoTheOmega.id("glitter_biome"));

    public static Biome create() {
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
                        .particleConfig(new BiomeParticleConfig(ParticleTypes.END_ROD, 0.02f))
                        .build())
                .spawnSettings(createSpawnSettings())
                .generationSettings(createGenerationSettings())
                .build();
    }

    private static GenerationSettings createGenerationSettings() {
        return new GenerationSettings.Builder()
                .build();
    }

    private static SpawnSettings createSpawnSettings() {
        return new SpawnSettings.Builder().build();
    }
}
