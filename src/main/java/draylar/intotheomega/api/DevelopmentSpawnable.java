package draylar.intotheomega.api;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;

public interface DevelopmentSpawnable {
    void spawn(StructureWorldAccess world, BlockPos pos);
}
