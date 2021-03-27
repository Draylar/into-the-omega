package draylar.intotheomega.block;

import draylar.intotheomega.entity.block.AbyssChainBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

public class AbyssChainBlock extends Block implements BlockEntityProvider {

    public AbyssChainBlock(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new AbyssChainBlockEntity();
    }
}
