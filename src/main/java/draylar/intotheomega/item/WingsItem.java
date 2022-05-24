package draylar.intotheomega.item;

import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public abstract class WingsItem extends Item {

    public WingsItem(Settings settings) {
        super(settings.maxCount(1));
    }

    public abstract Identifier getTexture();
}
