package draylar.intotheomega.api.biome;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

import java.util.function.Predicate;

public class IslandBiomeData {

    private final RegistryKey<Biome> highlands;
    private final RegistryKey<Biome> midlands;
    private final RegistryKey<Biome> barrens;
    private final Predicate<BlockPos> spawnPredicate;

    public IslandBiomeData(RegistryKey<Biome> highlands, RegistryKey<Biome> midlands, RegistryKey<Biome> barrens, Predicate<BlockPos> spawnPredicate) {
        this.highlands = highlands;
        this.midlands = midlands;
        this.barrens = barrens;
        this.spawnPredicate = spawnPredicate;
    }

    public RegistryKey<Biome> getHighlands() {
        return highlands;
    }

    public RegistryKey<Biome> getMidlands() {
        return midlands;
    }

    public RegistryKey<Biome> getBarrens() {
        return barrens;
    }

    public boolean canSpawnAt(BlockPos pos) {
        return spawnPredicate == null || spawnPredicate.test(pos);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private RegistryKey<Biome> highlands;
        private RegistryKey<Biome> midlands;
        private RegistryKey<Biome> barrens;
        private Predicate<BlockPos> spawnPredicate = null;

        public Builder singleBiome(RegistryKey<Biome> biome) {
            highlands = biome;
            midlands = biome;
            barrens = biome;
            return this;
        }

        public Builder highlands(RegistryKey<Biome> biome) {
            highlands = biome;
            return this;
        }

        public Builder midlands(RegistryKey<Biome> biome) {
            midlands = biome;
            return this;
        }

        public Builder barrens(RegistryKey<Biome> biome) {
            barrens = biome;
            return this;
        }

        public Builder spawnPredicate(Predicate<BlockPos> spawnPredicate) {
            this.spawnPredicate = spawnPredicate;
            return this;
        }

        // TODO: separate params for distance over spawn predicate
        public Builder maxDistance(int distance) {
            this.spawnPredicate = pos -> {
                return Math.sqrt(pos.getSquaredDistance(0, 0, 0)) <= distance;
            };

            return this;
        }

        public IslandBiomeData build() {
            if(highlands == null) {
                throw new IllegalStateException("Highlands for IslandBiomeData was not specified!");
            }

            if(midlands == null) {
                throw new IllegalStateException("Midlands for IslandBiomeData was not specified!");
            }

            if(barrens == null) {
                throw new IllegalStateException("Barrens for IslandBiomeData was not specified!");
            }

            return new IslandBiomeData(highlands, midlands, barrens, spawnPredicate);
        }
    }
}
