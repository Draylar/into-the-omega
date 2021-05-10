package draylar.intotheomega.item.slime;

import dev.emi.trinkets.api.SlotGroups;
import dev.emi.trinkets.api.Slots;
import dev.emi.trinkets.api.TrinketItem;
import draylar.intotheomega.api.TrinketEventHandler;
import draylar.intotheomega.registry.OmegaStatusEffects;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import java.util.List;

public class DestinySwirlItem extends TrinketItem implements TrinketEventHandler {

    public DestinySwirlItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if(player == null) {
            return;
        }

        tooltip.add(new LiteralText(""));
        tooltip.add(new TranslatableText("intotheomega.destiny_swirl.0").formatted(Formatting.GRAY));
        tooltip.add(new TranslatableText("intotheomega.destiny_swirl.1").formatted(Formatting.GRAY));
        tooltip.add(new LiteralText(""));
        tooltip.add(new TranslatableText("intotheomega.destiny_swirl.2").formatted(Formatting.GRAY));
        tooltip.add(new TranslatableText("intotheomega.destiny_swirl.3").formatted(Formatting.GRAY));
        tooltip.add(new TranslatableText("intotheomega.destiny_swirl.4").formatted(Formatting.GRAY));
    }

    @Override
    public boolean canWearInSlot(String group, String slot) {
        return group.equals(SlotGroups.CHEST) && slot.equals(Slots.CAPE);
    }

    @Override
    public void onAttackEnemy(ItemStack stack, LivingEntity source, LivingEntity target) {
        target.addStatusEffect(new StatusEffectInstance(OmegaStatusEffects.SWIRLED, 20 * 5, 0, false, true));
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
