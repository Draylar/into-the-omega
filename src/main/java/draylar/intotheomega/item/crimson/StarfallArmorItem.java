package draylar.intotheomega.item.crimson;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.item.SkinArmorItem;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class StarfallArmorItem extends SkinArmorItem {

    private static final Identifier TEXTURE = IntoTheOmega.id("textures/entity/starfall_skin.png");

    public StarfallArmorItem(ArmorMaterial material, EquipmentSlot slot, Settings settings) {
        super(material, slot, settings);
    }

    @Override
    public Identifier getTexture(AbstractClientPlayerEntity entity, EquipmentSlot equipmentSlot, ItemStack head) {
        return TEXTURE;
    }
}
