package draylar.intotheomega.mixin.armor;

import draylar.intotheomega.item.generic.ChorusArmorItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ChorusFruitItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChorusFruitItem.class)
public class ChorusFruitMixin {

    @Inject(
            method = "finishUsing",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/item/Item;finishUsing(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;)Lnet/minecraft/item/ItemStack;",
                    shift = At.Shift.AFTER),
            cancellable = true)
    private void onFinishUsing(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> cir) {
        for (ItemStack armorStack : user.getArmorItems()) {
            if (armorStack.getItem() instanceof ChorusArmorItem) {
                cir.setReturnValue(stack);
                return;
            }
        }
    }
}
