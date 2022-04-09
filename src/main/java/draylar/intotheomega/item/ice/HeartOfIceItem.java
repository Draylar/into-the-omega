package draylar.intotheomega.item.ice;

import dev.emi.trinkets.api.TrinketItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HeartOfIceItem extends TrinketItem {

    public HeartOfIceItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(LiteralText.EMPTY);
        tooltip.add(new TranslatableText("intotheomega.heart_of_ice.1").formatted(Formatting.GRAY));
        tooltip.add(new LiteralText("   ").append(new TranslatableText("intotheomega.heart_of_ice.2")).formatted(Formatting.GRAY));
    }
}
