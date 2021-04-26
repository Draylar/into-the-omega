package draylar.intotheomega.registry;

import draylar.intotheomega.IntoTheOmega;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.util.registry.Registry;

public class OmegaStatusEffects {

    public static final StatusEffect DUNGEON_LOCK = register("dungeon_lock", new StatusEffect(StatusEffectType.NEUTRAL, 0x7a7a7a) {});
    public static final StatusEffect ABYSSAL_FROSTBITE = register("abyssal_frostbite", (new StatusEffect(StatusEffectType.HARMFUL, 0x42f5ec) {})
            .addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, "03C3C89D-7037-4B42-869F-B146BCB64D2E", -0.25D, EntityAttributeModifier.Operation.MULTIPLY_TOTAL)
            .addAttributeModifier(EntityAttributes.GENERIC_ATTACK_SPEED, "03C3C89D-7037-4B42-869F-B146BCB64D2E", -0.25D, EntityAttributeModifier.Operation.MULTIPLY_TOTAL));

    private static StatusEffect register(String id, StatusEffect entry) {
        return  Registry.register(Registry.STATUS_EFFECT, IntoTheOmega.id(id), entry);
    }

    private OmegaStatusEffects() {
        // NO-OP
    }
}
