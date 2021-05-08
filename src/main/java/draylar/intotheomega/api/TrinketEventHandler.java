package draylar.intotheomega.api;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public interface TrinketEventHandler {
    default void onAttackEnemy(ItemStack stack, LivingEntity source, LivingEntity target) {

    }

    default double getCriticalChanceBonusAgainst(LivingEntity target) {
        return 0.0;
    }

    default double getDamageBonusAgainst(LivingEntity target) {
        return 0.0;
    }

    default double getDamageMultiplierAgainst(LivingEntity target) {
        return 1.0;
    }
}
