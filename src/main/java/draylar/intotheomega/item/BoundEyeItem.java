package draylar.intotheomega.item;

import draylar.intotheomega.item.api.EyeTrinketItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BoundEyeItem extends EyeTrinketItem {

    public BoundEyeItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);

        tooltip.add(new TranslatableText("intotheomega.bound_eye.1").formatted(Formatting.GRAY));
        tooltip.add(new TranslatableText("intotheomega.bound_eye.2").formatted(Formatting.GRAY));
    }
}
