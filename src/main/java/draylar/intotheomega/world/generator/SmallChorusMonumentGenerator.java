package draylar.intotheomega.world.generator;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.registry.world.OmegaStructurePieces;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.loot.LootTables;
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

public class SmallChorusMonumentGenerator extends SimpleStructurePiece {

    private static final Identifier TEMPLATE = IntoTheOmega.id("small_chorus_monument");

    public SmallChorusMonumentGenerator(StructureManager manager, BlockPos pos, BlockRotation rotation) {
        super(OmegaStructurePieces.SMALL_CHORUS_MONUMENT, 0, manager, TEMPLATE, TEMPLATE.toString(),  new StructurePlacementData().setRotation(rotation), pos);
    }

    public SmallChorusMonumentGenerator(StructureContext context, NbtCompound nbt) {
        super(OmegaStructurePieces.SMALL_CHORUS_MONUMENT, nbt, context.structureManager(), id -> new StructurePlacementData().setRotation(BlockRotation.valueOf(nbt.getString("Rotation"))));
    }

    @Override
    public void writeNbt(StructureContext context, NbtCompound nbt) {
        super.writeNbt(context, nbt);
        nbt.putString("Rotation", placementData.getRotation().name());
    }

    @Override
    protected void handleMetadata(String metadata, BlockPos pos, ServerWorldAccess access, Random random, BlockBox boundingBox) {
        if(metadata.startsWith("Chest")) {
            int rand = random.nextInt(2);

            // % chance to remove a chest so there are not 4 at every structure
            if(rand == 0) {
                access.setBlockState(pos.down(), Blocks.AIR.getDefaultState(), 3);
            } else if(boundingBox.contains(pos.down())) {
                LootableContainerBlockEntity.setLootTable(access, random, pos.down(), LootTables.END_CITY_TREASURE_CHEST);
            }
        }
    }
}
