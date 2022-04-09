package draylar.intotheomega.world.generator;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.registry.OmegaStructurePieces;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.structure.*;
import net.minecraft.structure.processor.BlockIgnoreStructureProcessor;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ServerWorldAccess;

import java.util.Random;

public class SmallPhantomTowerGenerator extends SimpleStructurePiece {

    private static final Identifier TEMPLATE = IntoTheOmega.id("small_phantom_tower");
    private static final Identifier LOOT_TABLE = IntoTheOmega.id("chests/eye_altar");
    private static final Identifier CHEST = new Identifier("intotheomega:chest");

    public SmallPhantomTowerGenerator(StructureManager manager, BlockPos pos, BlockRotation rotation) {
        super(OmegaStructurePieces.SMALL_PHANTOM_TOWER, 0, manager, TEMPLATE, TEMPLATE.toString(),  new StructurePlacementData().setRotation(rotation), pos);
    }

    public SmallPhantomTowerGenerator(StructureContext context, NbtCompound nbt) {
        super(OmegaStructurePieces.SMALL_PHANTOM_TOWER, nbt, context.structureManager(), id -> new StructurePlacementData().setRotation(BlockRotation.valueOf(nbt.getString("Rotation"))));
    }

    @Override
    public void writeNbt(StructureContext context, NbtCompound nbt) {
        super.writeNbt(context, nbt);
        nbt.putString("Rotation", placementData.getRotation().name());
    }

    @Override
    public void handleMetadata(String metadata, BlockPos pos, ServerWorldAccess access, Random random, BlockBox boundingBox) {
        if(metadata.equals(CHEST.toString())) {
            LootableContainerBlockEntity.setLootTable(access, random, pos.down(), LOOT_TABLE);
        }
    }
}
