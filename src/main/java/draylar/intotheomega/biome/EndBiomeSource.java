package draylar.intotheomega.biome;

import draylar.intotheomega.registry.OmegaBiomes;
import net.minecraft.util.math.noise.SimplexNoiseSampler;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;

/**
 * ITO biomes & chunk generation is split into <i>quadrants</i>.
 *
 * <p>
 * Each quadrant contains a ~4x4 grid of hexagons. Each hexagon represents the
 * standard unit of "one single large end island."
 */
public class EndBiomeSource {

    public static float getNoise(SimplexNoiseSampler sampler, int x, int z) {
        return 0.0f;
    }

    public static RegistryEntry<Biome> selectBiome(Registry<Biome> registry, int x, int y, int z, MultiNoiseUtil.MultiNoiseSampler noise) {
        return registry.getOrCreateEntry(BlackThornForestBiome.KEY);
    }
}
