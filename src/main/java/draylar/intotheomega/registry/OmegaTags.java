package draylar.intotheomega.registry;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.mixin.EntityTypeTagsAccessor;
import net.minecraft.entity.EntityType;
import net.minecraft.tag.Tag;

public class OmegaTags {

    public static final Tag.Identified<EntityType<?>> END_CREATURE = register("end_creature");
    public static final Tag.Identified<EntityType<?>> SLIME_CREATURE = register("slime_creature");

    private static Tag.Identified<EntityType<?>> register(String id) {
        return EntityTypeTagsAccessor.callRegister(IntoTheOmega.id(id).toString());
    }

    public static void init() {
        // NO-OP
    }

    private OmegaTags() {
        // NO-OP
    }
}
