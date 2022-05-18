package draylar.intotheomega.mixin.world;

import draylar.intotheomega.world.structure.jigsaw.EndLabyrinthData;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePools;
import net.minecraft.util.registry.RegistryEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StructurePools.class)
public class StructurePoolsMixin {

    @Inject(method = "initDefaultPools", at = @At("RETURN"))
    private static void initializeOmegaPools(CallbackInfoReturnable<RegistryEntry<StructurePool>> cir) {
        EndLabyrinthData.init();
    }
}
