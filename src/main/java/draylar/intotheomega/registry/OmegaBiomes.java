package draylar.intotheomega.registry;

import draylar.intotheomega.IntoTheOmega;
import net.fabricmc.fabric.api.biome.v1.TheEndBiomes;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilders;

public class OmegaBiomes {

    private static final Biome BLACK_THORN_FOREST = createBlackThornForest();
    public static final RegistryKey<Biome> BLACK_THORN_FOREST_KEY = RegistryKey.of(Registry.BIOME_KEY, IntoTheOmega.id("black_thorn_forest"));

    private static final Biome ABYSSAL_VOID = createAbyssalVoid();
    public static final RegistryKey<Biome> ABYSSAL_VOID_KEY = RegistryKey.of(Registry.BIOME_KEY, IntoTheOmega.id("abyssal_void"));

    private static final Biome ABYSSAL_CORE = createAbyssalVoid();
    public static final RegistryKey<Biome> ABYSSAL_CORE_KEY = RegistryKey.of(Registry.BIOME_KEY, IntoTheOmega.id("abyssal_core"));

    private static final Biome OMEGA_SLIME_WASTES = createSlimeWastes();
    public static final RegistryKey<Biome> OMEGA_SLIME_WASTES_KEY = RegistryKey.of(Registry.BIOME_KEY, IntoTheOmega.id("omega_slime_wastes"));

    private OmegaBiomes() {
        // NO-OP
    }

    public static void init() {
        Registry.register(BuiltinRegistries.BIOME, BLACK_THORN_FOREST_KEY.getValue(), BLACK_THORN_FOREST);
        Registry.register(BuiltinRegistries.BIOME, ABYSSAL_VOID_KEY.getValue(), ABYSSAL_VOID);
        Registry.register(BuiltinRegistries.BIOME, ABYSSAL_CORE_KEY.getValue(), ABYSSAL_CORE);
        Registry.register(BuiltinRegistries.BIOME, OMEGA_SLIME_WASTES_KEY.getValue(), OMEGA_SLIME_WASTES);

        TheEndBiomes.addHighlandsBiome(BLACK_THORN_FOREST_KEY, 1.0f);
        TheEndBiomes.addMidlandsBiome(BLACK_THORN_FOREST_KEY, BLACK_THORN_FOREST_KEY, 1.0f);
        TheEndBiomes.addBarrensBiome(BLACK_THORN_FOREST_KEY, BLACK_THORN_FOREST_KEY, 1.0f);
    }

    public static Biome createBlackThornForest() {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        DefaultBiomeFeatures.addEndMobs(spawnSettings);

        GenerationSettings.Builder generationSettings = new GenerationSettings.Builder();
        generationSettings.surfaceBuilder(ConfiguredSurfaceBuilders.END);
        generationSettings
                .feature(GenerationStep.Feature.SURFACE_STRUCTURES, OmegaConfiguredFeatures.OBISDIAN_SPIKE)
                .feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.CHORUS_PLANT)
                .feature(GenerationStep.Feature.UNDERGROUND_ORES, OmegaConfiguredFeatures.OMEGA_ORE)
                .feature(GenerationStep.Feature.SURFACE_STRUCTURES, OmegaConfiguredFeatures.END_PATCH)
                .feature(GenerationStep.Feature.VEGETAL_DECORATION, OmegaConfiguredFeatures.OBSIDISHROOM_PATCH)
                .feature(GenerationStep.Feature.VEGETAL_DECORATION, OmegaConfiguredFeatures.ENDERSHROOM_PATCH);

        return new Biome.Builder()
                .precipitation(Biome.Precipitation.NONE)
                .category(Biome.Category.THEEND)
                .depth(0.125f)
                .scale(0.5f)
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

    public static Biome createAbyssalVoid() {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        DefaultBiomeFeatures.addEndMobs(spawnSettings);

        GenerationSettings.Builder generationSettings = new GenerationSettings.Builder();
        generationSettings.surfaceBuilder(ConfiguredSurfaceBuilders.NOPE);

        return new Biome.Builder()
                .precipitation(Biome.Precipitation.NONE)
                .category(Biome.Category.NONE)
                .depth(0)
                .scale(0)
                .temperature(0.5f)
                .downfall(0.0f)
                .effects(new BiomeEffects.Builder()
                        .waterColor(4159204)
                        .waterFogColor(329011)
                        .fogColor(0xc0d8ff)
                        .skyColor(0x424242)
                        .build())
                .spawnSettings(spawnSettings.build())
                .generationSettings(generationSettings.build())
                .build();
    }

    public static Biome createSlimeWastes() {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        DefaultBiomeFeatures.addEndMobs(spawnSettings);

        GenerationSettings.Builder generationSettings = new GenerationSettings.Builder();
        generationSettings.surfaceBuilder(OmegaSurfaceBuilders.CONFIGURED_SLIME_WASTES);
        generationSettings.feature(GenerationStep.Feature.RAW_GENERATION, OmegaConfiguredFeatures.SLIME_PILLAR);

        return new Biome.Builder()
                .precipitation(Biome.Precipitation.NONE)
                .category(Biome.Category.THEEND)
                .depth(0.125f)
                .scale(0.5f)
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
