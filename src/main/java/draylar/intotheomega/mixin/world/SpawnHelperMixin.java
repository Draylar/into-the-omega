package draylar.intotheomega.mixin.world;

import draylar.intotheomega.registry.OmegaWorld;
import net.minecraft.block.Blocks;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.StructureFeature;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(SpawnHelper.class)
public class SpawnHelperMixin {

    @Inject(method = "method_29950", at = @At("HEAD"), cancellable = true)
    private static void injectStructureSpawns(ServerWorld serverWorld, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, SpawnGroup spawnGroup, BlockPos pos, Biome biome, CallbackInfoReturnable<List<SpawnSettings.SpawnEntry>> cir) {
        if(spawnGroup.equals(SpawnGroup.MONSTER) && structureAccessor.getStructureAt(pos, false, OmegaWorld.ICE_ISLAND).hasChildren()) {
            cir.setReturnValue(OmegaWorld.ICE_ISLAND.getMonsterSpawns());
        }
    }
}
