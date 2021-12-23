package draylar.intotheomega.enchantment.special;

import draylar.intotheomega.enchantment.api.PrimalEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class GalaxyBlessingEnchantment extends Enchantment implements PrimalEnchantment {

    public GalaxyBlessingEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentTarget.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }

    @Override
    public boolean isTreasure() {
        return true;
    }

    @Override
    public Text getName(int level) {
        MutableText star = new LiteralText("⭐ ").formatted(Formatting.DARK_PURPLE);
        MutableText starEnd = new LiteralText(" ⭐").formatted(Formatting.GOLD);
        Text superName = super.getName(level);
        superName = superName instanceof MutableText ? ((MutableText) superName).formatted(Formatting.LIGHT_PURPLE) : superName;
        return star.append(superName).formatted(Formatting.GOLD).append(starEnd);
    }
}
