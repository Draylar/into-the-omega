package draylar.intotheomega.block;

import draylar.intotheomega.entity.block.PhasePadBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class PhasePadBlock extends BlockWithEntity {

    public PhasePadBlock(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PhasePadBlockEntity(pos, state);
    }
}
