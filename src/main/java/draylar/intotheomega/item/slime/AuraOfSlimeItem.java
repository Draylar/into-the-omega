package draylar.intotheomega.item.slime;

import dev.emi.trinkets.api.SlotGroups;
import dev.emi.trinkets.api.Slots;
import dev.emi.trinkets.api.TrinketItem;
import draylar.intotheomega.api.TrinketEventHandler;
import draylar.intotheomega.registry.OmegaStatusEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;

public class AuraOfSlimeItem extends TrinketItem implements TrinketEventHandler {

    public AuraOfSlimeItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean canWearInSlot(String group, String slot) {
        return group.equals(SlotGroups.HEAD) && slot.equals(Slots.MASK);
    }

    @Override
    public void onAttackEnemy(ItemStack stack, LivingEntity source, LivingEntity target) {
        target.addStatusEffect(new StatusEffectInstance(OmegaStatusEffects.SWIRLED, 15, 0, true, true));
    }

    @Override
    public double getDamageMultiplierAgainst(LivingEntity target) {
        if(target.hasStatusEffect(OmegaStatusEffects.SWIRLED)) {
            return 1.1;
        }

        return TrinketEventHandler.super.getDamageMultiplierAgainst(target);
    }

    @Override
    public double getCriticalChanceBonusAgainst(LivingEntity target) {
        if(target.hasStatusEffect(OmegaStatusEffects.SWIRLED)) {
            return 0.1;
        }

        return TrinketEventHandler.super.getDamageMultiplierAgainst(target);
    }
}
