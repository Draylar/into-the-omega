package draylar.intotheomega.world.structure;

import com.mojang.serialization.Codec;
import draylar.intotheomega.api.StructureStartCache;
import draylar.intotheomega.api.world.StructureCache;
import draylar.intotheomega.impl.StructurePieceExtensions;
import draylar.intotheomega.registry.world.OmegaStructurePieces;
import draylar.intotheomega.world.SlimeStructureGenerator;
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

public class OmegaSlimeSpiralStructure extends StructureFeature<DefaultFeatureConfig> {

    private static final SimplexNoiseSampler NOISE = new SimplexNoiseSampler(new SimpleRandom(0));

    public OmegaSlimeSpiralStructure(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec, StructureGeneratorFactory.simple(StructureGeneratorFactory.checkForBiomeOnTop(Heightmap.Type.WORLD_SURFACE_WG), OmegaSlimeSpiralStructure::addPieces));
    }

    public static void addPieces(StructurePiecesCollector collector, StructurePiecesGenerator.Context<DefaultFeatureConfig> context) {
        BlockPos blockPos = new BlockPos(context.chunkPos().getStartX(), 0, context.chunkPos().getStartZ());
        collector.addPiece(new OmegaSlimeSpiralVoid(blockPos));
        collector.addPiece(new OmegaSlimeSpiralPiece(blockPos));
    }

    public static class OmegaSlimeSpiralVoid extends StructurePiece {

        public OmegaSlimeSpiralVoid(BlockPos pos) {
            super(OmegaStructurePieces.OMEGA_SLIME_SPIRAL_VOID, 0,
                    new BlockBox(pos.getX() - 128, pos.getY() - 64, pos.getZ() - 128, pos.getX() + 128, pos.getY() + 250, pos.getZ() + 128));

            setOrientation(null);
        }

        public OmegaSlimeSpiralVoid(StructureContext context, NbtCompound compound) {
            super(OmegaStructurePieces.OMEGA_SLIME_SPIRAL_VOID, compound);
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
                        for (int y = 0; y < 70; y++) {
                            double distanceFromCenter = Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2));

                            // Don't calculate noise if we're within 75% of the max radius to save time.
                            if(distanceFromCenter <= 128 * 0.75) {
                                blocks.put(new BlockPos(pos.getX() + x, y, pos.getZ() + z), Blocks.AIR.getDefaultState());
                            } else {
                                double noise = NOISE.sample((pos.getX() + x) / 50f, (pos.getY() + y) / 50f, (pos.getZ() + z) / 50f) * 28;
                                if(distanceFromCenter <= 128 - noise) {
                                    blocks.put(new BlockPos(pos.getX() + x, y, pos.getZ() + z), Blocks.AIR.getDefaultState());
                                }
                            }
                        }
                    }
                }

                return new StructureCache.ChunkSectionedEntry(blocks);
            });
        }
    }

    public static class OmegaSlimeSpiralPiece extends StructurePiece {

        public OmegaSlimeSpiralPiece(BlockPos pos) {
            super(OmegaStructurePieces.OMEGA_SLIME_SPIRAL, 0,
                    new BlockBox(pos.getX() - 48, pos.getY() - 48, pos.getZ() - 48, pos.getX() + 48, pos.getY() + 250, pos.getZ() + 48));


            setOrientation(null);
        }

        public OmegaSlimeSpiralPiece(StructureContext context, NbtCompound compound) {
            super(OmegaStructurePieces.OMEGA_SLIME_SPIRAL, compound);
            setOrientation(null);
        }

        @Override
        public void writeNbt(StructureContext context, NbtCompound nbt) {

        }

        @Override
        public void generate(StructureWorldAccess world, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox chunkBox, ChunkPos chunkPos, BlockPos pos) {
            StructureCache cache = StructureStartCache.get(StructurePieceExtensions.get(this).getStructureStart()).getPlacementCache();
            cache.placeOrCompute(1, world, this, chunkBox, chunkPos, (blocks) -> {
                new SlimeStructureGenerator(this, chunkBox, blocks).spawn(world, pos);
                return new StructureCache.ChunkSectionedEntry(blocks);
            });
        }
    }
}
