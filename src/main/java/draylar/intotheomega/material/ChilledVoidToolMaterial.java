package draylar.intotheomega.material;

import draylar.intotheomega.registry.OmegaItems;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class ChilledVoidToolMaterial implements ToolMaterial {

    public static final ChilledVoidToolMaterial INSTANCE = new ChilledVoidToolMaterial();

    @Override
    public int getDurability() {
        return 1500;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return 9;
    }

    @Override
    public float getAttackDamage() {
        return 5;
    }

    @Override
    public int getMiningLevel() {
        return 4;
    }

    @Override
    public int getEnchantability() {
        return 15;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(Items.PACKED_ICE);
    }
}
