package draylar.intotheomega.mixin.mechanic;

import draylar.intotheomega.client.PhasePadUtils;
import draylar.intotheomega.network.ClientNetworking;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(PlayerEntity.class)
public class PlayerEntityJumpMixin {

    @Inject(
            method = "jump",
            at = @At("HEAD"),
            cancellable = true)
    private void onJump(CallbackInfo ci) {
        if(PhasePadUtils.hasHighlight()) {
            ClientNetworking.requestPhasePadTeleport(PhasePadUtils.getHighlightPos());
            ci.cancel();
        }
    }
}
