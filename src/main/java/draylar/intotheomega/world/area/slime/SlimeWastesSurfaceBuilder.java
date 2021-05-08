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
        for (int bottomY = 40; bottomY < (60 + openSimplex.noise2(x / 100f, z / 100f) * 3) * distanceToEdge; bottomY++) chunk.setBlockState(new BlockPos(x, bottomY, z), Blocks.END_STONE.getDefaultState(), false);
        for (int bottomY = 120; bottomY < (140 + openSimplex.noise2(x / 100f, z / 100f) * 3) * distanceToEdge; bottomY++) chunk.setBlockState(new BlockPos(x, bottomY, z), Blocks.END_STONE.getDefaultState(), false);

        // slime bottom & top
        for(double i = 0; i < openSimplex.noise2(x / 100f, z / 100f) * 3 * distanceToEdge; i++) {
            if(openSimplex.noise3_Classic(x / 25f, 200, z / 25f) > 0) {
                chunk.setBlockState(new BlockPos(x, 59 + i, z), OmegaBlocks.OBSIDIAN_BOOKSHELF.getDefaultState(), false);
            } else {
                chunk.setBlockState(new BlockPos(x, 59 + i, z), Blocks.GLOWSTONE.getDefaultState(), false);
            }
        }

        for(double i = 0; i < openSimplex.noise2(x / 100f, z / 100f) * 3 * distanceToEdge; i++) {
            chunk.setBlockState(new BlockPos(x, 120 - i, z), Blocks.SLIME_BLOCK.getDefaultState(), false);
        }
    }
}