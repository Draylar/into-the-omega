package draylar.intotheomega.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import dev.emi.trinkets.api.SlotGroups;
import dev.emi.trinkets.api.Slots;
import dev.emi.trinkets.api.Trinket;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.UUID;

public class InvincibleTrinketItem extends Item implements Trinket {

    public static final UUID MODIFIER_UUID = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");

    public InvincibleTrinketItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean canWearInSlot(String s, String s1) {
        return s.equals(SlotGroups.LEGS) && s1.equals(Slots.BELT);
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getTrinketModifiers(String group, String slot, UUID uuid, ItemStack stack) {
        HashMultimap<EntityAttribute, EntityAttributeModifier> objs = HashMultimap.create();
        objs.put(EntityAttributes.GENERIC_ARMOR, new EntityAttributeModifier(MODIFIER_UUID, "infinite", Double.MAX_VALUE, EntityAttributeModifier.Operation.ADDITION));
        objs.put(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, new EntityAttributeModifier(MODIFIER_UUID, "infinite", Double.MAX_VALUE, EntityAttributeModifier.Operation.ADDITION));
        return objs;
    }
}
