package draylar.intotheomega.registry;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.api.biome.IslandBiomeData;
import draylar.intotheomega.api.biome.OmegaEndBiomePicker;
import draylar.intotheomega.biome.*;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.*;

import java.util.Arrays;
import java.util.List;

public class OmegaBiomes {

    private static final Biome ABYSSAL_CORE = AbyssalVoidBiome.create();
    public static final RegistryKey<Biome> ABYSSAL_CORE_KEY = RegistryKey.of(Registry.BIOME_KEY, IntoTheOmega.id("abyssal_core"));
    public static final List<RegistryKey<Biome>> USED_BIOMES = Arrays.asList(
            BiomeKeys.THE_END,
            BiomeKeys.END_BARRENS,
            BiomeKeys.END_HIGHLANDS,
            BiomeKeys.END_MIDLANDS,
            BiomeKeys.SMALL_END_ISLANDS,
            BlackThornForestBiome.KEY,
            AbyssalVoidBiome.KEY,
            ABYSSAL_CORE_KEY,
            OmegaSlimeWasteBiome.KEY,
            GlitterBiome.KEY,
            CrystaliteBiome.KEY,
            DarkSakuraForestBiome.KEY
    );

    private OmegaBiomes() {
        // NO-OP
    }

    public static void init() {
        Registry.register(BuiltinRegistries.BIOME, BlackThornForestBiome.KEY, BlackThornForestBiome.create());
        Registry.register(BuiltinRegistries.BIOME, AbyssalVoidBiome.KEY, AbyssalVoidBiome.create());
        Registry.register(BuiltinRegistries.BIOME, ABYSSAL_CORE_KEY, ABYSSAL_CORE);
        Registry.register(BuiltinRegistries.BIOME, OmegaSlimeWasteBiome.KEY, OmegaSlimeWasteBiome.create());
        Registry.register(BuiltinRegistries.BIOME, GlitterBiome.KEY, GlitterBiome.create());
        Registry.register(BuiltinRegistries.BIOME, ChorusForestBiome.KEY, ChorusForestBiome.create());
        Registry.register(BuiltinRegistries.BIOME, CrystaliteBiome.KEY, CrystaliteBiome.create());
        Registry.register(BuiltinRegistries.BIOME, DarkSakuraForestBiome.KEY, DarkSakuraForestBiome.create());

        // Zone 1 runs from 2,000 to 15,000.
        // The default End Island will spawn 50% of the time, regardless of weight.
        OmegaEndBiomePicker.register(IslandBiomeData.builder().singleBiome(BlackThornForestBiome.KEY).maxDistance(Integer.MAX_VALUE).build(), 1.0d);
        OmegaEndBiomePicker.register(IslandBiomeData.builder().singleBiome(CrystaliteBiome.KEY).barrens(BiomeKeys.END_BARRENS).maxDistance(Integer.MAX_VALUE).build(), 1.0d);
        OmegaEndBiomePicker.register(IslandBiomeData.builder().singleBiome(ChorusForestBiome.KEY).maxDistance(Integer.MAX_VALUE).build(), 1.0d);
        OmegaEndBiomePicker.register(IslandBiomeData.builder().singleBiome(OmegaSlimeWasteBiome.KEY).maxDistance(Integer.MAX_VALUE).build(), 1.0d);


        // TODO: if a biome is used anywhere & is not in the OmegaBiomes list, throw a big exception to prevent features from not generating.

        // Zone 2 [ 15,000 -> 30,000 ]

        // Zone 3 [ 30,000 -> 50,000 ]

        if(FabricLoader.getInstance().isDevelopmentEnvironment()) {
            OmegaEndBiomePicker.solo(IslandBiomeData.builder().singleBiome(BlackThornForestBiome.KEY).maxDistance(Integer.MAX_VALUE).build());
        }
    }
}
