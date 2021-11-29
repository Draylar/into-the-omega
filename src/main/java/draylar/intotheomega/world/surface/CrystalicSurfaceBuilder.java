package draylar.intotheomega.world.surface;

import com.mojang.serialization.Codec;
import draylar.intotheomega.registry.OmegaBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

import java.util.Random;

public class CrystalicSurfaceBuilder extends SurfaceBuilder<TernarySurfaceConfig> {

    public CrystalicSurfaceBuilder(Codec<TernarySurfaceConfig> codec) {
        super(codec);
    }

    @Override
    public void generate(Random random, Chunk chunk, Biome biome, int x, int z, int height, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, TernarySurfaceConfig surfaceBlocks) {
        if(height != 0) {
            chunk.setBlockState(new BlockPos(x, height, z), Blocks.OBSIDIAN.getDefaultState(), false);
            if(noise >= 0.8) {
                chunk.setBlockState(new BlockPos(x, height, z), Blocks.END_STONE.getDefaultState(), false);
            }
        }
    }
}
