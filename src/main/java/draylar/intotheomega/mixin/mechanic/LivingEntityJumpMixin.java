package draylar.intotheomega.mixin.mechanic;

import draylar.intotheomega.api.event.EntityJumpCallback;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.TypedActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(LivingEntity.class)
public class LivingEntityJumpMixin {

    @Unique
    private double modifiedJumpVelocity;

    @Inject(
            method = "jump",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;setVelocity(DDD)V"), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private void callJumpEvents(CallbackInfo ci, double velocity) {
        TypedActionResult<Double> result = EntityJumpCallback.EVENT.invoker().jump((LivingEntity) (Object) this, velocity);
        if(result.getResult() == ActionResult.FAIL) {
            ci.cancel();
        } else {
            modifiedJumpVelocity = result.getValue();
        }
    }

    @ModifyVariable(
            method = "jump",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;setVelocity(DDD)V"), index = 1)
    private double modifyJumpVelocity(double velocity) {
        return modifiedJumpVelocity;
    }
}
