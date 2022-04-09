package draylar.intotheomega.material;

import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class ShulkerToolMaterial implements ToolMaterial {

    public static final ShulkerToolMaterial INSTANCE = new ShulkerToolMaterial();

    @Override
    public int getDurability() {
        return 1250;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return 3;
    }

    @Override
    public float getAttackDamage() {
        return 0;
    }

    @Override
    public int getMiningLevel() {
        return 3;
    }

    @Override
    public int getEnchantability() {
        return 15;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(Items.SHULKER_SHELL);
    }
}
