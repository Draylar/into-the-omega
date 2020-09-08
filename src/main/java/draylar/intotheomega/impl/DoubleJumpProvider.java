package draylar.intotheomega.impl;

import net.minecraft.item.ItemStack;

public interface DoubleJumpProvider {
    default int getDoubleJumps(ItemStack stack) {
        return 1;
    }
}
