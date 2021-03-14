package draylar.intotheomega.impl;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface DoubleJumpTrinket {

    default int getDoubleJumps(ItemStack stack) {
        return 1;
    }

    default void onDoubleJump(World world, PlayerEntity player, ItemStack stack) {

    }
}
