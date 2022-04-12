package draylar.intotheomega.api.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public interface TrinketEventHandler {

    /**
     * Called when a {@link LivingEntity} wearing this Trinket attacks another {@link LivingEntity}.
     *
     * @param stack {@link ItemStack} with an Item implementing this interface used to attack the target
     * @param source source entity dealing damage while wearing this Trinket
     * @param target target being damaged
     */
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
