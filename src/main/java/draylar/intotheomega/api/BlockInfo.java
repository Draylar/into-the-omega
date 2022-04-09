package draylar.intotheomega.api;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.NbtCompound;

public class BlockInfo {

    public BlockState state;
    public NbtCompound tag;

    public BlockInfo(BlockState state, NbtCompound tag) {
        this.state = state;
        this.tag = tag;
    }
}
