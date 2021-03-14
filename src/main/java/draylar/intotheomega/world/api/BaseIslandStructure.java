package draylar.intotheomega.world.api;

import com.mojang.serialization.Codec;
import draylar.intotheomega.registry.OmegaWorld;
import draylar.intotheomega.world.chorus_island.ChorusIslandStructureStart;
import draylar.intotheomega.world.ice_island.IceIslandStructureStart;
import draylar.intotheomega.world.island.IslandStructureStart;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.StructureConfig;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class BaseIslandStructure extends StructureFeature<DefaultFeatureConfig> {

    private final List<Pair<StructureFeature<DefaultFeatureConfig>, StructureStartFactory<DefaultFeatureConfig>>> factories
            = Arrays.asList(
                    new Pair<StructureFeature<DefaultFeatureConfig>, StructureStartFactory<DefaultFeatureConfig>>(OmegaWorld.CHORUS_ISLAND, ChorusIslandStructureStart::new),
                    new Pair<StructureFeature<DefaultFeatureConfig>, StructureStartFactory<DefaultFeatureConfig>>(OmegaWorld.ICE_ISLAND, IceIslandStructureStart::new),
                    new Pair<StructureFeature<DefaultFeatureConfig>, StructureStartFactory<DefaultFeatureConfig>>(OmegaWorld.BASE_ISLAND, IslandStructureStart::new));

    public BaseIslandStructure(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    private StructureFeature<DefaultFeatureConfig> findFeature(StructureStartFactory<DefaultFeatureConfig> factory) {
        for (Pair<StructureFeature<DefaultFeatureConfig>, StructureStartFactory<DefaultFeatureConfig>> pair : factories) {
            if (pair.getRight().equals(factory)) {
                return pair.getLeft();
            }
        }

        return null;
    }

    private StructureStart<DefaultFeatureConfig> createStart(int chunkX, int chunkZ, BlockBox boundingBox, int referenceCount, long worldSeed) {
        StructureStartFactory<DefaultFeatureConfig> factory = this.getStructureStartFactory();
        return factory.create(findFeature(factory), chunkX, chunkZ, boundingBox, referenceCount, worldSeed);
    }
//
//    @Override
//    public StructureStart<?> tryPlaceStart(DynamicRegistryManager dynamicRegistryManager, ChunkGenerator chunkGenerator, BiomeSource biomeSource, StructureManager structureManager, long worldSeed, ChunkPos chunkPos, Biome biome, int referenceCount, ChunkRandom chunkRandom, StructureConfig structureConfig, DefaultFeatureConfig featureConfig) {
//        ChunkPos chunkPos2 = this.getStartChunk(structureConfig, worldSeed, chunkRandom, chunkPos.x, chunkPos.z);
//        if (chunkPos.x == chunkPos2.x && chunkPos.z == chunkPos2.z && this.shouldStartAt(chunkGenerator, biomeSource, worldSeed, chunkRandom, chunkPos.x, chunkPos.z, biome, chunkPos2, featureConfig)) {
//            StructureStart<DefaultFeatureConfig> structureStart = this.createStart(chunkPos.x, chunkPos.z, BlockBox.empty(), referenceCount, worldSeed);
//            structureStart.init(dynamicRegistryManager, chunkGenerator, structureManager, chunkPos.x, chunkPos.z, biome, featureConfig);
//            if (structureStart.hasChildren()) {
//                return structureStart;
//            }
//        }
//
//        return StructureStart.DEFAULT;
//    }

    @Override
    public StructureStartFactory<DefaultFeatureConfig> getStructureStartFactory() {
        return factories.get(new Random().nextInt(factories.size())).getRight();
    }
}
