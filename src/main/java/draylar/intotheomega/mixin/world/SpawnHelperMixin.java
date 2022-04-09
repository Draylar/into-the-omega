package draylar.intotheomega.mixin.world;

import net.minecraft.entity.SpawnGroup;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.collection.Pool;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SpawnHelper.class)
public class SpawnHelperMixin {

    @Inject(method = "getSpawnEntries", at = @At("HEAD"), cancellable = true)
    private static void injectStructureSpawns(ServerWorld world, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, SpawnGroup spawnGroup, BlockPos pos, @Nullable RegistryEntry<Biome> biomeEntry, CallbackInfoReturnable<Pool<SpawnSettings.SpawnEntry>> cir) {
//        if(spawnGroup.equals(SpawnGroup.MONSTER) && structureAccessor.getStructureAt(pos, OmegaWorld.ICE_ISLAND).hasChildren()) {
//            cir.setReturnValue(IceIslandStructure.MOB_SPAWNS);
//        }
    }
}
