package draylar.intotheomega.item;

import draylar.intotheomega.item.api.SetArmorItem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import java.util.List;

// The Chilled Void armor set provides 15% critical hit rate, 10% critical damage, and an additional 10% and 5% rate/damage against enemies inflicted with Abyssal Frostbite.
// Additionally, the user will be immune to slowing effects.
public class ChilledVoidArmorItem extends SetArmorItem {

    public ChilledVoidArmorItem(ArmorMaterial material, EquipmentSlot slot, Settings settings) {
        super(material, slot, settings, "chilled_void", 0);
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if(player == null) {
            return;
        }

        if(!hasFullSet(player)) {
            return;
        }

        tooltip.add(new LiteralText(" "));
        tooltip.add(new TranslatableText("intotheomega.set_bonus").formatted(Formatting.AQUA));
        tooltip.add(new LiteralText(" " ).append(new TranslatableText(String.format("intotheomega.%s.0", "chilled_void")).formatted(Formatting.GRAY)));
        tooltip.add(new LiteralText(" " ).append(new TranslatableText(String.format("intotheomega.%s.1", "chilled_void")).formatted(Formatting.GRAY)));
        tooltip.add(new LiteralText(" "));
        tooltip.add(new LiteralText(" " ).append(new TranslatableText(String.format("intotheomega.%s.2", "chilled_void")).formatted(Formatting.AQUA)));
        tooltip.add(new LiteralText(" " ).append(new TranslatableText(String.format("intotheomega.%s.3", "chilled_void")).formatted(Formatting.GRAY)));
        tooltip.add(new LiteralText(" " ).append(new TranslatableText(String.format("intotheomega.%s.4", "chilled_void")).formatted(Formatting.GRAY)));
    }
}
