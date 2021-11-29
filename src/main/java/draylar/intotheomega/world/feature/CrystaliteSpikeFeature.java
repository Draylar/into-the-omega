package draylar.intotheomega.world.feature;

import com.mojang.serialization.Codec;
import draylar.intotheomega.api.OpenSimplex2F;
import draylar.intotheomega.registry.OmegaBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

public class CrystaliteSpikeFeature extends Feature<DefaultFeatureConfig> {

    public CrystaliteSpikeFeature(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(StructureWorldAccess world, ChunkGenerator chunkGenerator, Random random, BlockPos pos, DefaultFeatureConfig config) {
        boolean large = world.getRandom().nextInt(5) == 0;
        int topMin = large ? 25 : 10;
        int topRand = large ? 35 : 20;
        int radiusMin = large ? 4 : 2;
        int radiusRand = large ? 6 : 3;

        // Locate good position to spawn at
        while(world.isAir(pos) && pos.getY() > 2) {
            pos = pos.down();
        }

        if(!world.getBlockState(pos.down()).getBlock().equals(Blocks.END_STONE)) {
            return false;
        }

        int top = topMin + world.getRandom().nextInt(topRand);
        int topX = world.getRandom().nextInt(top) - top / 2;
        int topZ = world.getRandom().nextInt(top) - top / 2;

        int radius = radiusMin + world.getRandom().nextInt(radiusRand);
        Vec3d to = new Vec3d(pos.getX() + topX, pos.getY() + top, pos.getZ() + topZ);

        for(int x = -radius; x <= radius; x++) {
            for(int z = -radius; z <= radius; z++) {
                double fromCenter = Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2));
                if(fromCenter <= radius) {
                    Vec3d from = new Vec3d(pos.getX() + x, pos.getY(), pos.getZ() + z);

                    // prevent spikes from generating over the void
                    if(world.getBlockState(new BlockPos(from).down()).isAir()) {
                        continue;
                    }

                    Vec3d per = to.subtract(from).normalize();
                    Vec3d current = from.add(0, 0, 0);
                    double distance = from.distanceTo(to);

                    for (double i = 0; i < distance; i++) {
                        BlockPos targetPos = new BlockPos(current);
                        world.setBlockState(targetPos, OmegaBlocks.CRYSTALITE.getDefaultState(), 3);
                        current = current.add(per);
                    }
                }
            }
        }

        return true;
    }
}
