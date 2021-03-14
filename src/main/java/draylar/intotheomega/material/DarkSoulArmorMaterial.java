package draylar.intotheomega.material;

import draylar.intotheomega.registry.OmegaItems;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public class DarkSoulArmorMaterial implements ArmorMaterial {

    private static final int[] BASE_DURABILITY = new int[] { 13, 15, 16, 11 };
    private static final int[] PROTECTION_AMOUNT = new int[] { 4, 7, 9, 4 };
    public static final DarkSoulArmorMaterial INSTANCE = new DarkSoulArmorMaterial();

    @Override
    public int getDurability(EquipmentSlot slot) {
        return BASE_DURABILITY[slot.getEntitySlotId()] * 30;
    }

    @Override
    public int getProtectionAmount(EquipmentSlot slot) {
        return PROTECTION_AMOUNT[slot.getEntitySlotId()];
    }

    @Override
    public int getEnchantability() {
        return 15;
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.BLOCK_BLASTFURNACE_FIRE_CRACKLE;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(OmegaItems.DARK_SOUL);
    }

    @Override
    public String getName() {
        return "dark_soul";
    }

    @Override
    public float getToughness() {
        return 2;
    }

    @Override
    public float getKnockbackResistance() {
        return 2;
    }
}
