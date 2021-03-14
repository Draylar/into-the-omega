package draylar.intotheomega.world.structure;

import com.mojang.serialization.Codec;
import draylar.intotheomega.world.generator.MediumPhantomTowerGenerator;
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

public class MediumPhantomTowerStructure extends StructureFeature<DefaultFeatureConfig> {

    public MediumPhantomTowerStructure(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean isUniformDistribution() {
        return false;
    }

    @Override
    public StructureStartFactory<DefaultFeatureConfig> getStructureStartFactory() {
        return Start::new;
    }

    @Override
    public boolean shouldStartAt(ChunkGenerator chunkGenerator, BiomeSource biomeSource, long l, ChunkRandom chunkRandom, int i, int j, Biome biome, ChunkPos chunkPos, DefaultFeatureConfig defaultFeatureConfig) {
        return getGenerationHeight(i, j, chunkGenerator) >= 60;
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

        public Start(StructureFeature<DefaultFeatureConfig> feature, int chunkX, int chunkZ, BlockBox box, int references, long seed) {
            super(feature, chunkX, chunkZ, box, references, seed);
        }

        @Override
        public void init(DynamicRegistryManager registryManager, ChunkGenerator chunkGenerator, StructureManager manager, int chunkX, int chunkZ, Biome biome, DefaultFeatureConfig config) {
            BlockRotation blockRotation = BlockRotation.random(this.random);
            int genHeight = getGenerationHeight(chunkX, chunkZ, chunkGenerator);

            if (genHeight >= 60) {
                BlockPos blockPos = new BlockPos(chunkX * 16 + 8, genHeight, chunkZ * 16 + 8);
                MediumPhantomTowerGenerator.addPieces(manager, blockPos, blockRotation, this.children, this.random);
                this.setBoundingBoxFromChildren();
            }
        }
    }
}
