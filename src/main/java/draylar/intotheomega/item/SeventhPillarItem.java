package draylar.intotheomega.item;

import draylar.intotheomega.material.PillarToolMaterial;
import net.minecraft.item.SwordItem;

public class SeventhPillarItem extends SwordItem {

    public SeventhPillarItem(Settings settings, int attackDamage) {
        super(PillarToolMaterial.INSTANCE, attackDamage, -3.5f, settings);
    }
}
