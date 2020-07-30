package draylar.intotheomega.impl;

import net.minecraft.enchantment.Enchantment;

public interface OmegaManipulator {
    void setOmega(boolean v);
    void setVanilla(Enchantment e);
    boolean isOmega();
    Enchantment getVanilla();
}
