package draylar.intotheomega.item.matrix;

import dev.emi.trinkets.api.TrinketItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MatrixLensItem extends TrinketItem {

    public MatrixLensItem(Settings settings) {
        super(settings);
    }


    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);

        tooltip.add(new TranslatableText("intotheomega.explosion_boost", getExplosiveDamageBoost(stack) * 100).append(new LiteralText("%")).formatted(Formatting.BLUE));
        tooltip.add(new LiteralText(""));

        tooltip.add(new TranslatableText("intotheomega.matrix_lens.description_1").formatted(Formatting.GRAY));
        tooltip.add(new TranslatableText("intotheomega.matrix_lens.description_2").formatted(Formatting.GRAY));
    }

    public static float getExplosiveDamageBoost(ItemStack stack) {
        float base = .25f;
        int protection = EnchantmentHelper.getLevel(Enchantments.PROTECTION, stack);
        base += (protection * .05);
        return base;
    }
}
