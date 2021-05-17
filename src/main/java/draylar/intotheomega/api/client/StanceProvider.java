package draylar.intotheomega.api.client;

import net.minecraft.item.ItemStack;

public interface StanceProvider {

    default Stance getUseStance(ItemStack stack) {
        return Stances.NONE;
    }
}
