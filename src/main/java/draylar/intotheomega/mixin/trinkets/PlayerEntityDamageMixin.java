package draylar.intotheomega.mixin.trinkets;

import draylar.intotheomega.api.event.PlayerAttackCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * This Mixin is responsible for modifying the damage a player deals to a target based on {@link draylar.intotheomega.api.TrinketEventHandler} listeners.
 */
@Mixin(PlayerEntity.class)
public class PlayerEntityDamageMixin {

    @Unique Entity ito_targetContext;

    @Inject(
            method = "attack",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getVelocity()Lnet/minecraft/util/math/Vec3d;"))
    private void storeDamageContext(Entity target, CallbackInfo ci) {
        ito_targetContext = target;
    }

    @ModifyVariable(
            method = "attack",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getVelocity()Lnet/minecraft/util/math/Vec3d;"),
            index = 2)
    private float modifyDamage(float original) {
        return PlayerAttackCallback.EVENT.invoker().attack(ito_targetContext, (PlayerEntity) (Object) this, original);
    }
}
