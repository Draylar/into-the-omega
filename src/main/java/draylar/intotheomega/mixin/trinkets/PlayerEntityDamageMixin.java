package draylar.intotheomega.mixin.trinkets;

import dev.emi.trinkets.api.TrinketsApi;
import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.api.event.PlayerAttackCallback;
import draylar.intotheomega.api.item.DamageHandler;
import draylar.intotheomega.api.item.TrinketEventHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Pair;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * This Mixin is responsible for modifying the damage a player deals to a target based on {@link TrinketEventHandler} listeners.
 */
@Mixin(PlayerEntity.class)
public abstract class PlayerEntityDamageMixin extends LivingEntity {

    @Shadow public abstract ItemStack getEquippedStack(EquipmentSlot slot);

    @Unique Entity ito_targetContext;
    @Unique float finalDamageCache;

    private PlayerEntityDamageMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

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
        finalDamageCache = PlayerAttackCallback.EVENT.invoker().attack(ito_targetContext, (PlayerEntity) (Object) this, original);
        return finalDamageCache;
    }

    @Inject(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"))
    private void triggerDamageHandlers(Entity target, CallbackInfo ci) {
        if(target instanceof LivingEntity livingTarget) {
            for (ItemStack stack : IntoTheOmega.collectListeningItems((PlayerEntity) (Object) this)) {
                if(stack.getItem() instanceof DamageHandler handler) {
                    handler.onDamageTarget(world, (PlayerEntity) (Object) this, stack, livingTarget, finalDamageCache);
                }
            }
        }
    }
}
