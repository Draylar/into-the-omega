package draylar.intotheomega.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import dev.emi.trinkets.api.SlotGroups;
import dev.emi.trinkets.api.Slots;
import dev.emi.trinkets.api.TrinketItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import java.util.List;
import java.util.UUID;

public class WardingOmegaItem extends TrinketItem {

    public WardingOmegaItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean canWearInSlot(String s, String s1) {
        return s.equals(SlotGroups.HAND) && s1.equals(Slots.RING);
    }

    @Override
    public void tick(PlayerEntity player, ItemStack stack) {
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 20 * 5, 0, false, false));
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);

        tooltip.add(new TranslatableText("intotheomega.warding_omega.1").formatted(Formatting.GRAY));
        tooltip.add(new TranslatableText("intotheomega.warding_omega.2").formatted(Formatting.GRAY));
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getTrinketModifiers(String group, String slot, UUID uuid, ItemStack stack) {
        if(group.equals(SlotGroups.HAND) && slot.equals(Slots.RING)) {
            return new ImmutableMultimap.Builder()
                    .put(EntityAttributes.GENERIC_ARMOR, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Trinket modifier", 5, EntityAttributeModifier.Operation.ADDITION))
                    .put(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Trinket modifier", 2, EntityAttributeModifier.Operation.ADDITION))
                    .build();
        }

        return null;
    }
}
