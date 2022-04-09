package draylar.intotheomega.mixin.trinkets;

import dev.emi.trinkets.api.TrinketsApi;
import draylar.intotheomega.registry.OmegaItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.ProjectileDamageSource;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EndermanEntity.class)
public abstract class EndermanBoundEyeMixin extends MobEntity {

    private EndermanBoundEyeMixin(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(
            method = "damage",
            at = @At("HEAD"),
            cancellable = true)
    private void onProjectileHit(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (this.isInvulnerableTo(source)) {
            cir.setReturnValue(false);
        } else if (source instanceof ProjectileDamageSource) {
            // If any nearby player is wearing a Bound Eye, prevent teleportation.
            boolean isBound = world.getEntitiesByClass(
                    PlayerEntity.class,
                    new Box(getBlockPos().add(-256, -128, -256), getBlockPos().add(256, 128, 256)),
                    player -> TrinketsApi.getTrinketComponent(player).isPresent() && TrinketsApi.getTrinketComponent(player).get().isEquipped(OmegaItems.BOUND_EYE))
                    .stream().anyMatch(player -> player.canSee(this));

            if(isBound) {
                cir.setReturnValue(super.damage(source, amount));
            }
        }
    }
}
