package draylar.intotheomega.impl;

import net.minecraft.item.ItemStack;

public interface ProjectileManipulator {
    void ito_setOrigin(ItemStack stack);
    ItemStack ito_getOrigin();
}
