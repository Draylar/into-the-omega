package draylar.intotheomega.api.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

public interface CriticalItem {

    default double modifyCriticalDamage(PlayerEntity player, Entity target, ItemStack stack, Hand hand, double modifier) {
        return modifier;
    }

    default void afterCriticalHit(PlayerEntity player, Entity target, ItemStack stack, Hand hand) {

    }
}
