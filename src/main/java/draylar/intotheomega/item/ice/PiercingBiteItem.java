package draylar.intotheomega.item.ice;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import draylar.attributed.CustomEntityAttributes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.ItemStack;

import java.util.UUID;

public class PiercingBiteItem extends TrinketItem {

    private static final UUID CRITICAL_DAMAGE_UUID = UUID.fromString("CB3F55D4-645C-4F38-A497-9C13A33DB5CF");
    private static final ImmutableMultimap<EntityAttribute, EntityAttributeModifier> MODIFIERS = ImmutableMultimap.<EntityAttribute, EntityAttributeModifier>builder()
            .put(CustomEntityAttributes.CRIT_DAMAGE, new EntityAttributeModifier(CRITICAL_DAMAGE_UUID, "Piercing Bite Critical Boost", .5, EntityAttributeModifier.Operation.ADDITION))
            .build();

    public PiercingBiteItem(Settings settings) {
        super(settings);
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getModifiers(ItemStack stack, SlotReference slot, LivingEntity entity, UUID uuid) {
        return MODIFIERS;
    }
}
