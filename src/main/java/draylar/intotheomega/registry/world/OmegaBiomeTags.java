package draylar.intotheomega.registry.world;

import draylar.intotheomega.IntoTheOmega;
import net.minecraft.tag.TagKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

public class OmegaBiomeTags {

    public static final TagKey<Biome> EYE_ALTAR_HAS_STRUCTURE = key("has_structure/eye_altar");

    private static TagKey<Biome> key(String id) {
        return TagKey.of(Registry.BIOME_KEY, IntoTheOmega.id(id));
    }

    public static void init() {

    }
}
