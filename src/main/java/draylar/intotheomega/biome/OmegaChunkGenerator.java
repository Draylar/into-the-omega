package draylar.intotheomega.biome;

import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import draylar.intotheomega.api.OpenSimplex2F;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.structure.StructureSet;
import net.minecraft.util.dynamic.RegistryOps;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.DefaultedRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntryList;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.biome.source.TheEndBiomeSource;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OmegaChunkGenerator extends ChunkGenerator {

    public static final Codec<OmegaChunkGenerator> CODEC = RecordCodecBuilder
            .create(instance -> DebugChunkGenerator
                    .method_41042(instance)
                    .and(RegistryOps.createRegistryCodec(Registry.BIOME_KEY)
                            .forGetter(chunkGenerator -> chunkGenerator.biomes)).apply(instance, instance.stable(OmegaChunkGenerator::new)));

    private static final BlockState[] EMPTY = new BlockState[0];
    private final OpenSimplex2F noise = new OpenSimplex2F(0);
    private Registry<Biome> biomes;

    public OmegaChunkGenerator(Registry<StructureSet> registry, Optional<RegistryEntryList<StructureSet>> optional, BiomeSource biomeSource) {
        super(registry, optional, biomeSource);
    }

    public OmegaChunkGenerator(Registry<StructureSet> structureSets, Registry<Biome> biomes) {
        this(structureSets, Optional.empty(), new TheEndBiomeSource(biomes, 0));
        this.biomes = biomes;
    }

    public Registry<Biome> getBiomes() {
        return biomes;
    }

    @Override
    public Codec<? extends ChunkGenerator> getCodec() {
        return CODEC;
    }

    @Override
    public ChunkGenerator withSeed(long seed) {
        return this;
    }

    @Override
    public MultiNoiseUtil.MultiNoiseSampler getMultiNoiseSampler() {
        return null;
    }

    @Override
    public void carve(ChunkRegion chunkRegion, long seed, BiomeAccess biomeAccess, StructureAccessor structureAccessor, Chunk chunk, GenerationStep.Carver generationStep) {

    }

    @Override
    public void buildSurface(ChunkRegion region, StructureAccessor structures, Chunk chunk) {

    }

    @Override
    public void populateEntities(ChunkRegion region) {

    }

    @Override
    public int getWorldHeight() {
        return 0;
    }

    @Override
    public CompletableFuture<Chunk> populateNoise(Executor executor, Blender blender, StructureAccessor structureAccessor, Chunk chunk) {
        return CompletableFuture.supplyAsync(() -> {
            List<Block> blocks = new ArrayList<>();
            Registry.BLOCK.forEach(blocks::add);
            Heightmap heightmap = chunk.getHeightmap(Heightmap.Type.WORLD_SURFACE_WG);
            int size = 256;
            int evalRadius = 100;
            int minY = -5;
            int maxY = 5;
            int height = 100;

            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {

                    // Determine world position
                    int realX = chunk.getPos().getStartX() + x;
                    int realZ = chunk.getPos().getStartZ() + z;
                    double eval = noise.noise2(realX / 50f, realZ / 50f);

                    // Find center of quad this point is in (quad is the smallest unit)
                    int quadIndexX = (realX / size);
                    int quadIndexZ = (realZ / size);
                    int quadX =  quadIndexX * size + (realX < 0 ? - size / 2 : size / 2);
                    int quadZ = quadIndexZ * size + (realZ < 0 ? - size / 2 : size / 2);
                    double quadrantNoise = noise.noise2(quadX * 1000, quadZ * 1000) / 2.0f + 0.5f;
                    int islandYOffset = (int) (quadrantNoise * 30);

                    double distance = Math.sqrt(Math.pow(quadX - realX, 2) + Math.pow(quadZ - realZ, 2)) - eval * 15;
                    double yExtra = (1.0 - distance / evalRadius);
                    double topExtra = Math.floor((1.0f - (distance / evalRadius)) * 4.0f) + Math.ceil(Math.max(0, noise.noise2(realX / 75f, realZ / 75f) * 3));

                    for (double y = height + minY - yExtra * 15 + islandYOffset; y <= height + maxY + topExtra + islandYOffset; y++) {
                        double positionNoise = noise.noise3_XZBeforeY(realX / 50f, y / 25f, realZ / 50f);
                        double evalYOffset = evalRadius - positionNoise * 10;
                        double topTaper = 0;

                        // Place island blocks
                        if(distance <= evalYOffset) {
                            chunk.setBlockState(new BlockPos(x, y, z), Blocks.END_STONE.getDefaultState(), false);
                            heightmap.trackUpdate(x, (int) y, z, Blocks.END_STONE.getDefaultState());
                        }
                    }
                }
            }

            return chunk;
        }, executor);
    }

    @Override
    public int getSeaLevel() {
        return 0;
    }

    @Override
    public int getMinimumY() {
        return 0;
    }

    @Override
    public int getHeight(int x, int z, Heightmap.Type heightmap, HeightLimitView world) {
        return 0;
    }

    @Override
    public VerticalBlockSample getColumnSample(int x, int z, HeightLimitView world) {
        return new VerticalBlockSample(0, EMPTY);
    }

    @Override
    public void getDebugHudText(List<String> text, BlockPos pos) {

    }
}
