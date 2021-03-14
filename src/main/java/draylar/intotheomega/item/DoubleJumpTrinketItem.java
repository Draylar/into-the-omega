package draylar.intotheomega.item;

import dev.emi.trinkets.api.SlotGroups;
import dev.emi.trinkets.api.Slots;
import dev.emi.trinkets.api.TrinketItem;
import draylar.intotheomega.impl.DoubleJumpTrinket;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import java.util.List;

public class DoubleJumpTrinketItem extends TrinketItem implements DoubleJumpTrinket {

    private final int descriptions;

    public DoubleJumpTrinketItem(Settings settings, int descriptionLines) {
        super(settings);
        this.descriptions = descriptionLines;
    }

    @Override
    public boolean canWearInSlot(String group, String slot) {
        return group.equals(SlotGroups.LEGS) && slot.equals(Slots.BELT);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);

        for(int i = 0; i < descriptions; i++) {
            tooltip.add(new TranslatableText(String.format("%s.description.%d", getTranslationKey().replace("item.", ""), i + 1)).formatted(Formatting.GRAY));
        }
    }
}
