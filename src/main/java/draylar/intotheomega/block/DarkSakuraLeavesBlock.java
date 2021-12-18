package draylar.intotheomega.block;

import draylar.intotheomega.registry.OmegaParticles;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class DarkSakuraLeavesBlock extends LeavesBlock {

    public DarkSakuraLeavesBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        super.randomDisplayTick(state, world, pos, random);

        if(random.nextDouble() <= 0.025 && world.getBlockState(pos.down()).isAir()) {
            world.addParticle(OmegaParticles.DARK_SAKURA_PETAL, pos.getX() + random.nextDouble(), pos.getY(), pos.getZ() + random.nextDouble(), 0, 0, 0);
        }
    }
}
