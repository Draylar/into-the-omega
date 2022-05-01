package draylar.intotheomega.mixin.dev;

import draylar.intotheomega.impl.ServerPlayerMirrorExtensions;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerMirrorMixin implements ServerPlayerMirrorExtensions {

    private BlockPos mirrorOrigin = null;

    @Unique
    @Override
    public void setMirrorOrigin(BlockPos pos) {
        mirrorOrigin = pos;
    }

    @Unique
    @Override
    public @Nullable BlockPos getOrigin() {
        return mirrorOrigin;
    }
}
