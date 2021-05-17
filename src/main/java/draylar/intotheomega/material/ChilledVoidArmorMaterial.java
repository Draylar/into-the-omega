package draylar.intotheomega.material;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public class ChilledVoidArmorMaterial implements ArmorMaterial {

    private static final int[] BASE_DURABILITY = new int[] {13, 15, 16, 11};
    private static final int[] PROTECTION_AMOUNT = new int[] {2, 5, 7, 2};
    public static final ChilledVoidArmorMaterial INSTANCE = new ChilledVoidArmorMaterial();

    @Override
    public int getDurability(EquipmentSlot slot) {
        return BASE_DURABILITY[slot.getEntitySlotId()] * 10;
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
        return SoundEvents.BLOCK_SNOW_FALL;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(Items.PACKED_ICE);
    }

    @Override
    public String getName() {
        return "chilled_void";
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
