package draylar.intotheomega.impl;

import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public interface ServerPlayerMirrorExtensions {
    void setMirrorOrigin(BlockPos pos);

    @Nullable
    BlockPos getOrigin();
}
