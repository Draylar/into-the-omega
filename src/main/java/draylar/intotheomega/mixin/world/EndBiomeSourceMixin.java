package draylar.intotheomega.mixin.world;

import draylar.intotheomega.api.Vec2i;
import draylar.intotheomega.api.biome.IslandBiomeData;
import draylar.intotheomega.api.biome.OmegaEndBiomePicker;
import draylar.intotheomega.biome.OmegaSlimeWasteBiome;
import draylar.intotheomega.registry.OmegaBiomes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.noise.SimplexNoiseSampler;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.source.TheEndBiomeSource;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Fabric API provides an API for manipulating end biomes, but it is limited in control.
 * For specific cases where we need to control exactly how biomes spawn,
 * this mixin takes priority over the Fabric API.
 */
@Mixin(TheEndBiomeSource.class)
public abstract class EndBiomeSourceMixin {

    @Unique private static final Direction[] HORIZONTAL_DIRECTIONS = new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST};

    @Shadow @Final private long seed;
    @Shadow @Final private Biome centerBiome;
    @Shadow @Final private SimplexNoiseSampler noise;
    @Shadow @Final private Biome smallIslandsBiome;
    @Shadow @Final private Registry<Biome> biomeRegistry;
    @Unique private static long cachedSeed = Integer.MAX_VALUE;
    @Unique private static List<RegistryKey<Biome>> biomeSections = new ArrayList<>();
    @Unique private static Map<Vec2i, Biome> cachedBiomePositions = new ConcurrentHashMap<>();
    @Unique private static Map<Vec2i, Float> cachedNoise = new ConcurrentHashMap<>();
    @Unique private static Random random = new Random();

    @Inject(method = "getBiome", at = @At("HEAD"))
    private void initializeNoise(int x, int y, int z, MultiNoiseUtil.MultiNoiseSampler noise, CallbackInfoReturnable<Biome> cir) {
        if(cachedSeed != seed) {
            cachedSeed = seed;

            // initialize biome sections
            // we previously did this in a static block, but that was causing some sort of registry race condition
            // I think biome sources are loaded super early compared to everything else[??]
            biomeSections.clear();
            biomeSections.add(OmegaSlimeWasteBiome.KEY);
            biomeSections.add(BiomeKeys.JUNGLE);

            // 3/5 do not have content rn
            biomeSections.add(null);
            biomeSections.add(null);
            biomeSections.add(null);
        }
    }

    /**
     * @author Draylar
     */
    // Coordinates passed in are the real-world coordinates divided by 4.
    // eg. (100, 50, 100) is (25, ?, 25), and we skip 4 blocks per biome sample.
    @Inject(at = @At("HEAD"), method = "getBiome", cancellable = true)
    public void getBiomeForNoiseGen(int x, int y, int z, MultiNoiseUtil.MultiNoiseSampler noise, CallbackInfoReturnable<Biome> cir) {
        int chunkX = x / 4;
        int chunkZ = z / 4;

        // If we are within the central area of The End, use the standard End biome for the main island.
        if((long) chunkX * (long) chunkX + (long) chunkZ * (long) chunkZ <= 4096L) {
            cir.setReturnValue(centerBiome);
        }

        // Outside the central island.
        else {
            Vec2i current = new Vec2i(x, z);

            // If we assigned this column position previously, use that value.
            if(cachedBiomePositions.containsKey(current)) {
                Biome biome = cachedBiomePositions.get(current);
                cir.setReturnValue(biome);
                return;
            }

            // Cached void
            if(cachedNoise.containsKey(current) && !cachedBiomePositions.containsKey(current)) {
                cir.setReturnValue(smallIslandsBiome);
                return;
            }

            // We have not yet assigned a value.
            // If the noise value is above our threshold,
            float chunkNoise = TheEndBiomeSource.getNoiseAt(this.noise, chunkX * 2 + 1, chunkZ * 2 + 1);

            // Void.
            if(chunkNoise < -20.0f) {
                cir.setReturnValue(smallIslandsBiome);
                return;
            }

            // Unselected island. Pick one now.
            IslandBiomeData islandBiome = OmegaEndBiomePicker.pick(new BlockPos(x * 4, 0, z * 4));

            // IF we are in an island, print out the collected noise group.
            // This should be always be a high number because cached values return earlier.
            if(chunkNoise >= -20.0f) {
                Set<Vec2i> yeet = locateIslandBlocks(current);

                // cache the starting biome to prevent it from potentially double checking in the future - probably not needed
                cachedBiomePositions.put(current, biomeRegistry.get(getBiomeFor(islandBiome, chunkNoise)));

                // assign cache biomes after processing island
                yeet.forEach(pos -> {
                    // TODO: cached biome based on posNoise
                    // TODO: combine with down
                    cachedBiomePositions.put(pos, biomeRegistry.get(getBiomeFor(islandBiome, cachedNoise.get(pos))));
                    // TODO: WE CAN CLEAR CACHED NOISE HERE
                });
            }

            // Fallback for the first block in an island.
            // TODO: combine with ^^
            cir.setReturnValue(biomeRegistry.get(getBiomeFor(islandBiome, chunkNoise)));
        }
    }

    private RegistryKey<Biome> getBiomeFor(IslandBiomeData islandBiome, float noise) {
        if(noise > 40.0F) {
            return islandBiome.getHighlands();
        } else if(noise >= 0.0F) {
            return islandBiome.getMidlands();
        } else {
            return islandBiome.getBarrens();
        }
    }

    private Set<Vec2i> locateIslandBlocks(Vec2i from) {
        Set<Vec2i> island = new HashSet<>(); // contains every valid position
        List<Vec2i> toProcess = new ArrayList<>(); // every position we NEED to process
        Set<Vec2i> finished = new HashSet<>(); // contains every position we have FINISHED processing

        toProcess.add(from);
        island.add(from);
        finished.add(from);

        ListIterator<Vec2i> iterator = toProcess.listIterator();
        while (iterator.hasNext()) {
            Vec2i next = iterator.next();
            iterator.remove();
            finished.add(next);

            // are we in the island?
            float offsetNoise = TheEndBiomeSource.getNoiseAt(noise, (next.x / 4) * 2 + 1, (next.z / 4) * 2 + 1);
            cachedNoise.put(next, offsetNoise);

            if(offsetNoise >= -20.0f) {
                island.add(next);

                // check neighbors
                for (Direction direction : HORIZONTAL_DIRECTIONS) {
                    Vec2i offset = new Vec2i(next.x + direction.getOffsetX(), next.z + direction.getOffsetZ());
                    if(!finished.contains(offset)) {
                        iterator.add(offset);
                        iterator.previous();
                    }
                }
            }
        }

        return island;
    }
}
