package draylar.intotheomega.mixin.boss;

import draylar.intotheomega.registry.OmegaDamageSources;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class DamageVelocityMixin extends Entity {

    @Shadow public abstract void takeKnockback(double strength, double x, double z);
    @Unique private DamageSource context = null;

    public DamageVelocityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(
            method = "damage",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;takeKnockback(DDD)V"))
    private void storeContext(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        context = source;
    }

    @Redirect(
            method = "damage",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;takeKnockback(DDD)V"))
    private void cancelKnockback(LivingEntity instance, double strength, double x, double z) {
        if(context.getName().equals(OmegaDamageSources.MATRIX_LASER)) {
            return;
        }

        takeKnockback(strength, x, z);
    }
}
