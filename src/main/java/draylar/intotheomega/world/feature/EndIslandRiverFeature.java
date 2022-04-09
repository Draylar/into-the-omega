package draylar.intotheomega.world.feature;

import com.mojang.serialization.Codec;
import draylar.intotheomega.api.FastNoiseLite;
import draylar.jvoronoi.JVoronoi;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.Random;

public class EndIslandRiverFeature extends Feature<DefaultFeatureConfig> {

    // todo: seed
    private static final JVoronoi voronoi = new JVoronoi(0, 100);

    public EndIslandRiverFeature(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos pos = context.getOrigin();

        for(int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int trueZ = pos.getZ() + z;
                int trueX = pos.getX() + x;
                int top = world.getTopY(Heightmap.Type.WORLD_SURFACE, trueX, trueZ);
                BlockPos topPos = world.getTopPosition(Heightmap.Type.WORLD_SURFACE, new BlockPos(trueX, top, trueZ));

                if(world.getBlockState(topPos.down()).getBlock().equals(Blocks.END_STONE)) {
                    double noise = voronoi.getDistanceToEdge(trueX / 2f, trueZ / 2f);
                    if (noise <= 5) {
                        for (int i = top - 1; i > top - 5 * Math.abs(noise); i--) {
                            world.setBlockState(pos.add(x, i, z), Blocks.WATER.getDefaultState(), 3);
                        }
                    }
                }
            }
        }

        return true;
    }
}
