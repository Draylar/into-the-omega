package draylar.intotheomega.world;

import com.mojang.serialization.Codec;
import draylar.intotheomega.registry.world.OmegaStructurePieces;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.structure.*;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

import java.util.Random;

public class TestSF extends StructureFeature<DefaultFeatureConfig> {

    public TestSF(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec, StructureGeneratorFactory.simple(StructureGeneratorFactory.checkForBiomeOnTop(Heightmap.Type.WORLD_SURFACE_WG), TestSF::addPieces));
    }

    public static void addPieces(StructurePiecesCollector collector, StructurePiecesGenerator.Context<DefaultFeatureConfig> context) {
        BlockPos blockPos = new BlockPos(context.chunkPos().getStartX(), 150, context.chunkPos().getStartZ());
        BlockRotation blockRotation = BlockRotation.random(context.random());
        collector.addPiece(new TestPiece(blockPos));
    }

    public static class TestPiece extends StructurePiece {

        public TestPiece(BlockPos pos) {
            super(OmegaStructurePieces.TEST_PIECE, 0, new BlockBox(pos.getX() - 64, pos.getY() - 64, pos.getZ() - 64, pos.getX() + 64, pos.getY() + 64, pos.getZ() + 64));
            setOrientation(null);
        }

        public TestPiece(StructureContext context, NbtCompound compound) {
            super(OmegaStructurePieces.TEST_PIECE, compound);
            setOrientation(null);
        }

        @Override
        protected void writeNbt(StructureContext context, NbtCompound nbt) {

        }

        @Override
        public void generate(StructureWorldAccess world, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox chunkBox, ChunkPos chunkPos, BlockPos pos) {
            for (int x = -64; x <= 64; x++) {
                for (int z = -64; z <= 64; z++) {
                    addBlock(world, Blocks.GLOWSTONE.getDefaultState(), pos.getX() + x, 150, pos.getZ() + z, chunkBox);
                }
            }

            addBlock(world, Blocks.REDSTONE_BLOCK.getDefaultState(), pos.getX(), 150, pos.getZ(), chunkBox);
        }
    }
}
