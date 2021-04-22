package draylar.intotheomega.item;

import dev.emi.trinkets.api.SlotGroups;
import dev.emi.trinkets.api.TrinketItem;
import draylar.intotheomega.IntoTheOmega;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BejeweledMirrorItem extends TrinketItem {

    public BejeweledMirrorItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(new TranslatableText("intotheomega.bejeweled_mirror.1").formatted(Formatting.GRAY));
        tooltip.add(new TranslatableText("intotheomega.bejeweled_mirror.2").formatted(Formatting.GRAY));
        tooltip.add(new TranslatableText("intotheomega.bejeweled_mirror.3").formatted(Formatting.GRAY));
    }

    @Override
    public boolean canWearInSlot(String s, String s1) {
        return s.equals(SlotGroups.HEAD) && s1.equals("eye");
    }
}
