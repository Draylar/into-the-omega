package draylar.intotheomega.api;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundTag;

public class BlockInfo {

    public BlockState state;
    public CompoundTag tag;

    public BlockInfo(BlockState state, CompoundTag tag) {
        this.state = state;
        this.tag = tag;
    }
}
