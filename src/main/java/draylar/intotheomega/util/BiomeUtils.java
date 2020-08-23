package draylar.intotheomega.util;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class BiomeUtils {

    // wtf
    public static void addFeatureToBiome(Biome biome, GenerationStep.Feature feature, ConfiguredFeature<?, ?> configuredFeature) {
        if(biome.getGenerationSettings().features instanceof ImmutableList) {
            biome.getGenerationSettings().features = biome.getGenerationSettings().features.stream().map(Lists::newArrayList).collect(Collectors.toList());
        }

        List<List<Supplier<ConfiguredFeature<?, ?>>>> features = biome.getGenerationSettings().features;
        while(features.size() <= feature.ordinal()) {
            features.add(Lists.newArrayList());
        }

        features.get(feature.ordinal()).add(() -> configuredFeature);
    }
}
