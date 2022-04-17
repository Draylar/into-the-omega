package draylar.intotheomega.registry;

import draylar.intotheomega.IntoTheOmega;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.DamageModifierStatusEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.registry.Registry;

public class OmegaStatusEffects {

    public static final StatusEffect DUNGEON_LOCK = register("dungeon_lock", new StatusEffect(StatusEffectCategory.NEUTRAL, 0x7a7a7a) {});
    public static final StatusEffect ABYSSAL_FROSTBITE = register("abyssal_frostbite", (new StatusEffect(StatusEffectCategory.HARMFUL, 0x42f5ec) {})
            .addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, "03C3C89D-7037-4B42-869F-B146BCB64D2E", -0.25D, EntityAttributeModifier.Operation.MULTIPLY_TOTAL)
            .addAttributeModifier(EntityAttributes.GENERIC_ATTACK_SPEED, "03C3C89D-7037-4B42-869F-B146BCB64D2E", -0.25D, EntityAttributeModifier.Operation.MULTIPLY_TOTAL));
    public static final StatusEffect SWIRLED = register("swirled", new StatusEffect(StatusEffectCategory.HARMFUL, 0x1fa641) {});
    public static final StatusEffect PURE_STRENGTH = register(
            "pure_strength", (new DamageModifierStatusEffect(StatusEffectCategory.BENEFICIAL, 9643043, 3.0D) {})
                    .addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE, "648D7064-6A60-4F59-8ABE-C2C23A6DD7A9", 0.2D, EntityAttributeModifier.Operation.MULTIPLY_TOTAL));

    private static StatusEffect register(String id, StatusEffect entry) {
        return  Registry.register(Registry.STATUS_EFFECT, IntoTheOmega.id(id), entry);
    }

    public static void init() {
        // NO-OP
    }

    private OmegaStatusEffects() {
        // NO-OP
    }
}
