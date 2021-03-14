package draylar.intotheomega.world.api;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import draylar.intotheomega.api.BlockInfo;
import draylar.intotheomega.registry.OmegaStructurePieces;
import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructurePieceWithDimensions;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SiftingStructureGenerator extends StructurePieceWithDimensions {

    public final Map<BlockPos, BlockInfo> blocks = new HashMap<>();

    public SiftingStructureGenerator(Random random, int x, int z) {
        super(OmegaStructurePieces.SIFTING_PIECE, random, x, 30, z, 16, 48, 16);
    }

    public SiftingStructureGenerator(StructureManager manager, CompoundTag tag) {
        super(OmegaStructurePieces.SIFTING_PIECE, tag);

        blocks.clear();
        ListTag blocksTag = tag.getList("Blocks", NbtType.COMPOUND);
        blocksTag.forEach(t -> {
            CompoundTag element = (CompoundTag) t;
            BlockPos pos = BlockPos.fromLong(element.getLong("Pos"));
            DataResult<Pair<BlockState, Tag>> state1 = BlockState.CODEC.decode(NbtOps.INSTANCE, element.get("State"));

            if(state1.result().isPresent()) {
                blocks.put(pos, new BlockInfo(state1.result().get().getFirst(), null));
            }
        });
    }

    @Override
    public void toNbt(CompoundTag tag) {
        ListTag blocksTag = new ListTag();

        blocks.forEach((pos, info) -> {
            CompoundTag t = new CompoundTag();
            t.putLong("Pos", pos.asLong());
            DataResult<Tag> encode = BlockState.CODEC.encodeStart(NbtOps.INSTANCE, info.state);
            if (encode.error().isPresent()) {
                System.out.println("Failed to encode BlockState in SiftingStructure");
            } else {
                t.put("State", encode.result().get());
            }
        });

        // TODO: SERAILIZE TAG

        tag.put("Blocks", blocksTag);
    }

    @Override
    public boolean generate(StructureWorldAccess structureWorldAccess, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
        blocks.forEach((pos, info) -> {
            structureWorldAccess.setBlockState(pos, info.state, 3);
            BlockEntity entity = structureWorldAccess.getBlockEntity(pos);
            if(entity != null && info.tag != null) {
                entity.fromTag(info.state, info.tag);
            }
        });

        return true;
    }
}
