package draylar.intotheomega.item.generic;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import java.util.List;
import java.util.UUID;

public class WardingOmegaItem extends TrinketItem {

    private static final ImmutableMultimap<EntityAttribute, EntityAttributeModifier> MODIFIERS = ImmutableMultimap.<EntityAttribute, EntityAttributeModifier>builder()
            .put(EntityAttributes.GENERIC_ARMOR, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Trinket modifier", 5, EntityAttributeModifier.Operation.ADDITION))
            .put(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Trinket modifier", 2, EntityAttributeModifier.Operation.ADDITION))
            .build();

    public WardingOmegaItem(Settings settings) {
        super(settings);
    }

    @Override
    public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 20 * 5, 0, false, false));
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);

        tooltip.add(new TranslatableText("intotheomega.warding_omega.1").formatted(Formatting.GRAY));
        tooltip.add(new TranslatableText("intotheomega.warding_omega.2").formatted(Formatting.GRAY));
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getModifiers(ItemStack stack, SlotReference slot, LivingEntity entity, UUID uuid) {
        return MODIFIERS;
    }
}
