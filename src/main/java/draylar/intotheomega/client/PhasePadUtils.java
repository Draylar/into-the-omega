package draylar.intotheomega.client;

import draylar.intotheomega.entity.block.PhasePadBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;

import java.util.ArrayList;
import java.util.List;

// client-
public class PhasePadUtils {

    private static final List<PhasePadBlockEntity> NEARBY = new ArrayList<>();
    private static World world;
    private static BlockPos phasePadBlock;
    private static BlockPos highlightPos;

    public static boolean hasHighlight() {
        return highlightPos != null;
    }

    public static BlockPos getHighlightPos() {
        return highlightPos;
    }

    public static void setHighlighting(BlockPos pos) {
        highlightPos = pos;
    }

    public static void stepOn(World world, BlockPos phasePadBlock) {
        reset();

        BlockEntity atPos = world.getBlockEntity(phasePadBlock);
        PhasePadBlockEntity originBE;

        if(atPos instanceof PhasePadBlockEntity) {
            originBE = (PhasePadBlockEntity) atPos;
        } else {
            return;
        }

        ChunkPos origin = new ChunkPos(phasePadBlock);
        notifyPhasePads(world.getChunk(origin.x, origin.z), originBE);

        // get 3x3 chunk
        for(int x = -1; x <= 1; x++) {
            for(int z = -1; z <= 1; z++) {
                ChunkPos offset = new ChunkPos(origin.x + x, origin.z + z);
                notifyPhasePads(world.getChunk(offset.x, offset.z), originBE);
            }
        }

        PhasePadUtils.world = world;
        PhasePadUtils.phasePadBlock = phasePadBlock;
    }

    public static void notifyPhasePads(WorldChunk chunk, PhasePadBlockEntity originBE) {
        chunk.getBlockEntities().forEach((pos, entity) -> {
            if(entity instanceof PhasePadBlockEntity && !entity.equals(originBE)) {
                PhasePadBlockEntity phasePad = (PhasePadBlockEntity) entity;
                NEARBY.add(phasePad);
                phasePad.activate();
            }
        });
    }

    public static void reset() {
        NEARBY.forEach(phasePadBlockEntity -> {
            phasePadBlockEntity.deactivate();
            phasePadBlockEntity.dehighlight();
        });

        PhasePadUtils.setHighlighting(null);
        NEARBY.clear();
        PhasePadUtils.world = null;
        PhasePadUtils.phasePadBlock = null;
    }
}
