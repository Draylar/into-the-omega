package draylar.intotheomega.mixin.biome.chorus;

import draylar.intotheomega.registry.OmegaTags;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChorusFlowerBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.ChorusPlantFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(ChorusPlantFeature.class)
public class ChorusPlantFeatureMixin {

    // This is probably better than an Overwrite or Redirect.
    @Inject(method = "generate", at = @At("HEAD"), cancellable = true)
    private void generateOnChorusGrass(StructureWorldAccess structureWorldAccess, ChunkGenerator chunkGenerator, Random random, BlockPos blockPos, DefaultFeatureConfig defaultFeatureConfig, CallbackInfoReturnable<Boolean> cir) {
        if (structureWorldAccess.isAir(blockPos) && OmegaTags.CHORUS_GROUND.contains(structureWorldAccess.getBlockState(blockPos.down()).getBlock())) {
            ChorusFlowerBlock.generate(structureWorldAccess, blockPos, random, 8);
            cir.setReturnValue(true);
        }
    }
}
