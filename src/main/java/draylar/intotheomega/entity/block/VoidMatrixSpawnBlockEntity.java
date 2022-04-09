package draylar.intotheomega.entity.block;

import draylar.intotheomega.registry.OmegaBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class VoidMatrixSpawnBlockEntity extends BlockEntity {

    public VoidMatrixSpawnBlockEntity(BlockPos pos, BlockState state) {
        super(OmegaBlockEntities.VOID_MATRIX_SPAWN_BLOCK, pos, state);
    }
}
