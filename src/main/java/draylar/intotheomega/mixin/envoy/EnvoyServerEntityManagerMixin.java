package draylar.intotheomega.mixin.envoy;

import draylar.intotheomega.api.EntityTrackingHandler;
import net.minecraft.server.world.ServerEntityManager;
import net.minecraft.world.entity.EntityLike;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerEntityManager.class)
public class EnvoyServerEntityManagerMixin {

    @Inject(method = "addEntity(Lnet/minecraft/world/entity/EntityLike;Z)Z", at = @At(value = "RETURN", ordinal = 1))
    private void onAddEntity(EntityLike entity, boolean existing, CallbackInfoReturnable<Boolean> cir) {
        if(entity instanceof EntityTrackingHandler<?> handler) {
            handler.startTracking((ServerEntityManager) (Object) this);
        }
    }
}
