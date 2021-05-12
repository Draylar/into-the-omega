package draylar.intotheomega.world.area.slime;

import com.mojang.serialization.Codec;
import draylar.intotheomega.registry.OmegaBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.StructureFeature;

import java.util.Random;

public class SlimeDungeonFeature extends Feature<DefaultFeatureConfig> {

    public SlimeDungeonFeature(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(StructureWorldAccess world, ChunkGenerator chunkGenerator, Random random, BlockPos pos, DefaultFeatureConfig config) {
        // Find a position to generate our lake structure at.
        while (pos.getY() > 5 && world.isAir(pos)) {
            pos = pos.down();
        }

        // Only place the lake if the y-position is valid.
        if (pos.getY() <= 4) {
            return false;
        } else {
            pos = pos.down(5);

            // Do not overwrite Omega Slime Obelisk structures!
            if (world.getStructures(ChunkSectionPos.from(pos), StructureFeature.VILLAGE).findAny().isPresent()) {
                return false;
            } else {
                for (int x = -10; x <= 10; x++) {
                    for (int z = -10; z <= 10; z++) {
                        for (int y = 0; y < 10; y++) {
                            BlockPos at = pos.add(x, y, z);
                            double distance = Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2));
                            if(distance <= 10) {

                                if(!world.getBlockState(at).isAir()) {
                                    if (distance > 9) {
                                        world.setBlockState(at, Blocks.END_STONE_BRICKS.getDefaultState(), 3);
                                    } else {
                                        world.setBlockState(at, Blocks.AIR.getDefaultState(), 3);
                                    }
                                }
                            }
                        }

                        world.setBlockState(pos.add(x, 0, z), OmegaBlocks.CONGEALED_SLIME.getDefaultState(), 3);
                    }
                }

                world.setBlockState(pos.up(), Blocks.SPAWNER.getDefaultState(), 3);
            }
        }

        return true;
    }
}
