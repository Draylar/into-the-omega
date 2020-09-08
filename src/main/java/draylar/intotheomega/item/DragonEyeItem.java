package draylar.intotheomega.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import dev.emi.trinkets.api.SlotGroups;
import dev.emi.trinkets.api.TrinketItem;
import dev.emi.trinkets.api.TrinketSlots;
import draylar.attributed.CustomEntityAttributes;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;

import java.util.UUID;

public class DragonEyeItem extends TrinketItem {

    private final Multimap<EntityAttribute, EntityAttributeModifier> ATTRIBUTE_MODIFIERS;

    public DragonEyeItem(Settings settings) {
        super(settings);

        // Setup attribute modifiers
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Tool modifier", 0.10D, EntityAttributeModifier.Operation.MULTIPLY_BASE));
        builder.put(CustomEntityAttributes.CRIT_CHANCE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Tool modifier", 0.10D, EntityAttributeModifier.Operation.MULTIPLY_BASE));
        this.ATTRIBUTE_MODIFIERS = builder.build();
    }

    @Override
    public boolean canWearInSlot(String group, String slot) {
        return group.equals(SlotGroups.HEAD) && slot.equals("eye");
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getTrinketModifiers(String group, String slot, UUID uuid, ItemStack stack) {
        if(group.equals(SlotGroups.HEAD) && slot.equals("eye")) {
            return ATTRIBUTE_MODIFIERS;
        }

        return null;
    }
}
