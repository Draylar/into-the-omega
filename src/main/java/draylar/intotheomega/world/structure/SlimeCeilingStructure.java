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
import net.minecraft.util.math.noise.SimplexNoiseSampler;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.random.SimpleRandom;

import java.util.Random;

public class SlimeCeilingStructure extends StructureFeature<DefaultFeatureConfig> {

    public SlimeCeilingStructure(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec, StructureGeneratorFactory.simple(StructureGeneratorFactory.checkForBiomeOnTop(Heightmap.Type.WORLD_SURFACE_WG), SlimeCeilingStructure::addPieces));
    }

    public static void addPieces(StructurePiecesCollector collector, StructurePiecesGenerator.Context<DefaultFeatureConfig> context) {
        BlockPos blockPos = new BlockPos(context.chunkPos().getStartX(), 200, context.chunkPos().getStartZ());
        collector.addPiece(new Piece(blockPos));
    }

    public static class Piece extends StructurePiece {

        private static final SimplexNoiseSampler NOISE = new SimplexNoiseSampler(new SimpleRandom(0));

        public Piece(BlockPos pos) {
            super(OmegaStructurePieces.SLIME_CEILING, 0,
                    new BlockBox(pos.getX() - 128, pos.getY() - 64, pos.getZ() - 128, pos.getX() + 128, pos.getY() + 64, pos.getZ() + 128));

            setOrientation(null);
        }

        public Piece(StructureContext context, NbtCompound compound) {
            super(OmegaStructurePieces.SLIME_CEILING, compound);
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
                        double distanceOut = Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2));
                        double maxDistance = 100 + NOISE.sample(x / 100f, 100, z / 100f) * 25;
                        if(distanceOut < maxDistance) {
                            for(int i = (int) (200 + NOISE.sample(x / 50f, 100, z / 50f) * 3); i >= 190 - NOISE.sample(x / 25f, 100, z / 25f) * 5; i--) {
                                // todo: liquid pools of slime at top
                                blocks.put(new BlockPos(pos.getX() + x, i, pos.getZ() + z), Blocks.END_STONE.getDefaultState());
                            }
                        }

                        // Place dripping slime
                        if(distanceOut >= maxDistance * 0.9 && distanceOut < maxDistance) {
                            int slimeDown = (int) (NOISE.sample(x / 15f, 1000, z / 15f) * 100);
                            for (int i = 200; i >= 200 - slimeDown; i--) {
                                blocks.put(new BlockPos(pos.getX() + x, i, pos.getZ() + z), Blocks.SLIME_BLOCK.getDefaultState());
                            }
                        }
                    }
                }

                return new StructureCache.ChunkSectionedEntry(blocks);
            });
        }
    }
}