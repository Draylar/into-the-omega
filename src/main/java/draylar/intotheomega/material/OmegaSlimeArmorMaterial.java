package draylar.intotheomega.material;

import draylar.intotheomega.registry.OmegaItems;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public class OmegaSlimeArmorMaterial implements ArmorMaterial {

    private static final int[] BASE_DURABILITY = new int[] { 13, 15, 16, 11 };
    private static final int[] PROTECTION_AMOUNT = new int[] { 4, 6, 8, 4 };
    public static final OmegaSlimeArmorMaterial INSTANCE = new OmegaSlimeArmorMaterial();

    @Override
    public int getDurability(EquipmentSlot slot) {
        return BASE_DURABILITY[slot.getEntitySlotId()] * 35;
    }

    @Override
    public int getProtectionAmount(EquipmentSlot slot) {
        return PROTECTION_AMOUNT[slot.getEntitySlotId()];
    }

    @Override
    public int getEnchantability() {
        return 10;
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ENTITY_SLIME_JUMP;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(OmegaItems.OMEGA_SLIME);
    }

    @Override
    public String getName() {
        return "omega_slime";
    }

    @Override
    public float getToughness() {
        return 0;
    }

    @Override
    public float getKnockbackResistance() {
        return 0;
    }
}
