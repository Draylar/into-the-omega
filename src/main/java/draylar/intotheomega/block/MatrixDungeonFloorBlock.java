package draylar.intotheomega.block;

import draylar.intotheomega.entity.block.MatrixDungeonFloorBlockEntity;
import draylar.intotheomega.registry.OmegaBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
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

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, OmegaBlockEntities.MATRIX_DUNGEON_FLOOR, world.isClient ? MatrixDungeonFloorBlockEntity::tickClient : null);
    }
}
