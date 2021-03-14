package draylar.intotheomega.material;

import draylar.intotheomega.registry.OmegaItems;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class DragonslayerToolMaterial implements ToolMaterial {

    public static final DragonslayerToolMaterial INSTANCE = new DragonslayerToolMaterial();

    @Override
    public int getDurability() {
        return 1750;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return 9;
    }

    @Override
    public float getAttackDamage() {
        return 3;
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
        return Ingredient.ofItems(OmegaItems.DRAGON_SCALE);
    }
}
