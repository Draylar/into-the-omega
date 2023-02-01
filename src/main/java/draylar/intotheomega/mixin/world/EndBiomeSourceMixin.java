package draylar.intotheomega.mixin.world;

import com.google.common.collect.ImmutableList;
import draylar.intotheomega.biome.EndBiomeSource;
import draylar.intotheomega.impl.BiomeRegistrySetter;
import draylar.intotheomega.registry.OmegaBiomes;
import net.minecraft.util.math.noise.SimplexNoiseSampler;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.biome.source.TheEndBiomeSource;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TheEndBiomeSource.class)
public class EndBiomeSourceMixin implements BiomeRegistrySetter {

    @Unique
    private static Registry<Biome> registry;

    @Redirect(method = "<init>(Lnet/minecraft/util/registry/Registry;J)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/registry/Registry;getOrCreateEntry(Lnet/minecraft/util/registry/RegistryKey;)Lnet/minecraft/util/registry/RegistryEntry;", ordinal = 0))
    private static RegistryEntry<Biome> test(Registry<Biome> instance, RegistryKey<Biome> tRegistryKey) {
        EndBiomeSourceMixin.registry = instance;
        return instance.getOrCreateEntry(BiomeKeys.THE_END);
    }

    @Redirect(method = "<init>(JLnet/minecraft/util/registry/RegistryEntry;Lnet/minecraft/util/registry/RegistryEntry;Lnet/minecraft/util/registry/RegistryEntry;Lnet/minecraft/util/registry/RegistryEntry;Lnet/minecraft/util/registry/RegistryEntry;)V", at = @At(value = "INVOKE", target = "Lcom/google/common/collect/ImmutableList;of(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Lcom/google/common/collect/ImmutableList;"))
    private static ImmutableList<RegistryEntry<Biome>> initializeCustomBiomeList(Object e1, Object e2, Object e3, Object e4, Object e5) {
        return ImmutableList.<RegistryEntry<Biome>>builder().addAll(OmegaBiomes.USED_BIOMES.stream().map(key -> registry.getOrCreateEntry(key)).toList()).build();
    }

    @Inject(method = "withSeed", at = @At("RETURN"))
    private void assignBiomeRegistry(long seed, CallbackInfoReturnable<BiomeSource> cir) {
        if(cir.getReturnValue() instanceof TheEndBiomeSource) {
            ((BiomeRegistrySetter) cir.getReturnValue()).setBiomeRegistry(registry);
        }
    }

    @Inject(method = "getBiome", at = @At("HEAD"), cancellable = true)
    private void ito$injectBiomes(int x, int y, int z, MultiNoiseUtil.MultiNoiseSampler noise, CallbackInfoReturnable<RegistryEntry<Biome>> cir) {
        cir.setReturnValue(EndBiomeSource.selectBiome(registry, x, y, z, noise));
    }

    @Overwrite
    public static float getNoiseAt(SimplexNoiseSampler simplexNoiseSampler, int i, int j) {
        return EndBiomeSource.getNoise(simplexNoiseSampler, i, j);
    }

    @Override
    public void setBiomeRegistry(Registry<Biome> registry) {
        EndBiomeSourceMixin.registry = registry;
    }
}
