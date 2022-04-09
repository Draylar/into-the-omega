package draylar.intotheomega.block;

import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

public class EndMushroomBlock extends PlantBlock {

    public EndMushroomBlock(Settings settings) {
        super(settings);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        Block underBlock = world.getBlockState(pos.down()).getBlock();
        // TODO: to tag
        return underBlock.equals(Blocks.OBSIDIAN) || underBlock.equals(Blocks.CRYING_OBSIDIAN) || underBlock.equals(Blocks.END_STONE);
    }
}
