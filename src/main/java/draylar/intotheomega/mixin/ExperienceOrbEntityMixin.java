package draylar.intotheomega.mixin;

import draylar.intotheomega.registry.OmegaEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Map;

@Mixin(ExperienceOrbEntity.class)
public class ExperienceOrbEntityMixin {

    private ItemStack intotheomega_contextStack;

    @Inject(
            method = "onPlayerCollision",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ExperienceOrbEntity;getMendingRepairCost(I)I", shift = At.Shift.AFTER),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void storeContext(PlayerEntity player, CallbackInfo ci, Map.Entry entry, ItemStack itemStack, int i) {
        intotheomega_contextStack = itemStack;
    }

    @ModifyVariable(
            method = "onPlayerCollision",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ExperienceOrbEntity;getMendingRepairCost(I)I", shift = At.Shift.AFTER),
            index = 4
    )
    private int modifyMendingAmount(int i) {
        if(EnchantmentHelper.getLevel(OmegaEnchantments.MENDING, intotheomega_contextStack) > 0) {
            return i * 2;
        }

        return i;
    }
}
