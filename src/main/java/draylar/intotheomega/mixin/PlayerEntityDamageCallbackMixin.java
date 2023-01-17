package draylar.intotheomega.mixin;

import draylar.intotheomega.api.event.PlayerDamageCallback;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.TypedActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class PlayerEntityDamageCallbackMixin {

    @Unique
    private float ito_dmg = 0;

    @Inject(
            method = "damage",
            at = @At("HEAD"),
            cancellable = true
    )
    private void store(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        TypedActionResult<Float> result = PlayerDamageCallback.EVENT.invoker().onDamage((PlayerEntity) (Object) this, source, amount);

        if(result.getResult().equals(ActionResult.FAIL) || result.getValue() <= 0.0) {
            cir.cancel();
        } else {
            ito_dmg = result.getValue();
        }
    }

    @ModifyVariable(
            method = "damage",
            at = @At("HEAD"),
            index = 2
    )
    private float onDamage(float original) {
        return ito_dmg;
    }
}
