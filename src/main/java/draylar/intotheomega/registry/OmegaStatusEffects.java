package draylar.intotheomega.registry;

import draylar.intotheomega.IntoTheOmega;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.util.registry.Registry;

public class OmegaStatusEffects {

    public static final StatusEffect DUNGEON_LOCK = register("dungeon_lock", new StatusEffect(StatusEffectType.NEUTRAL, 0x7a7a7a) {});

    private static StatusEffect register(String id, StatusEffect entry) {
        return  Registry.register(Registry.STATUS_EFFECT, IntoTheOmega.id(id), entry);
    }

    private OmegaStatusEffects() {
        // NO-OP
    }
}
