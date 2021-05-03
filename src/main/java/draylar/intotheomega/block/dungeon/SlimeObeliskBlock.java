package draylar.intotheomega.block.dungeon;

import draylar.intotheomega.entity.dungeon.SlimeObeliskBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

public class SlimeObeliskBlock extends Block implements BlockEntityProvider {

    public SlimeObeliskBlock(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new SlimeObeliskBlockEntity();
    }
}
