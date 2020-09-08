package draylar.intotheomega.impl;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface AttackingItem {
    void attack(PlayerEntity player, World world, ItemStack stack);
}
