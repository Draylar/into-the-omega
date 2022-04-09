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
        if(height >= 60) {
            return Optional.empty();
        }

        return Optional.of(generator.generatePieces(height));
    }

    @Override
    public boolean isUniformDistribution() {
        return false;
    }

    private static int getGenerationHeight(ChunkPos pos, ChunkGenerator chunkGenerator, HeightLimitView world) {
        Random random = new Random(pos.x + pos.z * 10387313L);
        BlockRotation blockRotation = BlockRotation.random(random);
        int i = 5;
        int j = 5;
        if(blockRotation == BlockRotation.CLOCKWISE_90) {
            i = -5;
        } else if(blockRotation == BlockRotation.CLOCKWISE_180) {
            i = -5;
            j = -5;
        } else if(blockRotation == BlockRotation.COUNTERCLOCKWISE_90) {
            j = -5;
        }
        int k = pos.getOffsetX(7);
        int l = pos.getOffsetZ(7);
        int m = chunkGenerator.getHeightInGround(k, l, Heightmap.Type.WORLD_SURFACE_WG, world);
        int n = chunkGenerator.getHeightInGround(k, l + j, Heightmap.Type.WORLD_SURFACE_WG, world);
        int o = chunkGenerator.getHeightInGround(k + i, l, Heightmap.Type.WORLD_SURFACE_WG, world);
        int p = chunkGenerator.getHeightInGround(k + i, l + j, Heightmap.Type.WORLD_SURFACE_WG, world);
        return Math.min(Math.min(m, n), Math.min(o, p));
    }

    @FunctionalInterface
    public interface HeightStructurePiecesGenerator<C extends FeatureConfig> {
        public StructurePiecesGenerator<DefaultFeatureConfig> generatePieces(int height);
    }
}
