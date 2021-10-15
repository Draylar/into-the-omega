package draylar.intotheomega.api.biome;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.BiomeKeys;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

public class OmegaEndBiomePicker {

    public static final IslandBiomeData DEFAULT = IslandBiomeData.builder()
            .highlands(BiomeKeys.END_HIGHLANDS)
            .midlands(BiomeKeys.END_MIDLANDS)
            .barrens(BiomeKeys.END_BARRENS)
            .build();

    private static final NavigableMap<Double, IslandBiomeData> ALL_ISLAND_BIOMES = new TreeMap<>();
    private static final Random random = new Random();
    private static double totalWeight = 0.0d;

    public static void register(IslandBiomeData biome, double weight) {
        // Do not allow weights of 0.0!
        if(weight <= 0) {
            return;
        }

        ALL_ISLAND_BIOMES.put(weight, biome);
        totalWeight += weight;
    }

    // TODO: does it make sense to pick based off noise, so that the same area always spawns the same biome?
    public static IslandBiomeData pick(BlockPos at) {
        if(random.nextDouble() <= 0.5) {
            return DEFAULT;
        }

        // TODO: WHAT IF EMPTY????????

        NavigableMap<Double, IslandBiomeData> filteredOptions =
                new TreeMap<>(ALL_ISLAND_BIOMES.entrySet().stream().filter(entry -> entry.getValue().canSpawnAt(at)).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));

        double index = random.nextDouble() * totalWeight;
        @Nullable Map.Entry<Double, IslandBiomeData> value = filteredOptions.higherEntry(index);

        if(value != null) {
            return value.getValue();
        } else {
            return DEFAULT;
        }
    }
}
