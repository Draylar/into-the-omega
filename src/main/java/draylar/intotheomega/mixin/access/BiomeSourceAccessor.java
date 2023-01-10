package draylar.intotheomega.mixin.access;

import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

@Mixin(BiomeSource.class)
public interface BiomeSourceAccessor {

    @Mutable
    @Accessor
    void setBiomes(Set<RegistryEntry<Biome>> biomes);

    @Mutable
    @Accessor
    void setIndexedFeaturesSupplier(Supplier<List<BiomeSource.IndexedFeatures>> indexedFeaturesSupplier);

    @Invoker
    List<BiomeSource.IndexedFeatures> callMethod_39525(List<RegistryEntry<Biome>> biomes, boolean bl);
}
