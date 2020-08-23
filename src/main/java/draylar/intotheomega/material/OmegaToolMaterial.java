package draylar.intotheomega.material;

import draylar.intotheomega.registry.OmegaItems;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class OmegaToolMaterial implements ToolMaterial {

    public static final OmegaToolMaterial INSTANCE = new OmegaToolMaterial();

    @Override
    public int getDurability() {
        return 1250;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return 16;
    }

    @Override
    public float getAttackDamage() {
        return 4;
    }

    @Override
    public int getMiningLevel() {
        return 5;
    }

    @Override
    public int getEnchantability() {
        return 25;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(OmegaItems.OMEGA_CRYSTAL);
    }
}
