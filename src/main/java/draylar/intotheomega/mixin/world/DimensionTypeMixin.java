package draylar.intotheomega.mixin.world;

import com.mojang.serialization.Lifecycle;
import draylar.intotheomega.biome.OmegaChunkGenerator;
import net.minecraft.structure.StructureSet;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.util.registry.*;
import net.minecraft.world.biome.source.TheEndBiomeSource;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

import static net.minecraft.world.dimension.DimensionType.THE_END_REGISTRY_KEY;

@Mixin(DimensionType.class)
public class DimensionTypeMixin {

    @Unique private static DynamicRegistryManager registryManager;
    @Unique private static long seed;

    @Inject(method = "createDefaultDimensionOptions(Lnet/minecraft/util/registry/DynamicRegistryManager;JZ)Lnet/minecraft/util/registry/Registry;", at = @At("HEAD"))
    private static void ito$storeContext(DynamicRegistryManager registryManager, long seed, boolean bl, CallbackInfoReturnable<Registry<DimensionOptions>> cir) {
        DimensionTypeMixin.registryManager = registryManager;
        DimensionTypeMixin.seed = seed;
    }

    @Redirect(method = "createDefaultDimensionOptions(Lnet/minecraft/util/registry/DynamicRegistryManager;JZ)Lnet/minecraft/util/registry/Registry;", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/registry/MutableRegistry;add(Lnet/minecraft/util/registry/RegistryKey;Ljava/lang/Object;Lcom/mojang/serialization/Lifecycle;)Lnet/minecraft/util/registry/RegistryEntry;", ordinal = 1))
    private static RegistryEntry<DimensionOptions> ito$redirectEndChunkGenerator(MutableRegistry<DimensionOptions> registry, RegistryKey<Object> tRegistryKey, Object t, Lifecycle lifecycle) {
        Registry<StructureSet> structureSetRegistry = registryManager.get(Registry.STRUCTURE_SET_KEY);
        Registry<DimensionType> dimensionTypeRegistry = registryManager.get(Registry.DIMENSION_TYPE_KEY);

        return registry.add(
                DimensionOptions.END,
                new DimensionOptions(
                        dimensionTypeRegistry.getOrCreateEntry(THE_END_REGISTRY_KEY),
                        new OmegaChunkGenerator(
                                structureSetRegistry,
                                Optional.empty(),
                                new TheEndBiomeSource(
                                        registryManager.get(Registry.BIOME_KEY),
                                        seed
                                )
                        ))
                , Lifecycle.stable());
    }
}
