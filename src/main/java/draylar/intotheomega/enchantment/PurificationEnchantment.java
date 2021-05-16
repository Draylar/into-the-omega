package draylar.intotheomega.enchantment;

import draylar.intotheomega.registry.OmegaEntityGroups;
import net.minecraft.enchantment.DamageEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;

public class PurificationEnchantment extends Enchantment {

    private static final int[] field_9063 = new int[]{1, 5, 5};
    private static final int[] field_9066 = new int[]{11, 8, 8};
    private static final int[] field_9064 = new int[]{20, 20, 20};

    public PurificationEnchantment() {
        super(Rarity.RARE, EnchantmentTarget.WEAPON, new EquipmentSlot[] { EquipmentSlot.MAINHAND });
    }

    @Override
    public boolean isTreasure() {
        return true;
    }

    @Override
    public int getMinPower(int level) {
        return 20;
    }

    @Override
    public int getMaxPower(int level) {
        return 50;
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public float getAttackDamage(int level, EntityGroup group) {
        if(group == OmegaEntityGroups.SLIME_CREATURE) {
            return level * 2.5F;
        }

        return super.getAttackDamage(level, group);
    }

    @Override
    public boolean canAccept(Enchantment other) {
        return !(other instanceof DamageEnchantment);
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return stack.getItem() instanceof AxeItem ? true : super.isAcceptableItem(stack);
    }
}
