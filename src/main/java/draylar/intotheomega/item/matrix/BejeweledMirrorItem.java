package draylar.intotheomega.item.matrix;

import dev.emi.trinkets.api.TrinketItem;
import net.minecraft.client.item.TooltipContext;
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
}
