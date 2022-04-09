package draylar.intotheomega.entity.block;

import draylar.intotheomega.registry.OmegaBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class GalaxyFurnaceBlockEntity extends BlockEntity {

    public GalaxyFurnaceBlockEntity(BlockPos pos, BlockState state) {
        super(OmegaBlockEntities.GALAXY_FURNACE, pos, state);
    }
}
