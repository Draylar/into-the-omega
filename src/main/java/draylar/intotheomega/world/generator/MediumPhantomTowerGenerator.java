package draylar.intotheomega.world.generator;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.registry.world.OmegaStructurePieces;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.structure.SimpleStructurePiece;
import net.minecraft.structure.StructureContext;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ServerWorldAccess;

import java.util.Random;

public class MediumPhantomTowerGenerator extends SimpleStructurePiece {

    private static final Identifier TEMPLATE = IntoTheOmega.id("medium_phantom_tower");
    private static final Identifier LOOT_TABLE = IntoTheOmega.id("chests/eye_altar");
    private static final Identifier CHEST = new Identifier("intotheomega:chest");

    public MediumPhantomTowerGenerator(StructureManager manager, BlockPos pos, BlockRotation rotation) {
        super(OmegaStructurePieces.MEDIUM_PHANTOM_TOWER, 0, manager, TEMPLATE, TEMPLATE.toString(), new StructurePlacementData().setRotation(rotation), pos);
    }

    public MediumPhantomTowerGenerator(StructureContext context, NbtCompound nbt) {
        super(OmegaStructurePieces.MEDIUM_PHANTOM_TOWER, nbt, context.structureManager(), id -> new StructurePlacementData().setRotation(BlockRotation.valueOf(nbt.getString("Rotation"))));
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
