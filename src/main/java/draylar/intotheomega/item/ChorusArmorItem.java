package draylar.intotheomega.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ChorusArmorItem extends ArmorItem {

    public ChorusArmorItem(ArmorMaterial material, EquipmentSlot slot, Settings settings) {
        super(material, slot, settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);

        tooltip.add(new TranslatableText("intotheomega.chorus_armor.1").formatted(Formatting.GRAY));
        tooltip.add(new TranslatableText("intotheomega.chorus_armor.2").formatted(Formatting.GRAY));
        tooltip.add(new TranslatableText("intotheomega.chorus_armor.3").formatted(Formatting.GRAY));
    }
}
