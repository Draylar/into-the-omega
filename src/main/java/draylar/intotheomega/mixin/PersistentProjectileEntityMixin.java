package draylar.intotheomega.mixin;

import draylar.intotheomega.impl.ProjectileEntityManipulator;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.util.hit.EntityHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PersistentProjectileEntity.class)
public class PersistentProjectileEntityMixin implements ProjectileEntityManipulator {

    private boolean intotheomega_isOmegaFlame;

    @Inject(
            method = "onEntityHit",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;setOnFireFor(I)V", shift = At.Shift.AFTER)
    )
    private void applyOmegaFlame(EntityHitResult entityHitResult, CallbackInfo ci) {
        if (intotheomega_isOmegaFlame) {
            entityHitResult.getEntity().setOnFireFor(10);
        }
    }

    @Override
    public void setOmegaFlame(boolean b) {
        intotheomega_isOmegaFlame = b;
    }
}
