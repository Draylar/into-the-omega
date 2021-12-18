package draylar.intotheomega.api.client;

import draylar.intotheomega.registry.OmegaBiomes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

import java.util.HashMap;
import java.util.Map;

public class EndSkyColor {

    public static final Vec3d DEFAULT = new Vec3d(40, 40, 40);
    public static final Map<RegistryKey<Biome>, Vec3d> BIOME_TO_COLOR = new HashMap<>();

    static {
        BIOME_TO_COLOR.put(OmegaBiomes.DARK_SAKURA_FOREST, new Vec3d(150, 0, 0));
    }

    public static Vec3d getColor(RegistryKey<Biome> biome) {
        return BIOME_TO_COLOR.getOrDefault(biome, DEFAULT);
    }
}
