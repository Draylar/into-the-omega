package draylar.intotheomega.item;

import dev.emi.trinkets.api.SlotGroups;
import dev.emi.trinkets.api.Slots;
import dev.emi.trinkets.api.TrinketItem;
import draylar.intotheomega.impl.DoubleJumpProvider;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import java.util.List;

public class VoidAirBottleItem extends TrinketItem implements DoubleJumpProvider {

    public VoidAirBottleItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean canWearInSlot(String s, String s1) {
        return s.equals(SlotGroups.LEGS) && s1.equals(Slots.BELT);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);

        tooltip.add(new TranslatableText("intotheomega.void_air_bottle.1").formatted(Formatting.GRAY));
        tooltip.add(new TranslatableText("intotheomega.void_air_bottle.2").formatted(Formatting.GRAY));
    }
}
