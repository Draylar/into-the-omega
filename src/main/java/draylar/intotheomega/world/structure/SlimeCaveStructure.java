package draylar.intotheomega.world.structure;

import com.mojang.serialization.Codec;
import draylar.intotheomega.api.StructureStartCache;
import draylar.intotheomega.api.world.StructureCache;
import draylar.intotheomega.impl.StructurePieceExtensions;
import draylar.intotheomega.registry.OmegaBlocks;
import draylar.intotheomega.registry.world.OmegaStructurePieces;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.structure.*;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.noise.PerlinNoiseSampler;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.random.SimpleRandom;

import java.util.Random;

public class SlimeCaveStructure extends StructureFeature<DefaultFeatureConfig> {

    public SlimeCaveStructure(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec, StructureGeneratorFactory.simple(StructureGeneratorFactory.checkForBiomeOnTop(Heightmap.Type.WORLD_SURFACE_WG), SlimeCaveStructure::addPieces));
    }

    public static void addPieces(StructurePiecesCollector collector, StructurePiecesGenerator.Context<DefaultFeatureConfig> context) {
        BlockPos blockPos = new BlockPos(context.chunkPos().getStartX(), 5, context.chunkPos().getStartZ());
        collector.addPiece(new Piece(blockPos));
    }

    public static class Piece extends StructurePiece {

        private static final PerlinNoiseSampler NOISE = new PerlinNoiseSampler(new SimpleRandom(0));

        public Piece(BlockPos pos) {
            super(OmegaStructurePieces.SLIME_CAVE, 0,
                    new BlockBox(pos.getX() - 128, pos.getY(), pos.getZ() - 128, pos.getX() + 128, pos.getY() + 64, pos.getZ() + 128));

            setOrientation(null);
        }

        public Piece(StructureContext context, NbtCompound compound) {
            super(OmegaStructurePieces.SLIME_CAVE, compound);
            setOrientation(null);
        }

        @Override
        public void writeNbt(StructureContext context, NbtCompound nbt) {

        }

        @Override
        public void generate(StructureWorldAccess world, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox chunkBox, ChunkPos chunkPos, BlockPos pos) {
            StructureCache cache = StructureStartCache.get(StructurePieceExtensions.get(this).getStructureStart()).getPlacementCache();
            cache.placeOrCompute(0, world, this, chunkBox, chunkPos, (blocks) -> {
                for (int x = -128; x <= 128; x++) {
                    for (int z = -128; z <= 128; z++) {
                        int height = 30;
                        for (int y = 1; y < height; y++) {
                            double distanceFromCenter = Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2));
                            double noiseAtPosition = NOISE.sample((pos.getX() + x) / 25f, y / 25f, (pos.getZ() + z) / 25f) * 25;

                            if(distanceFromCenter <= 75) {
                                double interiorNoise = NOISE.sample((pos.getX() + x) / 25f, y / 300f, (pos.getZ() + z) / 25f);

                                // we can skip interior ~60 blocks because we know they'll always evaluate to air
                                // apply additional noise to get columns inside the slime cave
                                if(interiorNoise >= 0.3) {
                                    blocks.put(pos.add(x, y, z), OmegaBlocks.OMEGA_SLIME_BRICKS.getDefaultState());
                                } else {
                                    if(y <= 2 || y > height - 2) {
                                        blocks.put(pos.add(x, y, z), Blocks.END_STONE_BRICKS.getDefaultState());
                                    } else {
                                        blocks.put(pos.add(x, y, z), Blocks.VOID_AIR.getDefaultState());
                                    }
                                }
                            } else {
                                double maxDistance = 100 - noiseAtPosition;
                                if(distanceFromCenter <= maxDistance && distanceFromCenter >= 75) {
                                    blocks.put(pos.add(x, y, z), Blocks.END_STONE.getDefaultState());
                                }
                            }
                        }
                    }
                }

                return new StructureCache.ChunkSectionedEntry(blocks);
            });
        }
    }
}