package draylar.intotheomega.world.island;

import draylar.intotheomega.api.BlockInfo;
import draylar.intotheomega.api.OpenSimplex2F;
import draylar.intotheomega.world.api.SiftingStructureStart;
import net.minecraft.block.Blocks;
import net.minecraft.structure.StructureManager;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

import java.util.HashMap;
import java.util.Map;

public class IslandStructureStart extends SiftingStructureStart {

    public IslandStructureStart(StructureFeature<DefaultFeatureConfig> feature, int chunkX, int chunkZ, BlockBox box, int references, long seed) {
        super(feature, chunkX, chunkZ, box, references, seed);
    }

    @Override
    public Map<BlockPos, BlockInfo> place(DynamicRegistryManager registryManager, ChunkGenerator chunkGenerator, StructureManager manager, int chunkX, int chunkZ, Biome biome, DefaultFeatureConfig config) {
        int height = 175;
        OpenSimplex2F noise = new OpenSimplex2F(random.nextInt(8911805));
        Map<BlockPos, BlockInfo> blocks = new HashMap<>();
        BlockPos origin = new BlockPos(chunkX * 16, height, chunkZ * 16);

        int radius = 64;
        int evalRadius = 48;
        int minY = -radius / 6;
        int maxY = radius / 6;

        // For each position inside the flat box of our island...
        for(int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {

                // Determine the TRUE world position of these positions.
                double realX = origin.getX() + x;
                double realZ = origin.getZ() + z;

                // Evaluate noise at this position for island shape
                double eval = noise.noise2(realX / 50f, realZ / 50f) * 15;

                // The maximum/minimum value of each x/z pairing is adjusted up to 1 by a general noise call.
                // This makes floors/ceilings not flat.
                double minNoise = Math.max(0, noise.noise2(realX / 25f, realZ / 25f)) * 2f;
                double instMinY = minY - minNoise;
                double instMaxY = maxY - minNoise;

                // Check each vertical position at this x/z pairing.
                for(double y = instMinY; y <= instMaxY; y++) {
                    double distance = Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2) + Math.pow(y, 2)) - eval;

                    // Place island blocks
                    if(distance <= evalRadius - noise.noise3_XZBeforeY(realX / 50f, y / 25f, realZ / 50f) * 10) {
                        blocks.put(new BlockPos(realX, y + height, realZ), new BlockInfo(Blocks.END_STONE.getDefaultState(), null));
                    }
                }
            }
        }

        return blocks;
    }
}
