package draylar.intotheomega.impl;

import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;

public interface NoiseApply {
    void set(DoublePerlinNoiseSampler noise, double min, double max);
}
