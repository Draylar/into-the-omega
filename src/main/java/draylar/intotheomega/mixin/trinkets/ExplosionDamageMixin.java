package draylar.intotheomega.mixin.trinkets;

import draylar.intotheomega.api.event.ExplosionDamageEntityCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.ActionResult;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Explosion.class)
public class ExplosionDamageMixin {

    @Redirect(
            method = "collectBlocksAndDamageEntities",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"))
    private boolean modifyDamage(Entity entity, DamageSource source, float amount) {
        TypedActionResult<Float> result = ExplosionDamageEntityCallback.EVENT.invoker().onDamage(entity, source, amount);

        if(!result.getResult().equals(ActionResult.FAIL)) {
            entity.damage(source, result.getValue());
        }

        return false;
    }
}
