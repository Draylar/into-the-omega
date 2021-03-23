package draylar.intotheomega.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class VariantSparkItem extends Item {

    public VariantSparkItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if(user.isSneaking()) {
           if(!world.isClient) {
                int mode = stack.getOrCreateSubTag("Data").getInt("Mode");
                stack.getOrCreateSubTag("Data").putInt("Mode", mode == 0 ? 1 : 0);
           }

            return TypedActionResult.success(stack);
        }

        return TypedActionResult.fail(stack);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);

        int mode = stack.getOrCreateSubTag("Data").getInt("Mode");
        tooltip.add(new TranslatableText(String.format("item.intotheomega.variant_spark.%d", mode)).formatted(Formatting.GRAY));
    }

    @Override
    public Text getName(ItemStack stack) {
        int mode = stack.getOrCreateSubTag("Data").getInt("Mode");
        return new TranslatableText(this.getTranslationKey(stack)).formatted(mode == 0 ? Formatting.BLUE : Formatting.LIGHT_PURPLE);
    }
}
