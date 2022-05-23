package draylar.intotheomega.registry;

import draylar.intotheomega.IntoTheOmega;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.util.registry.Registry;

public class OmegaEntityAttributes {

    public static final EntityAttribute PHYSICAL_DAMAGE = register("generic.physical_damage", new ClampedEntityAttribute("omega.attribute.name.generic.physical_damage", 0.0, 0.0, 100.0).setTracked(true));
    public static final EntityAttribute RANGED_DAMAGE = register("generic.ranged_damage", new ClampedEntityAttribute("omega.attribute.name.generic.ranged_damage", 0.0, 0.0, 100.0).setTracked(true));
    public static final EntityAttribute MAGIC_DAMAGE = register("generic.magic_damage", new ClampedEntityAttribute("omega.attribute.name.generic.magic_damage", 0.0, 0.0, 100.0).setTracked(true));
    public static final EntityAttribute DAMAGE_REDUCTION = register("generic.damage_reduction", new ClampedEntityAttribute("omega.attribute.name.generic.damage_reduction", 0.0, 0.0, 100.0).setTracked(true));

    private static EntityAttribute register(String id, EntityAttribute attribute) {
        return Registry.register(Registry.ATTRIBUTE, IntoTheOmega.id(id), attribute);
    }

    public static void init() {

    }
}
