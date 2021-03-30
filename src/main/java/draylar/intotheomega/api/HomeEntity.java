package draylar.intotheomega.api;

import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public interface HomeEntity {
    @Nullable
    BlockPos getHome();
    void setHome(BlockPos home);
    boolean hasHome();
    int getHomeRadius();
}
