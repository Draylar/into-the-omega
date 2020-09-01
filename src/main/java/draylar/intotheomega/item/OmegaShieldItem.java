package draylar.intotheomega.item;

import me.crimsondawn45.fabricshieldlib.object.AbstractShield;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import java.util.List;

public class OmegaShieldItem extends AbstractShield {

    public OmegaShieldItem(Settings settings, int cooldownTicks, int durability, Item repairItem) {
        super(settings, cooldownTicks, durability, repairItem);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);

        tooltip.add(new TranslatableText("intotheomega.omega_shield.1").formatted(Formatting.GRAY));
        tooltip.add(new TranslatableText("intotheomega.omega_shield.2").formatted(Formatting.GRAY));
    }
}
