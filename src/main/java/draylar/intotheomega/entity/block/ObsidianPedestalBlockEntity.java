package draylar.intotheomega.entity.block;

import draylar.intotheomega.registry.OmegaBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class ObsidianPedestalBlockEntity extends BlockEntity {

    public ObsidianPedestalBlockEntity(BlockPos pos, BlockState state) {
        super(OmegaBlockEntities.OBSIDIAN_PEDESTAL, pos, state);
    }
}
