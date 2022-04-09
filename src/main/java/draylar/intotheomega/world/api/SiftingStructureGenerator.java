package draylar.intotheomega.world.api;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import draylar.intotheomega.api.BlockInfo;
import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

//public class SiftingStructureGenerator extends StructurePieceWithDimensions {
//
//    public final Map<BlockPos, BlockInfo> blocks = new HashMap<>();
//    private boolean placed = false;
//    private final ChunkPos pos;
//    private NbtCompound cachedTag = null;
//
//    public SiftingStructureGenerator(ChunkPos pos, StructurePieceType type, Random random, int x, int z) {
//        super(type, random, x, 30, z, 16, 48, 16);
//        this.pos = pos;
//    }
//
//    public SiftingStructureGenerator(StructurePieceType type, StructureManager manager, NbtCompound tag) {
//        super(type, tag);
//
//        blocks.clear();
//        ListTag blocksTag = tag.getList("Blocks", NbtType.COMPOUND);
//        blocksTag.forEach(t -> {
//            NbtCompound element = (NbtCompound) t;
//            BlockPos pos = BlockPos.fromLong(element.getLong("Pos"));
//            DataResult<Pair<BlockState, Tag>> state1 = BlockState.CODEC.decode(NbtOps.INSTANCE, element.get("State"));
//
//            if(state1.result().isPresent()) {
//                blocks.put(pos, new BlockInfo(state1.result().get().getFirst(), null));
//            }
//        });
//
//        cachedTag = tag;
//        pos = new ChunkPos(tag.getLong("Position"));
//        placed = tag.getBoolean("Placed");
//    }
//
//    /**
//     * {@link net.minecraft.structure.StructurePiece#toNbt(NbtCompound)} is called every time a chunk is saved.
//     * We only want to serialize our structure while it has not been generated yet.
//     *
//     * Once this structure has been placed, we set a flag, which will cause future NBT to not serialize.
//     * For performance reasons, we cache the serialized tag in this piece instance.
//     * This tag will never change after it is initially set, so it should be reliable.
//     */
//    @Override
//    public void toNbt(NbtCompound tag) {
//        // Only serialize this piece if we have not placed the structure yet.
//        if (!placed) {
//
//            // If we have serialized this structure already, return what we got before.
//            if(cachedTag != null) {
//                tag.put("Blocks", cachedTag.getList("Blocks", NbtType.COMPOUND));
//            } else {
//                ListTag blocksTag = new ListTag();
//
//                // only serialize non-placed chunks
//                blocks.forEach((pos, info) -> {
//                    NbtCompound t = new NbtCompound();
//                    t.putLong("Pos", pos.asLong());
//                    DataResult<Tag> encode = BlockState.CODEC.encodeStart(NbtOps.INSTANCE, info.state);
//                    if (encode.error().isPresent()) {
//                        System.out.println("Failed to encode BlockState in SiftingStructure");
//                    } else {
//                        t.put("State", encode.result().get());
//                    }
//                });
//
//                // TODO: SERAILIZE TAG
//                tag.put("Blocks", blocksTag);
//            }
//        }
//
//        tag.putBoolean("Placed", placed);
//        cachedTag = tag;
//    }
//
//    @Override
//    public boolean generate(StructureWorldAccess structureWorldAccess, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
//        if(!placed) {
//            blocks.forEach((pos, info) -> {
//                structureWorldAccess.setBlockState(pos, info.state, 3);
//                BlockEntity entity = structureWorldAccess.getBlockEntity(pos);
//                if (entity != null && info.tag != null) {
//                    entity.fromTag(info.state, info.tag);
//                }
//            });
//
//            placed = true;
//        }
//
//        return true;
//    }
//}
