package draylar.intotheomega.world.structure;

import com.mojang.serialization.Codec;
import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.world.generator.SpiralDungeonPiece;
import net.minecraft.structure.StructureGeneratorFactory;
import net.minecraft.structure.StructurePiecesCollector;
import net.minecraft.structure.StructurePiecesGenerator;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class SpiralDungeonStructure extends StructureFeature<DefaultFeatureConfig> {

    private static final Identifier TEMPLATE = IntoTheOmega.id("spiral_dungeon/spiral_dungeon");
    private static final Identifier LAYER = IntoTheOmega.id("spiral_dungeon/spiral_dungeon_layer");

    public SpiralDungeonStructure(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec, StructureGeneratorFactory.simple(StructureGeneratorFactory.checkForBiomeOnTop(Heightmap.Type.WORLD_SURFACE_WG), SpiralDungeonStructure::generatePieces));
    }

    private static void generatePieces(StructurePiecesCollector collector, StructurePiecesGenerator.Context<DefaultFeatureConfig> context) {
        BlockPos blockPos = new BlockPos(context.chunkPos().getStartX(), 60, context.chunkPos().getStartZ());
        collector.addPiece(new SpiralDungeonPiece(context.structureManager(), blockPos, BlockRotation.NONE, TEMPLATE));

        for (int i = 1; i < 10; i++) {
            collector.addPiece(new SpiralDungeonPiece(context.structureManager(), blockPos.add(0, i * 13, 0), BlockRotation.NONE, LAYER));
        }
    }
}