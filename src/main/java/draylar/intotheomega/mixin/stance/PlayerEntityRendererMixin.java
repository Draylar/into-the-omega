package draylar.intotheomega.mixin.stance;

import draylar.intotheomega.api.client.Stance;
import draylar.intotheomega.api.client.StanceProvider;
import draylar.intotheomega.api.client.Stances;
import draylar.intotheomega.api.event.PlayerStanceCallback;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityRenderer.class)
public class PlayerEntityRendererMixin {

    @Inject(
            method = "setModelPose",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/PlayerEntityRenderer;getArmPose(Lnet/minecraft/client/network/AbstractClientPlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/client/render/entity/model/BipedEntityModel$ArmPose;"))
    private void setupStance(AbstractClientPlayerEntity player, CallbackInfo ci) {
        Stances.set(player, getStance(player));
    }

    @Unique
    private static Stance getStance(AbstractClientPlayerEntity player) {
        return PlayerStanceCallback.EVENT.invoker().getStance(player, Stances.NONE);
    }
}
