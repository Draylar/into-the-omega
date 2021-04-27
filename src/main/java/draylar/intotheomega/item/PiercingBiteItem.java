package draylar.intotheomega.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import dev.emi.trinkets.api.SlotGroups;
import dev.emi.trinkets.api.TrinketItem;
import draylar.attributed.CustomEntityAttributes;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.ItemStack;

import java.util.UUID;

public class PiercingBiteItem extends TrinketItem {

    private static final UUID CRITICAL_DAMAGE_UUID = UUID.fromString("CB3F55D4-645C-4F38-A497-9C13A33DB5CF");

    public PiercingBiteItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean canWearInSlot(String group, String slot) {
        return group.equals(SlotGroups.CHEST) && slot.equals("necklace");
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getTrinketModifiers(String group, String slot, UUID uuid, ItemStack stack) {
        if(group.equals(SlotGroups.CHEST) && slot.equals("necklace")) {
            ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> map = ImmutableMultimap.builder();
            map.put(CustomEntityAttributes.CRIT_DAMAGE, new EntityAttributeModifier(CRITICAL_DAMAGE_UUID, "Piercing Bite Critical Boost", .5, EntityAttributeModifier.Operation.ADDITION));
            return map.build();
        }

        return super.getTrinketModifiers(group, slot, uuid, stack);
    }
}
