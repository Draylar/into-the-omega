package draylar.intotheomega.entity.dungeon;

import draylar.intotheomega.registry.OmegaBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class InvisibleDungeonBrickBlockEntity extends BlockEntity {

    public InvisibleDungeonBrickBlockEntity(BlockPos pos, BlockState state) {
        super(OmegaBlockEntities.INVISIBLE_DUNGEON_BRICK, pos, state);
    }
}
