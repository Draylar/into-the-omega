package draylar.intotheomega.world.area.slime;

import com.mojang.serialization.Codec;
import draylar.intotheomega.api.OpenSimplex2F;
import draylar.intotheomega.registry.OmegaBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.Random;

public class SlimePillarFeature extends Feature<DefaultFeatureConfig> {

    public SlimePillarFeature(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }


    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        OpenSimplex2F noise = new OpenSimplex2F(world.getSeed());
        Random random = world.getRandom();
        BlockPos pos = context.getOrigin();

        // only 25% of pillars spawn
        if(random.nextInt(4) != 0) {
            return false;
        }

        BlockPos check = new BlockPos(pos.getX(), 30, pos.getZ());

        // Find first blocks
        while(world.getBlockState(check).isAir()) {
            check = check.up();

            // If the y-pos reaches 60, there is nothing
            if(check.getY() >= 60) {
                return false;
            }
        }

        // We now know something exists. Move up until we find air.
        while(!world.getBlockState(check).isAir()) {
            check = check.up();

            // fail-safe
            if(check.getY() >= 200) {
                return false;
            }
        }

        // TODO: what if there is a cave or ravine here?
        // We should now roughly be inside the Slime Area. Find the top position now.
        BlockPos bottom = check.mutableCopy().toImmutable();
        while(world.getBlockState(check).isAir()) {
            check = check.up();

            // fail-safe
            if(check.getY() >= 200) {
                return false;
            }
        }
        BlockPos top = check.mutableCopy().toImmutable();
        double size = 0.5 + random.nextDouble() * .75;
        double rand = random.nextDouble();

        // select a random block for the pillar
        BlockState block = Blocks.SLIME_BLOCK.getDefaultState();
        if(rand < .3) {
            block = OmegaBlocks.CONGEALED_OMEGA_SLIME.getDefaultState();
        } else if (rand < .6) {
            block = OmegaBlocks.CONGEALED_SLIME.getDefaultState();
        }

        // Place pillar
        for(int i = bottom.getY(); i < top.getY(); i++) {
            // circle
            int radius = (int) (Math.pow(i - 60 - 20, 2) / 240 * size) + 2;

            for(int x = -radius; x <= radius; x++) {
                for (int z = -radius; z <= radius; z++) {
                    int realX = check.getX() + x;
                    int realZ = check.getZ() + z;

                    double distance = Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2));
                    if(distance <= radius - noise.noise3_Classic(realX / 10f, i / 10f, realZ / 10f) * 2) {
                        world.setBlockState(new BlockPos(realX, i, realZ), block, 3);
                    }
                }
            }
        }

        return true;
    }
}
