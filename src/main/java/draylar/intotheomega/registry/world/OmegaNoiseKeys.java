package draylar.intotheomega.registry.world;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.mixin.BuiltinNoiseParametersAccessor;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;

public class OmegaNoiseKeys {

    public static final RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> OMEGA_SLIME_WASTE_RIVER = noise("omega_slime_waste_river");

    static {
        BuiltinNoiseParametersAccessor.callRegister(OMEGA_SLIME_WASTE_RIVER, -8, 1.0);
    }

    private static RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> noise(String id) {
        return RegistryKey.of(Registry.NOISE_WORLDGEN, IntoTheOmega.id(id));
    }

    public static void init() {

    }
}
