package draylar.intotheomega.world.structure;

import com.mojang.serialization.Codec;
import draylar.intotheomega.api.StructureStartCache;
import draylar.intotheomega.api.world.StructureCache;
import draylar.intotheomega.impl.StructurePieceExtensions;
import draylar.intotheomega.registry.OmegaBlocks;
import draylar.intotheomega.registry.world.OmegaStructurePieces;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.structure.*;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.noise.OctavePerlinNoiseSampler;
import net.minecraft.util.math.noise.OctaveSimplexNoiseSampler;
import net.minecraft.util.math.noise.SimplexNoiseSampler;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.random.SimpleRandom;

import java.util.Arrays;
import java.util.Random;

public class StarfieldStructure extends StructureFeature<DefaultFeatureConfig> {

    public StarfieldStructure(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec, StructureGeneratorFactory.simple(StructureGeneratorFactory.checkForBiomeOnTop(Heightmap.Type.WORLD_SURFACE_WG), StarfieldStructure::addPieces));
    }

    public static void addPieces(StructurePiecesCollector collector, StructurePiecesGenerator.Context<DefaultFeatureConfig> context) {
        BlockPos blockPos = new BlockPos(context.chunkPos().getStartX(), 0, context.chunkPos().getStartZ());
        collector.addPiece(new Piece(blockPos));
    }

    public static class Piece extends StructurePiece {

        private static final SimplexNoiseSampler NOISE = new SimplexNoiseSampler(new SimpleRandom(0));

        public Piece(BlockPos pos) {
            super(OmegaStructurePieces.STARFIELD, 0,
                    new BlockBox(pos.getX() - 128, pos.getY(), pos.getZ() - 128, pos.getX() + 128, pos.getY() + 128, pos.getZ() + 128));

            setOrientation(null);
        }

        public Piece(StructureContext context, NbtCompound compound) {
            super(OmegaStructurePieces.STARFIELD, compound);
            setOrientation(null);
        }

        @Override
        public void writeNbt(StructureContext context, NbtCompound nbt) {

        }

        @Override
        public void generate(StructureWorldAccess world, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox chunkBox, ChunkPos chunkPos, BlockPos pos) {
            StructureCache cache = StructureStartCache.get(StructurePieceExtensions.get(this).getStructureStart()).getPlacementCache();
            cache.placeOrCompute(0, world, this, chunkBox, chunkPos, (blocks) -> {
                OctaveSimplexNoiseSampler blockSampler = new OctaveSimplexNoiseSampler(new SimpleRandom(random.nextLong()), Arrays.asList(1, 2, 3));

                for (int x = -128; x <= 128; x++) {
                    for (int z = -128; z <= 128; z++) {
                        double distance = Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2));
                        double sample = Math.max(0.0f, NOISE.sample(x / 50f, 0, z / 50f) * 75);
                        double height = Math.max(0.0f, NOISE.sample(x / 100f, 100, z / 100f) * 10);
                        double blockNoise = blockSampler.sample(x / 200f, z / 200f, true);
                        Block block = Blocks.END_STONE;
                        if(blockNoise >= 0.3f) {
                            block = Blocks.OBSIDIAN;
                        } else if (blockNoise >= 0.0f) {
                            block = Blocks.END_STONE_BRICKS;
                        }

                        if(distance <= 128 - sample) {
                            for(int i = 1; i < Math.max(2, 1 + height); i++) {
                                blocks.put(new BlockPos(pos.getX() + x, i, pos.getZ() + z), block.getDefaultState());
                            }
                        }
                    }
                }

                // place random stars
                Random seededRandom = new Random();
                seededRandom.setSeed(pos.asLong());
                for(int i = 0; i < 20; i++) {
                    int x = seededRandom.nextInt(256) - 128;
                    int z = seededRandom.nextInt(256) - 128;
                    int size = seededRandom.nextInt(40) + 40;
                    BlockPos lesserStarPosition = pos.toImmutable().add(x, 0, z);

                    if(Math.sqrt(lesserStarPosition.getSquaredDistance(pos)) <= 100) {
                        for (BlockPos starPosition : BlockPos.iterateOutwards(lesserStarPosition, size, size, size)) {
                            double noise = NOISE.sample(starPosition.getX() / 10f, starPosition.getY() / 10f, starPosition.getZ() / 10f) * 10;
                            if(starPosition.getSquaredDistance(lesserStarPosition) <= size - noise) {
                                blocks.put(starPosition.toImmutable(), OmegaBlocks.LESSER_STAR_EDGE.getDefaultState());
                            }
                        }
                    }
                }

                return new StructureCache.ChunkSectionedEntry(blocks);
            });
        }
    }
}
