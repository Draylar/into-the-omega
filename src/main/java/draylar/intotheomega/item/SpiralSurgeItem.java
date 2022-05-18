package draylar.intotheomega.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import draylar.intotheomega.api.item.AttackHandler;
import draylar.intotheomega.api.item.CriticalItem;
import draylar.intotheomega.registry.OmegaStatusEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

public class SpiralSurgeItem extends Item implements AttackHandler, CriticalItem {

    private static final ImmutableMultimap<EntityAttribute, EntityAttributeModifier> ATTRIBUTES =
            ImmutableMultimap.<EntityAttribute, EntityAttributeModifier>builder()
                    .put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(Item.ATTACK_DAMAGE_MODIFIER_ID, "damage", 19, EntityAttributeModifier.Operation.ADDITION))
                    .put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(Item.ATTACK_DAMAGE_MODIFIER_ID, "damage", -3.0f, EntityAttributeModifier.Operation.ADDITION))
                    .build();

    public SpiralSurgeItem(Settings settings) {
        super(settings);
    }

    @Override
    public void onAttack(World world, PlayerEntity holder, ItemStack stack, LivingEntity target) {
        if(!world.isClient && !holder.getItemCooldownManager().isCoolingDown(this)) {
            for (PlayerEntity nearby : world.getPlayers(TargetPredicate.createNonAttackable(), null, new Box(holder.getBlockPos()).expand(15))) {
                StatusEffectInstance existingSurgeEffect = nearby.getStatusEffect(OmegaStatusEffects.VIOLET_SURGE);
                if(existingSurgeEffect != null) {
                    nearby.addStatusEffect(new StatusEffectInstance(OmegaStatusEffects.VIOLET_SURGE, 10 * 20, Math.min(4, existingSurgeEffect.getAmplifier() + 1), false, false, true));
                } else {
                    nearby.addStatusEffect(new StatusEffectInstance(OmegaStatusEffects.VIOLET_SURGE, 10 * 20, 0, false, false, true));
                }

                holder.getItemCooldownManager().set(this, 20);
            }
        }
    }

    @Override
    public double modifyCriticalDamage(PlayerEntity player, Entity target, ItemStack stack, Hand mainHand, double modifier) {
        if(mainHand == Hand.MAIN_HAND) {
            StatusEffectInstance surgeEffect = player.getStatusEffect(OmegaStatusEffects.VIOLET_SURGE);
            if(surgeEffect != null) {
                // 10% boost for each level
                return (((surgeEffect.getAmplifier() + 1) * .1) + 1) * modifier;
            }
        }

        return CriticalItem.super.modifyCriticalDamage(player, target, stack, mainHand, modifier);
    }

    @Override
    public void afterCriticalHit(PlayerEntity player, Entity target, ItemStack stack, Hand hand) {
        if(hand == Hand.MAIN_HAND) {
            player.removeStatusEffect(OmegaStatusEffects.VIOLET_SURGE);
        }
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        if (slot == EquipmentSlot.MAINHAND) {
            return ATTRIBUTES;
        }

        return super.getAttributeModifiers(slot);
    }
}
