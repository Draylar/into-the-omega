package draylar.intotheomega.api;

import net.minecraft.util.math.BlockPos;

public interface HomeEntity {
    BlockPos getHome();
    void setHome(BlockPos home);
    boolean hasHome();
    int getHomeRadius();
}
