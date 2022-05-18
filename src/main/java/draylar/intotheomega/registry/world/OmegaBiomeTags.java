package draylar.intotheomega.registry.world;

import draylar.intotheomega.IntoTheOmega;
import net.minecraft.tag.TagKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

public class OmegaBiomeTags {

    public static final TagKey<Biome> EYE_ALTAR_HAS_STRUCTURE = key("has_structure/eye_altar");
    public static final TagKey<Biome> OMEGA_SLIME_WASTES = key("has_structure/omega_slime_spiral");
    public static final TagKey<Biome> END_ISLAND = key("is_biome/end_island");
    public static final TagKey<Biome> STARFALL_VALLEY = key("is_biome/starfall_valley");
    public static final TagKey<Biome> GENERIC_END_STRUCTURE = key("has_structure/generic_end_structure");

    private static TagKey<Biome> key(String id) {
        return TagKey.of(Registry.BIOME_KEY, IntoTheOmega.id(id));
    }

    public static void init() {

    }
}
