package draylar.intotheomega.item;

import draylar.intotheomega.material.PillarToolMaterial;
import net.minecraft.item.SwordItem;

public class SeventhPillarItem extends SwordItem {

    public SeventhPillarItem(Settings settings) {
        super(PillarToolMaterial.INSTANCE, 14, -3.5f, settings);
    }
}
