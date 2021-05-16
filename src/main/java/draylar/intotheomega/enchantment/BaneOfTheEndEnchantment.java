package draylar.intotheomega.enchantment;

import draylar.intotheomega.registry.OmegaEntityGroups;
import net.minecraft.enchantment.DamageEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EquipmentSlot;

public class BaneOfTheEndEnchantment extends Enchantment {

    public BaneOfTheEndEnchantment() {
        super(Rarity.RARE, EnchantmentTarget.WEAPON, new EquipmentSlot[] { EquipmentSlot.MAINHAND });
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public float getAttackDamage(int level, EntityGroup group) {
        if(group == OmegaEntityGroups.END_CREATURE) {
            return level * 2.5F;
        }

        return super.getAttackDamage(level, group);
    }

    @Override
    public boolean canAccept(Enchantment other) {
        return !(other instanceof DamageEnchantment);
    }
}
