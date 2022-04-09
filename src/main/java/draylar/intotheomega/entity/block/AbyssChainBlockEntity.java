package draylar.intotheomega.entity.block;

import draylar.intotheomega.registry.OmegaBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class AbyssChainBlockEntity extends BlockEntity {

    public AbyssChainBlockEntity(BlockPos pos, BlockState state) {
        super(OmegaBlockEntities.ABYSS_CHAIN, pos, state);
    }
}
