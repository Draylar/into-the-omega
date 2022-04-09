package draylar.intotheomega.mixin;

import draylar.intotheomega.registry.OmegaEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ExperienceOrbEntity.class)
public class ExperienceOrbEntityMixin {

    @Redirect(
            method = "repairPlayerGears",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;setDamage(I)V")
    )
    private void modifyMendingAmount(ItemStack stack, int damage) {
        if(EnchantmentHelper.getLevel(OmegaEnchantments.MENDING, stack) > 0) {
            stack.setDamage(Math.max(0, stack.getDamage() - damage * 2));
        }

        stack.setDamage(stack.getDamage() - damage);
    }
}
