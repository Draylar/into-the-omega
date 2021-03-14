package draylar.intotheomega.world.api;

import draylar.intotheomega.api.BlockInfo;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

import java.util.HashMap;
import java.util.Map;

public abstract class SiftingStructureStart extends StructureStart<DefaultFeatureConfig> {

    private final StructurePieceType type;

    public SiftingStructureStart(StructurePieceType type, StructureFeature<DefaultFeatureConfig> feature, int chunkX, int chunkZ, BlockBox box, int references, long seed) {
        super(feature, chunkX, chunkZ, box, references, seed);
        this.type = type;
    }

    public abstract Map<BlockPos, BlockInfo> place(DynamicRegistryManager registryManager, ChunkGenerator chunkGenerator, StructureManager manager, int chunkX, int chunkZ, Biome biome, DefaultFeatureConfig config);

    @Override
    public void init(DynamicRegistryManager registryManager, ChunkGenerator chunkGenerator, StructureManager manager, int chunkX, int chunkZ, Biome biome, DefaultFeatureConfig config) {
        Map<BlockPos, BlockInfo> blocks = place(registryManager, chunkGenerator, manager, chunkX, chunkZ, biome, config);
        sift(blocks);
        children.add(new SiftingStructureGenerator(type, random, (chunkX) * 16, (chunkZ) * 16));
    }

    public void sift(Map<BlockPos, BlockInfo> blocks) {
        Map<ChunkPos, SiftingStructureGenerator> pieces = new HashMap<>();

        blocks.forEach((pos, info) -> {
            ChunkPos key = new ChunkPos(pos);

            if (!pieces.containsKey(key)) {
                pieces.put(key, new SiftingStructureGenerator(type, random, key.x * 16, key.z * 16));
            }

            pieces.get(key).blocks.put(pos, info);
        });

        children.addAll(pieces.values());
        setBoundingBoxFromChildren();
    }
}