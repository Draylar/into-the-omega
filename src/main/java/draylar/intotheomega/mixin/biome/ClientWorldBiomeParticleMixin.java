package draylar.intotheomega.mixin.biome;

import draylar.intotheomega.registry.OmegaBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Random;

@Mixin(ClientWorld.class)
public class ClientWorldBiomeParticleMixin {

    @Inject(
            method = "randomBlockDisplayTick",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isFullCube(Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;)Z"),
            cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    private void cancelThornAirParticles(
            int xCenter,
            int yCenter,
            int zCenter,
            int radius,
            Random random,
            boolean spawnBarrierParticles,
            BlockPos.Mutable pos,
            CallbackInfo ci,
            int i,
            int j,
            int k,
            BlockState blockState) {
        if(blockState.getBlock().equals(OmegaBlocks.THORN_AIR)) {
            ci.cancel();
        }
    }
}
