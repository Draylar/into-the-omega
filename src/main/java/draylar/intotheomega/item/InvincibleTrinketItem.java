package draylar.intotheomega.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.Trinket;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.UUID;

public class InvincibleTrinketItem extends Item implements Trinket {

    private static final UUID MODIFIER_UUID = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");
    private static final Multimap<EntityAttribute, EntityAttributeModifier> MODIFIERS = ImmutableMultimap.<EntityAttribute, EntityAttributeModifier>builder()
            .put(EntityAttributes.GENERIC_ARMOR, new EntityAttributeModifier(MODIFIER_UUID, "infinite", Double.MAX_VALUE, EntityAttributeModifier.Operation.ADDITION))
            .put(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, new EntityAttributeModifier(MODIFIER_UUID, "infinite", Double.MAX_VALUE, EntityAttributeModifier.Operation.ADDITION))
            .build();

    public InvincibleTrinketItem(Settings settings) {
        super(settings);
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getModifiers(ItemStack stack, SlotReference slot, LivingEntity entity, UUID uuid) {
        return MODIFIERS;
    }

    @Override
    public void tick(ItemStack stack, SlotReference slot, LivingEntity player) {
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 20, 100, true, false));
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 20, 100, true, true));
    }
}
