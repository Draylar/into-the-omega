package draylar.intotheomega.world.chorus_island;

import draylar.intotheomega.api.BlockInfo;
import draylar.intotheomega.api.OpenSimplex2F;
import draylar.intotheomega.api.Pos2D;
import draylar.intotheomega.registry.OmegaBlocks;
import draylar.intotheomega.registry.OmegaStructurePieces;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChorusIslandStructureStart extends SiftingStructureStart {

    public ChorusIslandStructureStart(StructureFeature<DefaultFeatureConfig> feature, int chunkX, int chunkZ, BlockBox box, int references, long seed) {
        super(OmegaStructurePieces.CHORUS_ISLAND, feature, chunkX, chunkZ, box, references, seed);

        int realX = chunkX * 16;
        int realZ = chunkZ * 16;

        this.boundingBox = new BlockBox(new int[]{
                realX - 100, 150, realZ - 100,
                realX + 100, 200, realZ + 100
        });
    }

    @Override
    public Map<BlockPos, BlockInfo> place(DynamicRegistryManager registryManager, ChunkGenerator chunkGenerator, StructureManager manager, int chunkX, int chunkZ, Biome biome, DefaultFeatureConfig config) {
        int height = 175;
        OpenSimplex2F noise = new OpenSimplex2F(random.nextInt(8911805));
        Map<BlockPos, BlockInfo> blocks = new HashMap<>();
        List<Pos2D> flatPositions = new ArrayList<>();
        BlockPos origin = new BlockPos(chunkX * 16, height, chunkZ * 16);

        long ms = System.currentTimeMillis();

        int radius = 150;
        int evalRadius = 100;
        int minY = -radius / 10;
        int maxY = radius / 10;

        // For each position inside the flat box of our island...
        for (int x = -radius; x <= radius; x++) {
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

                // For each x/z pairing, store the point if it is within the noise radius and not near walls.
                double distance2d = Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2)) - eval;
                if (distance2d <= evalRadius * 0.7) {
                    flatPositions.add(new Pos2D((int) realX, (int) realZ));
                }

                // Check each vertical position at this x/z pairing.
                for (double y = instMinY; y <= instMaxY; y++) {
                    double distance = Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2) + Math.pow(y, 2)) - eval;
                    double positionNoise = noise.noise3_XZBeforeY(realX / 50f, y / 25f, realZ / 50f);
                    double evalYOffset = evalRadius - positionNoise * 10;

                    // Place island blocks
                    if (distance <= evalYOffset) {
                        if (y >= instMaxY * .9 - (positionNoise * .2)) {
                            blocks.put(new BlockPos(realX, y + height, realZ), new BlockInfo(OmegaBlocks.CHORUS_GRASS.getDefaultState(), null));
                        } else {
                            blocks.put(new BlockPos(realX, y + height, realZ), new BlockInfo(Blocks.END_STONE.getDefaultState(), null));
                        }
                    }
                }
            }
        }

        // Now that the island has been placed, we need to carve out a circular cone.
        for (int size = 10; size > 0; size--) {
            int downRadius = size * 8;

            for (int x = -downRadius; x <= downRadius; x++) {
                for (int z = -downRadius; z <= downRadius; z++) {
                    double distance = Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2));

                    // Determine the TRUE world position of these positions.
                    double realX = origin.getX() + x;
                    double realZ = origin.getZ() + z;

                    // place block at bottom
                    double v = noise.noise2(realX / 50f, realZ / 50f);

                    // inside radius, place blocks down
                    if (distance <= downRadius - v * 3f) {
                        for (int y = 0; y < (10 - size); y++) {
                            blocks.put(new BlockPos(realX, height + maxY - y, realZ), new BlockInfo(Blocks.AIR.getDefaultState(), null));
                        }

                        if(size != 10) {
                            if (v > .25) {
                                blocks.put(new BlockPos(realX, height + maxY - (10 - size), realZ), new BlockInfo(Blocks.END_STONE_BRICKS.getDefaultState(), null));
                            } else {
                                blocks.put(new BlockPos(realX, height + maxY - (10 - size), realZ), new BlockInfo(Blocks.END_STONE.getDefaultState(), null));
                            }
                        }
                    }
                }
            }
        }

        // generate tree
        // first, place the trunk
        // trunk is a giant cylinder that is bigger at the base
        // iterate over each height
        for(int y = -10; y < 50; y++) {
            int width = (int) (- 3 * Math.log(Math.max(0.1, y + 10f)) + 15f);

            // base has

            for(int x = -width * 2; x <= width * 2; x++) {
                for(int z = -width * 2; z <= width * 2; z++) {
                    double distance = Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2));

                    // Determine the TRUE world position of these positions.
                    double realX = origin.getX() + x;
                    double realZ = origin.getZ() + z;
                    double eval = noise.noise2(realX / 10, realZ / 10) * 1;

                    if(distance <= width + eval) {
                        blocks.put(new BlockPos(realX, height + maxY + y, realZ), new BlockInfo(Blocks.END_STONE.getDefaultState(), null));
                    }
                }
            }
        }

        System.out.println(System.currentTimeMillis() - ms);

        return blocks;
    }

    @Override
    public void setBoundingBoxFromChildren() {

    }
}
