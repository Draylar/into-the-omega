package draylar.intotheomega.block;

import draylar.intotheomega.registry.OmegaParticles;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class StarlightBlock extends Block {

    public StarlightBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        super.randomDisplayTick(state, world, pos, random);
        world.addParticle(OmegaParticles.TINY_STAR, pos.getX() + world.random.nextDouble(1.0), pos.getY() - 0.1, pos.getZ() + world.random.nextDouble(1.0), 0, 0, 0);
    }
}
