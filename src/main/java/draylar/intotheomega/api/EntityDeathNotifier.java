package draylar.intotheomega.api;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public interface EntityDeathNotifier {
    void setTarget(@NotNull RegistryKey<World> world, @NotNull BlockPos target);
}
