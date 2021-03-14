package draylar.intotheomega.world.structure;

import com.mojang.serialization.Codec;
import draylar.intotheomega.world.generator.EyeAltarGenerator;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

import java.util.Random;

public class EyeAltarStructure extends StructureFeature<DefaultFeatureConfig> {

    public EyeAltarStructure(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean isUniformDistribution() {
        return false;
    }

    @Override
    public boolean shouldStartAt(ChunkGenerator chunkGenerator, BiomeSource biomeSource, long l, ChunkRandom chunkRandom, int i, int j, Biome biome, ChunkPos chunkPos, DefaultFeatureConfig defaultFeatureConfig) {
        return getGenerationHeight(i, j, chunkGenerator) >= 60;
    }

    @Override
    public StructureStartFactory<DefaultFeatureConfig> getStructureStartFactory() {
        return Start::new;
    }

    private static int getGenerationHeight(int chunkX, int chunkZ, ChunkGenerator chunkGenerator) {
        Random random = new Random(chunkX + chunkZ * 10387313);
        BlockRotation rotation = BlockRotation.random(random);

        int xOffset = 5;
        int zOffset = 5;
        if (rotation == BlockRotation.CLOCKWISE_90) {
            xOffset = -5;
        } else if (rotation == BlockRotation.CLOCKWISE_180) {
            xOffset = -5;
            zOffset = -5;
        } else if (rotation == BlockRotation.COUNTERCLOCKWISE_90) {
            zOffset = -5;
        }

        int x = (chunkX << 4) + 7;
        int z = (chunkZ << 4) + 7;
        int m = chunkGenerator.getHeightInGround(x, z, Heightmap.Type.WORLD_SURFACE_WG);
        int n = chunkGenerator.getHeightInGround(x, z + zOffset, Heightmap.Type.WORLD_SURFACE_WG);
        int o = chunkGenerator.getHeightInGround(x + xOffset, z, Heightmap.Type.WORLD_SURFACE_WG);
        int p = chunkGenerator.getHeightInGround(x + xOffset, z + zOffset, Heightmap.Type.WORLD_SURFACE_WG);
        return Math.min(Math.min(m, n), Math.min(o, p));
    }

    public static class Start extends StructureStart<DefaultFeatureConfig> {

        public Start(StructureFeature<DefaultFeatureConfig> structureFeature, int i, int j, BlockBox blockBox, int k, long l) {
            super(structureFeature, i, j, blockBox, k, l);
        }

        @Override
        public void init(DynamicRegistryManager dynamicRegistryManager, ChunkGenerator chunkGenerator, StructureManager structureManager, int chunkX, int chunkZ, Biome biome, DefaultFeatureConfig defaultFeatureConfig) {
            BlockRotation blockRotation = BlockRotation.random(this.random);
            int genHeight = getGenerationHeight(chunkX, chunkZ, chunkGenerator);

            if (genHeight >= 60) {
                BlockPos blockPos = new BlockPos(chunkX * 16 + 8, genHeight, chunkZ * 16 + 8);
                EyeAltarGenerator.addPieces(structureManager, blockPos, blockRotation, this.children, this.random);
                this.setBoundingBoxFromChildren();
            }
        }
    }
}
