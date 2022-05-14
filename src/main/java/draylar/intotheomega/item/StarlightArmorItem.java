package draylar.intotheomega.item;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextHandler;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class StarlightArmorItem extends ArmorItem {

    public StarlightArmorItem(ArmorMaterial material, EquipmentSlot slot, Settings settings) {
        super(material, slot, settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(new LiteralText(""));

        if(Screen.hasShiftDown()) {
            tooltip.add(new LiteralText("Set Bonus: §dStardrive").formatted(Formatting.GRAY).setStyle(Style.EMPTY.withBold(true)));
            tooltip.add(new LiteralText("Your Starlight attacks are infused with").formatted(Formatting.GRAY));
            tooltip.add(new LiteralText("additional light, granting you:").formatted(Formatting.GRAY));
            tooltip.add(new LiteralText(" - 15% attack bonus").formatted(Formatting.GRAY));
            tooltip.add(new LiteralText(" - 15% magic damage cooldown reduction").formatted(Formatting.GRAY));
            tooltip.add(new LiteralText(" - 15% attack speed increase").formatted(Formatting.GRAY));

            tooltip.add(LiteralText.EMPTY);
            tooltip.add(new LiteralText("Set Ability: §dFinal Starlight").formatted(Formatting.GRAY).setStyle(Style.EMPTY.withBold(true)));
            tooltip.add(new LiteralText("Press shift-click with a Starlight weapon to activate.").formatted(Formatting.GRAY));
            tooltip.add(new LiteralText("You are surrounded with Starlight, granting").formatted(Formatting.GRAY));
            tooltip.add(new LiteralText("double buff bonuses for 15s.").formatted(Formatting.GRAY));
        } else {
            tooltip.add(new LiteralText("Shift for Armor Set Ability").formatted(Formatting.GRAY));
        }
    }
}
