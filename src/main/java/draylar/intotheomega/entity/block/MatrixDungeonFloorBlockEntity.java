package draylar.intotheomega.entity.block;

import draylar.intotheomega.registry.OmegaBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class MatrixDungeonFloorBlockEntity extends BlockEntity {

    public MatrixDungeonFloorBlockEntity(BlockPos pos, BlockState state) {
        super(OmegaBlockEntities.MATRIX_DUNGEON_FLOOR, pos, state);
    }
}
