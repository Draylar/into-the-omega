package draylar.intotheomega.biome;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.registry.world.OmegaConfiguredFeatures;
import draylar.intotheomega.registry.world.OmegaPlacedFeatures;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.EndPlacedFeatures;

public class BlackThornForestBiome {

    public static final RegistryKey<Biome> KEY = RegistryKey.of(Registry.BIOME_KEY, IntoTheOmega.id("black_thorn_forest"));

    public static Biome create() {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        DefaultBiomeFeatures.addEndMobs(spawnSettings);

        GenerationSettings.Builder generationSettings = new GenerationSettings.Builder();
//        generationSettings.surfaceBuilder(ConfiguredSurfaceBuilders.END);
        generationSettings
//                .structureFeature(OmegaConfiguredFeatures.ENIGMA_KING_SPIKE)
                .feature(GenerationStep.Feature.SURFACE_STRUCTURES, OmegaPlacedFeatures.OBSIDIAN_SPIKE)
                .feature(GenerationStep.Feature.VEGETAL_DECORATION, OmegaPlacedFeatures.DARK_THORN_CHORUS_PLANT)
                .feature(GenerationStep.Feature.UNDERGROUND_ORES, OmegaPlacedFeatures.OMEGA_ORE)
                .feature(GenerationStep.Feature.SURFACE_STRUCTURES, OmegaPlacedFeatures.COARSE_PATCH)
                .feature(GenerationStep.Feature.VEGETAL_DECORATION, OmegaPlacedFeatures.OBSIDISHROOM_PATCH)
                .feature(GenerationStep.Feature.VEGETAL_DECORATION, OmegaPlacedFeatures.ENDERSHROOM_PATCH);

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
                        .particleConfig(new BiomeParticleConfig(ParticleTypes.WHITE_ASH, 0.059046667f))
                        .loopSound(SoundEvents.AMBIENT_BASALT_DELTAS_LOOP)
                        .build())
                .spawnSettings(spawnSettings.build())
                .generationSettings(generationSettings.build())
                .build();
    }
}
