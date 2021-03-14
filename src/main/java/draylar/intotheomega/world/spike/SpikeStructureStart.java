package draylar.intotheomega.world.spike;

import draylar.intotheomega.api.OpenSimplex2F;
import draylar.intotheomega.registry.OmegaBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SpikeStructureStart extends StructureStart<DefaultFeatureConfig> {

    public SpikeStructureStart(StructureFeature<DefaultFeatureConfig> feature, int chunkX, int chunkZ, BlockBox box, int references, long seed) {
        super(feature, chunkX, chunkZ, box, references, seed);
    }

    private static int getGenerationHeight(int chunkX, int chunkZ, ChunkGenerator chunkGenerator) {
        Random random = new Random(chunkX + chunkZ * 10387313);
        BlockRotation rotation = BlockRotation.random(random);

        int xOffset = 5;
        int zOffset = 5;
        if (rotation == BlockRotation.CLOCKWISE_90) {
            xOffset = -5;
        } else if (rotation == BlockRotation.CLOCKWISE_180) {
            xOffset = -5;
            zOffset = -5;
        } else if (rotation == BlockRotation.COUNTERCLOCKWISE_90) {
            zOffset = -5;
        }

        int x = (chunkX << 4) + 7;
        int z = (chunkZ << 4) + 7;
        int m = chunkGenerator.getHeightInGround(x, z, Heightmap.Type.WORLD_SURFACE_WG);
        int n = chunkGenerator.getHeightInGround(x, z + zOffset, Heightmap.Type.WORLD_SURFACE_WG);
        int o = chunkGenerator.getHeightInGround(x + xOffset, z, Heightmap.Type.WORLD_SURFACE_WG);
        int p = chunkGenerator.getHeightInGround(x + xOffset, z + zOffset, Heightmap.Type.WORLD_SURFACE_WG);
        return Math.min(Math.min(m, n), Math.min(o, p));
    }

    @Override
    public void init(DynamicRegistryManager registryManager, ChunkGenerator chunkGenerator, StructureManager manager, int chunkX, int chunkZ, Biome biome, DefaultFeatureConfig config) {
        OpenSimplex2F noise = new OpenSimplex2F(random.nextInt(8911805));
        Map<BlockPos, BlockState> blocks = new HashMap<>();
        int genHeight = getGenerationHeight(chunkX, chunkZ, chunkGenerator) - 5;

        Random random = new Random();

        double realX = chunkX * 16;
        double realZ = chunkZ * 16;
        BlockPos target = new BlockPos(realX + 10 - random.nextInt(20), genHeight + 155, realZ + 10 - random.nextInt(20));

        // Initial radius
        int radius = 32;
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                double distance = Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2));

                // Circle inclusion
                double realRadius = radius - noise.noise2((realX + x) / 25f, (realZ + z) / 25f) * 5;
                if(distance >= realRadius) {
                    continue;
                }

                // floor
                blocks.put(new BlockPos(realX + x, genHeight - 1, realZ + z), Blocks.OBSIDIAN.getDefaultState());

                // Draw line to top
                BlockPos from = new BlockPos(realX + x, genHeight, realZ + z);
                Vec3d change = new Vec3d(target.getX() - from.getX(), target.getY() - from.getY(), target.getZ() - from.getZ());
                Vec3d normalized = change.normalize();
                Vec3d real = new Vec3d(from.getX(), from.getY(), from.getZ());
                double d = Math.sqrt(Math.pow(change.x, 2) + Math.pow(change.y, 2) + Math.pow(change.z, 2));

                for (double i = 0; i <= d; i++) {
                    // walls and interior
                    if(distance >= realRadius * .8) {
                        if (noise.noise3_Classic(real.x / 20F, real.y / 20F, real.z / 20F) > 0.5) {
                            blocks.put(new BlockPos(real), Blocks.CRYING_OBSIDIAN.getDefaultState());
                        } else {
                            blocks.put(new BlockPos(real), Blocks.OBSIDIAN.getDefaultState());
                        }
                    } else {
                        blocks.put(new BlockPos(real), OmegaBlocks.THORN_AIR.getDefaultState());
                    }

                    real = real.add(normalized);
                }
            }
        }

        // Door
        BlockPos doorPos = new BlockPos(realX, genHeight, realZ + radius);
        BlockPos.iterateOutwards(doorPos, 5, 25, 5).forEach(pos -> {
            blocks.remove(pos);
        });

        // Sift structure
        sift(blocks);
        children.add(new SpikeStructureGenerator(random, (chunkX) * 16, (chunkZ) * 16));
    }

    public void sift(Map<BlockPos, BlockState> blocks) {
        Map<ChunkPos, SpikeStructureGenerator> pieces = new HashMap<>();

        blocks.forEach((pos, state) -> {
            ChunkPos key = new ChunkPos(pos);

            if (!pieces.containsKey(key)) {
                pieces.put(key, new SpikeStructureGenerator(random, key.x * 16, key.z * 16));
            }

            pieces.get(key).blocks.put(pos, state);
        });

        children.addAll(pieces.values());
        setBoundingBoxFromChildren();
    }
}
