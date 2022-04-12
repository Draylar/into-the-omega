package draylar.intotheomega.api.world;

import net.minecraft.block.BlockState;
import net.minecraft.structure.StructurePiece;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.StructureWorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class StructureCache {

    private final Map<Integer, Entry> layers = new HashMap<>();

    public void placeOrCompute(int layer, StructureWorldAccess world, StructurePiece piece, BlockBox box, ChunkPos chunkPos, E e) {
        @Nullable Entry entry = layers.get(layer);
        if(entry == null) {
            entry = e.apply(new HashMap<>());
            layers.put(layer, entry);
        }

        entry.place(world, piece, chunkPos, box);
    }

    public interface E {
        Entry apply(HashMap<BlockPos, BlockState> states);
    }

    public static class Entry {

        protected final Map<BlockPos, BlockState> blocks;

        public Entry(Map<BlockPos, BlockState> blocks) {

            this.blocks = blocks;
        }

        public void place(StructureWorldAccess world, StructurePiece piece, ChunkPos chunkPos, BlockBox box) {
            blocks.forEach((cachePos, state) -> {
                piece.addBlock(world, state, cachePos.getX(), cachePos.getY(), cachePos.getZ(), box);
            });
        }
    }

    public static class ChunkSectionedEntry extends Entry {

        private final Map<ChunkPos, Map<BlockPos, BlockState>> blocksByChunk = new HashMap<>();

        public ChunkSectionedEntry(Map<BlockPos, BlockState> blocks) {
            super(Collections.emptyMap());

            blocks.forEach((pos, state) -> {
                ChunkPos chunkPos = new ChunkPos(pos);
                blocksByChunk.computeIfAbsent(chunkPos, (t) -> new HashMap<>());
                blocksByChunk.get(chunkPos).put(pos, state);
            });
        }

        public void place(StructureWorldAccess world, StructurePiece piece, ChunkPos chunkPos, BlockBox box) {
            @Nullable Map<BlockPos, BlockState> blocks = blocksByChunk.get(chunkPos);
            if(blocks != null) {
                blocks.forEach((pos, block) -> {
                    piece.addBlock(world, block, pos.getX(), pos.getY(), pos.getZ(), box);
                });
            }
        }
    }
}
