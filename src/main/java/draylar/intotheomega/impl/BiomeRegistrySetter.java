package draylar.intotheomega.impl;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

public interface BiomeRegistrySetter {
    void setBiomeRegistry(Registry<Biome> registry);
}
