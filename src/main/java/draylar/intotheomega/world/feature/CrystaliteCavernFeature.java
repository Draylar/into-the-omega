package draylar.intotheomega.world.feature;

import com.mojang.serialization.Codec;
import draylar.intotheomega.registry.OmegaBiomes;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

public class CrystaliteCavernFeature extends Feature<DefaultFeatureConfig> {

    public CrystaliteCavernFeature(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(StructureWorldAccess world, ChunkGenerator chunkGenerator, Random random, BlockPos pos, DefaultFeatureConfig config) {
        for (int x = -18; x < 18; x++) {
            for (int z = -18; z < 18; z++) {
                BlockPos m = new BlockPos(pos.getX() + x, 0, pos.getZ() + z);

                // Locate first Endstone from bottom
                int y = 0;
                while(world.getBlockState(m).isAir() && y <= 60) {
                    m = m.add(0, 1, 0);
                    y++;
                }

                if(!world.getBlockState(m).isAir()) {
                    // Go up until we find ceiling
                    m = m.add(0, 3, 0);
                    while(world.getBlockState(m.up(3)).getBlock().equals(Blocks.END_STONE)) {
                        boolean valid = true;
                        for(Direction direction : Direction.values()) {
                            Block block = world.getBlockState(m.offset(direction)).getBlock();
                            if(block.equals(Blocks.VOID_AIR) || block.equals(Blocks.AIR)) {
                                valid = false;
                                break;
                            }
                        }

                        if(valid) {
                            world.setBlockState(m, Blocks.CAVE_AIR.getDefaultState(), 3);
                        }

                        m = m.add(0, 1, 0);
                    }
                }
            }
        }

        return true;
    }
}
