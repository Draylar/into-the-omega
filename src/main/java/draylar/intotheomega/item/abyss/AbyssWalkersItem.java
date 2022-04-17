package draylar.intotheomega.item.abyss;

import draylar.intotheomega.material.AbyssalArmorMaterial;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AbyssWalkersItem extends ArmorItem {

    public AbyssWalkersItem(Settings settings) {
        super(AbyssalArmorMaterial.INSTANCE, EquipmentSlot.FEET, settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(new TranslatableText("intotheomega.abyssal_boots.description_1").formatted(Formatting.GRAY));
        tooltip.add(new TranslatableText("intotheomega.abyssal_boots.description_2").formatted(Formatting.GRAY));
    }
}
