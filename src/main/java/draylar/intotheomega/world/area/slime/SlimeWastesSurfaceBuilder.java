package draylar.intotheomega.world.area.slime;

import com.mojang.serialization.Codec;
import draylar.intotheomega.api.OpenSimplex2F;
import draylar.intotheomega.registry.OmegaBlocks;
import draylar.jvoronoi.JVoronoi;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

import java.util.Random;

public class SlimeWastesSurfaceBuilder extends SurfaceBuilder<TernarySurfaceConfig> {

    private OpenSimplex2F openSimplex;
    private JVoronoi voronoi;

    public SlimeWastesSurfaceBuilder(Codec<TernarySurfaceConfig> codec) {
        super(codec);
    }

    @Override
    public void initSeed(long seed) {
        super.initSeed(seed);
        openSimplex = new OpenSimplex2F(seed);
        voronoi = new JVoronoi(seed, 250);
    }

    @Override
    public void generate(Random random, Chunk chunk, Biome biome, int x, int z, int height, double noise, BlockState blockState, BlockState blockState2, int l, long m, TernarySurfaceConfig nil) {
        double distanceToEdge = Math.min(1, (voronoi.getDistanceToEdge(x / 4f, z / 4f) - 100) / 5); // [0, 1], where 0 is on edges

        // bottom & top endstone
        double baseNoise = openSimplex.noise2(x / 100f, z / 100f) * 3;
        double mountainNoise = openSimplex.noise3_Classic(x / 25f, 100, z / 25f) * 30 + openSimplex.noise3_Classic(x / 5f, 50, z / 5f) * 2;
        double topNoise = openSimplex.noise2((x + 1000) / 100f, (z + 1000) / 100f) * 3;

        if(baseNoise <= 0.7) {
            mountainNoise = 0;
        } else {
            mountainNoise = Math.max(0, mountainNoise);
        }

        for (int bottomY = 40; bottomY < (60 + baseNoise + mountainNoise) * distanceToEdge; bottomY++) {
            // 60 + baseNoise -> mountainNoise => mix
            if(bottomY >= 60 + baseNoise) {
                if(random.nextDouble() <= .4) {
                    chunk.setBlockState(new BlockPos(x, bottomY, z), Blocks.END_STONE.getDefaultState(), false);
                } else {
                    chunk.setBlockState(new BlockPos(x, bottomY, z), Blocks.END_STONE_BRICKS.getDefaultState(), false);
                }
            } else {
                // 40-60 + baseNoise = end stone
                chunk.setBlockState(new BlockPos(x, bottomY, z), Blocks.END_STONE.getDefaultState(), false);
            }
        }
        for (int bottomY = (int) (120 - topNoise); bottomY < (140 + baseNoise) * distanceToEdge; bottomY++)
            chunk.setBlockState(new BlockPos(x, bottomY, z), Blocks.END_STONE.getDefaultState(), false);

        // slime bottom & top
        double slimeRiverNoise = openSimplex.noise3_Classic(x / 50f, 1000, z / 50f) * distanceToEdge;
        double omegaSlimeRiverNoise = openSimplex.noise3_Classic(x / 25f, 2000, z / 25f) * distanceToEdge;

        // bottom green
        if (slimeRiverNoise < .1 && distanceToEdge == 1) {
            if(random.nextDouble() > .5) {
                chunk.setBlockState(new BlockPos(x, 60 + baseNoise, z), OmegaBlocks.CONGEALED_SLIME.getDefaultState(), false);
            } else {
                chunk.setBlockState(new BlockPos(x, 60 + baseNoise, z), Blocks.SLIME_BLOCK.getDefaultState(), false);
            }
        }

        // bottom purple
        if(omegaSlimeRiverNoise < .025 && distanceToEdge == 1) {
            chunk.setBlockState(new BlockPos(x, 60 + baseNoise, z), OmegaBlocks.CONGEALED_OMEGA_SLIME.getDefaultState(), false);
        }

        // top
        for (double i = 0; i < openSimplex.noise2(x / 100f, z / 100f) * 3 * distanceToEdge; i++) {
            chunk.setBlockState(new BlockPos(x, 120 - i, z), Blocks.SLIME_BLOCK.getDefaultState(), false);
        }
    }
}