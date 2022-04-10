package draylar.intotheomega.impl;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public interface StructureStartExtensions {
    @Nullable Map<BlockPos, BlockState> getPlacementCache();
    void setPlacementCache(Map<BlockPos, BlockState> map);
}
