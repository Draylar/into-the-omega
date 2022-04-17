package draylar.intotheomega.mixin.armor;

import draylar.intotheomega.item.generic.ChorusArmorItem;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ChorusFruitItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityEatMixin {

    @Shadow protected HungerManager hungerManager;

    @Shadow public abstract Iterable<ItemStack> getArmorItems();

    /**
     * If a player eats Chorus Fruit and has Chorus Armor equipped,
     * they gain double hunger and saturation from the fruit.
     * @param world  world this player is in
     * @param stack  the stack this player is eating
     * @param cir    mixin callback information
     */
    @Inject(
            method = "eatFood",
            at = @At("HEAD"))
    private void onEat(World world, ItemStack stack, CallbackInfoReturnable<ItemStack> cir) {
        if(stack.getItem() instanceof ChorusFruitItem) {
            for (ItemStack armorStack : getArmorItems()) {
                if (armorStack.getItem() instanceof ChorusArmorItem) {
                    hungerManager.eat(stack.getItem(), stack);
                    return;
                }
            }
        }
    }
}
