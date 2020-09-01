package draylar.intotheomega.world.altar;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.registry.OmegaWorld;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.structure.*;
import net.minecraft.structure.processor.BlockIgnoreStructureProcessor;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;

import java.util.List;
import java.util.Random;

public class TrueEyeAltarGenerator {

    private static final Identifier TEMPLATE = IntoTheOmega.id("true_eye_pedestal");

    public static void addPieces(StructureManager manager, BlockPos pos, BlockRotation rotation, List<StructurePiece> pieces, Random random) {
        pieces.add(new Piece(manager, TEMPLATE, pos));
    }

    public static class Piece extends SimpleStructurePiece {

        private final Identifier template;

        public Piece(StructureManager manager, Identifier template, BlockPos pos) {
            super(OmegaWorld.TRUE_EYE_ALTAR_PIECE, 0);
            this.template = template;
            this.pos = pos;

            initializeStructureData(manager);
        }

        public Piece(StructureManager manager, CompoundTag tag) {
            super(OmegaWorld.TRUE_EYE_ALTAR_PIECE, tag);
            template = new Identifier(tag.getString("Template"));
            initializeStructureData(manager);
        }

        private void initializeStructureData(StructureManager manager) {
            Structure structure = manager.getStructureOrBlank(this.template);
            StructurePlacementData structurePlacementData = new StructurePlacementData().setRotation(BlockRotation.NONE).setMirror(BlockMirror.NONE).addProcessor(BlockIgnoreStructureProcessor.IGNORE_STRUCTURE_BLOCKS);
            this.setStructureData(structure, this.pos, structurePlacementData);
        }

        @Override
        protected void toNbt(CompoundTag tag) {
            super.toNbt(tag);
            tag.putString("Template", template.toString());
        }

        @Override
        protected void handleMetadata(String metadata, BlockPos pos, ServerWorldAccess serverWorldAccess, Random random, BlockBox boundingBox) {

        }

        @Override
        public boolean generate(StructureWorldAccess structureWorldAccess, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            BlockPos topPos = structureWorldAccess.getTopPosition(Heightmap.Type.WORLD_SURFACE, blockPos);

            if(topPos.getY() >= 50 && topPos.getY() <= 200) {
                return super.generate(structureWorldAccess, structureAccessor, chunkGenerator, random, boundingBox, chunkPos, blockPos);
            }

            return false;
        }
    }
}
