package draylar.intotheomega.world.feature;

import com.mojang.serialization.Codec;
import draylar.intotheomega.api.OpenSimplex2F;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

public class ObsidianSpikeFeature extends Feature<DefaultFeatureConfig> {

    public ObsidianSpikeFeature(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(StructureWorldAccess world, ChunkGenerator chunkGenerator, Random random, BlockPos pos, DefaultFeatureConfig config) {
        OpenSimplex2F noise = new OpenSimplex2F(world.getRandom().nextInt());
        boolean cryingObsidian = world.getRandom().nextInt(3) == 0;

        boolean large = world.getRandom().nextInt(5) == 0;
        int topMin = large ? 25 : 10;
        int topRand = large ? 25 : 20;
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
                    Vec3d per = to.subtract(from).normalize();
                    Vec3d current = from.add(0, 0, 0);
                    double distance = from.distanceTo(to);

                    for (double i = 0; i < distance; i++) {
                        BlockPos targetPos = new BlockPos(current);
                        double v = 0;

                        if(cryingObsidian) {
                            v = noise.noise3_Classic(targetPos.getX() / 10f, targetPos.getY() / 10f, targetPos.getZ() / 10f);
                        }

                        if(v > .6) {
                            world.setBlockState(targetPos, Blocks.CRYING_OBSIDIAN.getDefaultState(), 3);
                        } else {
                            world.setBlockState(targetPos, Blocks.OBSIDIAN.getDefaultState(), 3);
                        }

                        current = current.add(per);
                    }
                }
            }
        }

        return true;
    }
}
