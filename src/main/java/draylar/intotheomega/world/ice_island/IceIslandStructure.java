package draylar.intotheomega.world.ice_island;

import com.mojang.serialization.Codec;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class IceIslandStructure extends StructureFeature<DefaultFeatureConfig> {

    public IceIslandStructure(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean shouldStartAt(ChunkGenerator chunkGenerator, BiomeSource biomeSource, long worldSeed, ChunkRandom random, int chunkX, int chunkZ, Biome biome, ChunkPos chunkPos, DefaultFeatureConfig config) {
        return chunkGenerator.getHeightInGround(chunkX << 4, chunkZ << 4, Heightmap.Type.WORLD_SURFACE_WG) > 35;
    }

    @Override
    public StructureStartFactory<DefaultFeatureConfig> getStructureStartFactory() {
        return IceIslandStructureStart::new;
    }
}
