package draylar.intotheomega.biome;

import com.mojang.blaze3d.systems.RenderSystem;
import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.registry.OmegaConfiguredFeatures;
import draylar.intotheomega.registry.OmegaParticles;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilders;

public class DarkSakuraForestBiome {

    public static final Biome INSTANCE = create();
    public static final RegistryKey<Biome> KEY = RegistryKey.of(Registry.BIOME_KEY, IntoTheOmega.id("dark_sakura_forest"));

    public static Biome create() {
        return new Biome.Builder()
                .precipitation(Biome.Precipitation.NONE)
                .category(Biome.Category.THEEND)
                .depth(0.125f)
                .scale(0.5f)
                .temperature(0.25f)
                .downfall(0.0f)
                .effects(new BiomeEffects.Builder()
                        .skyColor(0x424242)
                        .fogColor(0x424242)
                        .waterColor(0xffffff)
                        .waterFogColor(0xffffff)
                        .particleConfig(new BiomeParticleConfig(OmegaParticles.DARK_SAKURA_PETAL, 0.001f))
                        .build())
                .spawnSettings(createSpawnSettings())
                .generationSettings(createGenerationSettings())
                .build();
    }

    private static GenerationSettings createGenerationSettings() {
        return new GenerationSettings.Builder()
                .surfaceBuilder(ConfiguredSurfaceBuilders.END)
                .feature(GenerationStep.Feature.VEGETAL_DECORATION, OmegaConfiguredFeatures.DARK_SAKURA_TREE)
                .build();
    }

    private static SpawnSettings createSpawnSettings() {
        return new SpawnSettings.Builder().build();
    }
}
