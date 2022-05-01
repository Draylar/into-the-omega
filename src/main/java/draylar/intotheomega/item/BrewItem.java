package draylar.intotheomega.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class BrewItem extends Item {

    private final int cooldown;

    public BrewItem(Settings settings, int cooldownTicks) {
        super(settings);
        this.cooldown = cooldownTicks;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if(!world.isClient) {
            if(user instanceof ServerPlayerEntity player) {
                player.getItemCooldownManager().set(this, cooldown);
            }
        }

        return super.finishUsing(stack, world, user);
    }
}
