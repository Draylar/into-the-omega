package draylar.intotheomega.block;

import draylar.intotheomega.entity.block.MatrixDungeonFloorBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class MatrixDungeonFloorBlock extends BlockWithEntity {

    public MatrixDungeonFloorBlock(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new MatrixDungeonFloorBlockEntity(pos, state);
    }
}
