package draylar.intotheomega.mixin;

import draylar.intotheomega.api.world.StructureCache;
import draylar.intotheomega.impl.StructurePieceExtensions;
import draylar.intotheomega.impl.StructureStartExtensions;
import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

@Mixin(StructureStart.class)
public class StructureStartMixin implements StructureStartExtensions {

    @Unique private final StructureCache cache = new StructureCache();

    @Override
    @Unique
    public @Nullable StructureCache getPlacementCache() {
        return cache;
    }

    @Inject(method = "place", at = @At(value = "INVOKE", target = "Lnet/minecraft/structure/StructurePiece;generate(Lnet/minecraft/world/StructureWorldAccess;Lnet/minecraft/world/gen/StructureAccessor;Lnet/minecraft/world/gen/chunk/ChunkGenerator;Ljava/util/Random;Lnet/minecraft/util/math/BlockBox;Lnet/minecraft/util/math/ChunkPos;Lnet/minecraft/util/math/BlockPos;)V"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void setStructurePieceContext(StructureWorldAccess world, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox chunkBox, ChunkPos chunkPos, CallbackInfo ci, List list, BlockBox blockBox, BlockPos blockPos, BlockPos blockPos2, Iterator var11, StructurePiece structurePiece) {
        StructurePieceExtensions.get(structurePiece).setStructureStart((StructureStart) (Object) this);
    }
}
