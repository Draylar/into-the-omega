package draylar.intotheomega.world.structure;

import com.mojang.serialization.Codec;
import draylar.intotheomega.api.StructureStartCache;
import draylar.intotheomega.api.world.StructureCache;
import draylar.intotheomega.impl.StructurePieceExtensions;
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

public class SlimeTendrilStructure extends StructureFeature<DefaultFeatureConfig> {

    public SlimeTendrilStructure(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec, StructureGeneratorFactory.simple(StructureGeneratorFactory.checkForBiomeOnTop(Heightmap.Type.WORLD_SURFACE_WG), SlimeTendrilStructure::addPieces));
    }

    public static void addPieces(StructurePiecesCollector collector, StructurePiecesGenerator.Context<DefaultFeatureConfig> context) {
        BlockPos blockPos = new BlockPos(context.chunkPos().getStartX(), 0, context.chunkPos().getStartZ());
        collector.addPiece(new Piece(blockPos));
    }

    public static class Piece extends StructurePiece {

        private static final SimplexNoiseSampler NOISE = new SimplexNoiseSampler(new SimpleRandom(0));

        public Piece(BlockPos pos) {
            super(OmegaStructurePieces.SLIME_TENDRIL, 0,
                    new BlockBox(pos.getX() - 128, pos.getY() - 64, pos.getZ() - 128, pos.getX() + 128, pos.getY() + 250, pos.getZ() + 128));

            setOrientation(null);
        }

        public Piece(StructureContext context, NbtCompound compound) {
            super(OmegaStructurePieces.SLIME_TENDRIL, compound);
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
                        double res = NOISE.sample(x / 100f, 30, z / 100f);
                        if(res >= 0.0 && res <= 0.2) {
                            blocks.put(new BlockPos(pos.getX() + x, 30 + (res - 0.1) * 20, pos.getZ() + z), Blocks.SLIME_BLOCK.getDefaultState());
                        }
                    }
                }

                return new StructureCache.ChunkSectionedEntry(blocks);
            });
        }
    }
}
