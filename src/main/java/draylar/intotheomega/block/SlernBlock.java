package draylar.intotheomega.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FernBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class SlernBlock extends FernBlock {

    private static final VoxelShape SHAPE = Block.createCuboidShape(0.0f, 0.0f, 0.0f, 16.0f, 32.0f, 16.0f);

    public SlernBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }
}
