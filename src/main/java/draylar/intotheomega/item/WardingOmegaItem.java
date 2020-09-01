package draylar.intotheomega.item;

import dev.emi.trinkets.api.SlotGroups;
import dev.emi.trinkets.api.Slots;
import dev.emi.trinkets.api.TrinketItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import java.util.List;

public class WardingOmegaItem extends TrinketItem {

    public WardingOmegaItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean canWearInSlot(String s, String s1) {
        return s.equals(SlotGroups.HAND) && s1.equals(Slots.RING);
    }

    @Override
    public void tick(PlayerEntity player, ItemStack stack) {
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 20 * 5, 0));
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);

        tooltip.add(new TranslatableText("intotheomega.warding_omega.1").formatted(Formatting.GRAY));
        tooltip.add(new TranslatableText("intotheomega.warding_omega.2").formatted(Formatting.GRAY));
    }
}
