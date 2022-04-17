package draylar.intotheomega.item.enigma;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;

import java.util.UUID;

public class ObsidianCoreItem extends TrinketItem {

    private static final ImmutableMultimap<EntityAttribute, EntityAttributeModifier> MODIFIERS = ImmutableMultimap.<EntityAttribute, EntityAttributeModifier>builder()
            .put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Trinket boost", .2, EntityAttributeModifier.Operation.MULTIPLY_TOTAL))
            .put(EntityAttributes.GENERIC_MOVEMENT_SPEED, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Trinket boost", -.35, EntityAttributeModifier.Operation.MULTIPLY_TOTAL))
            .put(EntityAttributes.GENERIC_ARMOR, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Trinket boost", .1, EntityAttributeModifier.Operation.MULTIPLY_TOTAL))
            .build();

    public ObsidianCoreItem(Settings settings) {
        super(settings);
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getModifiers(ItemStack stack, SlotReference slot, LivingEntity entity, UUID uuid) {
        return MODIFIERS;
    }
}
