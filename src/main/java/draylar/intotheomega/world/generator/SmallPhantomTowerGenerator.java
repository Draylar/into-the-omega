package draylar.intotheomega.world.generator;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.registry.OmegaStructurePieces;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.structure.*;
import net.minecraft.structure.processor.BlockIgnoreStructureProcessor;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ServerWorldAccess;

import java.util.List;
import java.util.Random;

public class SmallPhantomTowerGenerator {

    private static final Identifier TEMPLATE = IntoTheOmega.id("small_phantom_tower");
    private static final Identifier LOOT_TABLE = IntoTheOmega.id("chests/eye_altar");
    private static final Identifier CHEST = new Identifier("intotheomega:chest");

    public static void addPieces(StructureManager manager, BlockPos pos, BlockRotation rotation, List<StructurePiece> pieces, Random random) {
        pieces.add(new Piece(manager, TEMPLATE, pos, rotation));
    }

    public static class Piece extends SimpleStructurePiece {

        private final Identifier template;
        private final BlockRotation rotation;

        public Piece(StructureManager manager, Identifier template, BlockPos pos, BlockRotation rotation) {
            super(OmegaStructurePieces.SMALL_PHANTOM_TOWER, 0);
            this.template = template;
            this.rotation = rotation;
            this.pos = pos;

            initializeStructureData(manager);
        }

        public Piece(StructureManager manager, CompoundTag tag) {
            super(OmegaStructurePieces.SMALL_PHANTOM_TOWER, tag);
            template = new Identifier(tag.getString("Template"));
            rotation = BlockRotation.valueOf(tag.getString("Rot"));
            initializeStructureData(manager);
        }

        private void initializeStructureData(StructureManager manager) {
            Structure structure = manager.getStructureOrBlank(this.template);
            StructurePlacementData structurePlacementData = new StructurePlacementData().setRotation(rotation).setMirror(BlockMirror.NONE).addProcessor(BlockIgnoreStructureProcessor.IGNORE_STRUCTURE_BLOCKS);
            this.setStructureData(structure, this.pos, structurePlacementData);
        }

        @Override
        protected void toNbt(CompoundTag tag) {
            super.toNbt(tag);
            tag.putString("Template", template.toString());
            tag.putString("Rot", rotation.name());
        }

        @Override
        public void handleMetadata(String metadata, BlockPos pos, ServerWorldAccess access, Random random, BlockBox boundingBox) {
            if(metadata.equals(CHEST.toString())) {
                LootableContainerBlockEntity.setLootTable(access, random, pos.down(), LOOT_TABLE);
            }
        }
    }
}
