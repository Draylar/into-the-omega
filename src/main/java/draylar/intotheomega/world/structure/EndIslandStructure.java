package draylar.intotheomega.world.structure;

import com.mojang.serialization.Codec;
import net.minecraft.structure.StructureGeneratorFactory;
import net.minecraft.structure.StructurePiecesCollector;
import net.minecraft.structure.StructurePiecesGenerator;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

import java.util.Optional;
import java.util.Random;

public abstract class EndIslandStructure extends StructureFeature<DefaultFeatureConfig> {

    public EndIslandStructure(Codec<DefaultFeatureConfig> codec, HeightStructurePiecesGenerator<DefaultFeatureConfig> generator) {
        super(codec, context -> addPieces(context, generator));
    }

    private static Optional<StructurePiecesGenerator<DefaultFeatureConfig>> addPieces(StructureGeneratorFactory.Context<DefaultFeatureConfig> context, HeightStructurePiecesGenerator<DefaultFeatureConfig> generator) {
        int height = getGenerationHeight(context.chunkPos(), context.chunkGenerator(), context.world());

        // Do not place structures close to the primary End Island.
        if(Math.sqrt(Math.pow(context.chunkPos().getStartX(), 2) + Math.pow(context.chunkPos().getStartX(), 2)) <= 1000) {
            return Optional.empty();
        }

        // Do not place structures above Y60 or below Y50.
        if(height >= 60 || height <= 50) {
            return Optional.empty();
        }

        return Optional.of(generator.generatePieces(height));
    }

    private static int getGenerationHeight(ChunkPos pos, ChunkGenerator chunkGenerator, HeightLimitView world) {
        Random random = new Random(pos.x + pos.z * 10387313L);
        BlockRotation blockRotation = BlockRotation.random(random);
        int xRotationOffset = 5;
        int zRotationOffset = 5;
        if(blockRotation == BlockRotation.CLOCKWISE_90) {
            xRotationOffset = -5;
        } else if(blockRotation == BlockRotation.CLOCKWISE_180) {
            xRotationOffset = -5;
            zRotationOffset = -5;
        } else if(blockRotation == BlockRotation.COUNTERCLOCKWISE_90) {
            zRotationOffset = -5;
        }

        int xOffset = pos.getOffsetX(7);
        int zOffset = pos.getOffsetZ(7);
        int worldHeight = chunkGenerator.getHeightInGround(xOffset, zOffset, Heightmap.Type.WORLD_SURFACE_WG, world);
        int n = chunkGenerator.getHeightInGround(xOffset, zOffset + zRotationOffset, Heightmap.Type.WORLD_SURFACE_WG, world);
        int o = chunkGenerator.getHeightInGround(xOffset + xRotationOffset, zOffset, Heightmap.Type.WORLD_SURFACE_WG, world);
        int p = chunkGenerator.getHeightInGround(xOffset + xRotationOffset, zOffset + zRotationOffset, Heightmap.Type.WORLD_SURFACE_WG, world);
        return Math.min(Math.min(worldHeight, n), Math.min(o, p));
    }

    @FunctionalInterface
    public interface HeightStructurePiecesGenerator<C extends FeatureConfig> {
        StructurePiecesGenerator<C> generatePieces(int height);
    }
}
