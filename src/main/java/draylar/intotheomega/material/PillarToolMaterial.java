package draylar.intotheomega.material;

import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class PillarToolMaterial implements ToolMaterial {

    public static final PillarToolMaterial INSTANCE = new PillarToolMaterial();

    @Override
    public int getDurability() {
        return 1500;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return 6;
    }

    @Override
    public float getAttackDamage() {
        return 5;
    }

    @Override
    public int getMiningLevel() {
        return 3;
    }

    @Override
    public int getEnchantability() {
        return 5;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(Items.OBSIDIAN);
    }
}
