package draylar.intotheomega.block;

import draylar.intotheomega.entity.StatueBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class StatueBlock extends Block implements BlockEntityProvider {

    private static final VoxelShape SHAPE = Block.createCuboidShape(3, 0, 3, 13, 1, 13);

    public StatueBlock() {
        super(FabricBlockSettings.of(Material.METAL).nonOpaque());
    }

    public StatueBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new StatueBlockEntity();
    }
}
