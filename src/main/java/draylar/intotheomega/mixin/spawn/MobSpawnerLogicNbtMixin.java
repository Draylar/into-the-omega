package draylar.intotheomega.mixin.spawn;

import draylar.intotheomega.impl.MobSpawnerLogicExtensions;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.MobSpawnerLogic;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MobSpawnerLogic.class)
public class MobSpawnerLogicNbtMixin implements MobSpawnerLogicExtensions {

    @Unique
    private boolean skipRead = false;

    @Override
    public void setSkipRead() {
        skipRead = true;
    }

    @Inject(method = "readNbt", at = @At("HEAD"), cancellable = true)
    private void skipNbtRead(World world, BlockPos pos, NbtCompound nbt, CallbackInfo ci) {
        if(skipRead) {
            ci.cancel();
        }
    }
}
