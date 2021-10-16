package draylar.intotheomega.api.biome;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.BiomeKeys;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class OmegaEndBiomePicker {

    public static final IslandBiomeData DEFAULT = IslandBiomeData.builder()
            .highlands(BiomeKeys.END_HIGHLANDS)
            .midlands(BiomeKeys.END_MIDLANDS)
            .barrens(BiomeKeys.END_BARRENS)
            .build();

    private static final Map<IslandBiomeData, Double> ISLANDS = new HashMap<>();
    private static final Random random = new Random();
    private static double totalWeight = 0.0d;

    public static void register(IslandBiomeData biome, double weight) {
        // Do not allow weights of 0.0!
        if(weight <= 0) {
            return;
        }

        ISLANDS.put(biome, weight);
        totalWeight += weight;
    }

    // TODO: does it make sense to pick based off noise, so that the same area always spawns the same biome?
    public static IslandBiomeData pick(BlockPos at) {
        if(random.nextDouble() <= 0.5) {
            return DEFAULT;
        }

        // TODO: WHAT IF EMPTY????????

        Map<IslandBiomeData, Double> filteredOptions =
                ISLANDS.entrySet().stream().filter(entry -> entry.getKey().canSpawnAt(at)).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        double picked = random.nextDouble() * totalWeight;
        double cumulativeSum = 0.0d;
        for(Map.Entry<IslandBiomeData, Double> entry : filteredOptions.entrySet()) {
            cumulativeSum += entry.getValue();

            if(picked <= cumulativeSum) {
                System.out.println(entry.getKey().getBarrens().toString());
                return entry.getKey();
            }
        }

        return DEFAULT;
    }
}
