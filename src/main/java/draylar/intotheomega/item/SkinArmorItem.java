package draylar.intotheomega.item;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public abstract class SkinArmorItem extends ArmorItem {

    public SkinArmorItem(ArmorMaterial material, EquipmentSlot slot, Settings settings) {
        super(material, slot, settings);
    }

    public abstract Identifier getTexture(AbstractClientPlayerEntity entity, EquipmentSlot equipmentSlot, ItemStack head);
}
