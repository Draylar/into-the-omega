package draylar.intotheomega.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import dev.emi.trinkets.api.SlotReference;
import draylar.attributed.CustomEntityAttributes;
import draylar.intotheomega.item.api.EyeTrinketItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;

import java.util.UUID;

public class DragonEyeItem extends EyeTrinketItem {

    private static final Multimap<EntityAttribute, EntityAttributeModifier> MODIFIERS = ImmutableMultimap.<EntityAttribute, EntityAttributeModifier>builder()
            .put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Tool modifier", 0.10D, EntityAttributeModifier.Operation.MULTIPLY_BASE))
            .put(CustomEntityAttributes.CRIT_CHANCE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Tool modifier", 0.10D, EntityAttributeModifier.Operation.MULTIPLY_BASE))
            .build();

    public DragonEyeItem(Settings settings) {
        super(settings);
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getModifiers(ItemStack stack, SlotReference slot, LivingEntity entity, UUID uuid) {
        return MODIFIERS;
    }
}
