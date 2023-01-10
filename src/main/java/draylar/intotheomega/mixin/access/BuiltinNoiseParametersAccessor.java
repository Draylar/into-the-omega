package draylar.intotheomega.mixin.access;

import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.noise.BuiltinNoiseParameters;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BuiltinNoiseParameters.class)
public interface BuiltinNoiseParametersAccessor {

    @Invoker
    static void callRegister(RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> noise, int firstOctave, double firstAmplitude, double... amplitudes) {
        throw new UnsupportedOperationException();
    }
}
