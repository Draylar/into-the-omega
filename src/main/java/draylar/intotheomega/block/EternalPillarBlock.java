package draylar.intotheomega.block;

import draylar.intotheomega.entity.block.EternalPillarBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

public class EternalPillarBlock extends Block implements BlockEntityProvider {

    public EternalPillarBlock(Settings settings) {
        super(settings);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockView world) {
        return new EternalPillarBlockEntity();
    }
}
