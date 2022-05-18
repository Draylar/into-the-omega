package draylar.intotheomega.api;

import draylar.intotheomega.mixin.BlockEntityTypeAccessor;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class BlockEntityExtensions {

    public static void extend(BlockEntityType<?> type, Block... blocks) {
        Set<Block> existing = new HashSet<>();
        existing.addAll(((BlockEntityTypeAccessor) type).getBlocks());
        existing.addAll(Arrays.asList(blocks));
        ((BlockEntityTypeAccessor) type).setBlocks(existing);
    }
}
