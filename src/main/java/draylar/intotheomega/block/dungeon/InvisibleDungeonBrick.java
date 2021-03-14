package draylar.intotheomega.block.dungeon;

import draylar.intotheomega.entity.dungeon.InvisibleDungeonBrickBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

public class InvisibleDungeonBrick extends Block implements BlockEntityProvider {

    public InvisibleDungeonBrick(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new InvisibleDungeonBrickBlockEntity();
    }

    @Override
    public boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {
        return false;
    }
}
