package draylar.intotheomega.mixin;

import draylar.intotheomega.entity.OmegaSlimeMountEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMountMixin extends Entity {

    public LivingEntityMountMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(
            method = "tickMovement",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;isInLava()Z", ordinal = 1))
    private void jumpMount(CallbackInfo ci) {
        if(getVehicle() instanceof OmegaSlimeMountEntity) {
            ((OmegaSlimeMountEntity) getVehicle()).jump();
        }
    }
}
