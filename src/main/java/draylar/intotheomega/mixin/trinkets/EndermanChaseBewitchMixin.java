package draylar.intotheomega.mixin.trinkets;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import draylar.intotheomega.api.BewitchedHelper;
import draylar.intotheomega.registry.OmegaItems;
import draylar.intotheomega.registry.OmegaParticles;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Pair;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Optional;

@Mixin(EndermanEntity.ChasePlayerGoal.class)
public class EndermanChaseBewitchMixin {

    @Shadow private LivingEntity target;
    @Shadow @Final private EndermanEntity enderman;

    @Inject(
            method = "canStart",
            at = @At(value = "RETURN"), cancellable = true)
    private void startAggression(CallbackInfoReturnable<Boolean> cir) {
        // Only run our logic if the Enderman would aggro on the player
        if(cir.getReturnValue()) {

            // If the Enderman is bewitched, it will not aggro from eye-contact.
            if(BewitchedHelper.isBewitched(enderman)) {
                cir.setReturnValue(false);
                return;
            }

            PlayerEntity player = (PlayerEntity) target;

            // If the player has a Bejeweled Mirror equipped in head:eyes, the Enderman will become bewitched.
            Optional<TrinketComponent> trinkets = TrinketsApi.getTrinketComponent(player);
            trinkets.ifPresent(component -> {
                List<Pair<SlotReference, ItemStack>> found = component.getEquipped(stack -> stack.getItem().equals(OmegaItems.BEJEWELED_MIRROR));
                if(!found.isEmpty()) {
                    BewitchedHelper.bewitch(enderman, player);
                    cir.setReturnValue(false);
                    ((ServerWorld) enderman.world).spawnParticles(OmegaParticles.SMALL_BLUE_OMEGA_BURST, enderman.getX(), enderman.getY() + 2, enderman.getZ(), 50, 0, 1, 0, 0);
                }
            });
        }
    }
}
