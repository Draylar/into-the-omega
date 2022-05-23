package draylar.intotheomega.world.structure;

import com.mojang.serialization.Codec;
import draylar.intotheomega.api.BlockTransformationMatrix;
import draylar.intotheomega.api.StructureStartCache;
import draylar.intotheomega.api.world.StructureCache;
import draylar.intotheomega.impl.StructurePieceExtensions;
import draylar.intotheomega.registry.world.OmegaStructurePieces;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.structure.*;
import net.minecraft.util.math.*;
import net.minecraft.util.math.noise.SimplexNoiseSampler;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.random.SimpleRandom;

import java.util.Random;

public class EndThornStructure extends StructureFeature<DefaultFeatureConfig> {

    public EndThornStructure(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec, StructureGeneratorFactory.simple(context -> {
            ChunkPos pos = context.chunkPos();
            double distanceOut = Math.sqrt(Math.pow(pos.x, 2) + Math.pow(pos.z, 2));
            if(distanceOut >= 15 && distanceOut <= 25) {
                return context.isBiomeValid(Heightmap.Type.WORLD_SURFACE_WG);
            }

            return false;
        }, EndThornStructure::addPieces));
    }

    public static void addPieces(StructurePiecesCollector collector, StructurePiecesGenerator.Context<DefaultFeatureConfig> context) {
        BlockPos blockPos = new BlockPos(context.chunkPos().getStartX(), 125 + context.random().nextInt(50), context.chunkPos().getStartZ());
        collector.addPiece(new Piece(blockPos));
    }

    public static class Piece extends StructurePiece {

        private static final SimplexNoiseSampler NOISE = new SimplexNoiseSampler(new SimpleRandom(0));

        public Piece(BlockPos pos) {
            super(OmegaStructurePieces.END_THORN, 0,
                    new BlockBox(pos.getX() - 42, pos.getY() - 64, pos.getZ() - 42, pos.getX() + 42, pos.getY() + 64, pos.getZ() + 42));

            setOrientation(null);
        }

        public Piece(StructureContext context, NbtCompound compound) {
            super(OmegaStructurePieces.END_THORN, compound);
            setOrientation(null);
        }

        @Override
        public void writeNbt(StructureContext context, NbtCompound nbt) {

        }

        @Override
        public void generate(StructureWorldAccess world, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox chunkBox, ChunkPos chunkPos, BlockPos pos) {
            StructureCache cache = StructureStartCache.get(StructurePieceExtensions.get(this).getStructureStart()).getPlacementCache();
            cache.placeOrCompute(0, world, this, chunkBox, chunkPos, (blocks) -> {
                BlockTransformationMatrix matrices = new BlockTransformationMatrix();
                BlockPos origin = new BlockPos(pos.getX(), 160, pos.getZ());
                float xRot = 32 - 64 * world.getRandom().nextFloat();
                float yRot = 32 - 64 * world.getRandom().nextFloat();
                float zRot = 32 - 64 * world.getRandom().nextFloat();

                for (int y = -100; y < 100; y++) {
                    int yPosition = y + 100;
                    int width = (int) ((1 - (Math.abs(y) / 100f)) * 20f);

                    for (int x = -width; x <= width; x++) {
                        for (int z = -width; z <= width; z++) {
                            if(Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2)) <= width) {
                                BlockPos nPos = new BlockPos(pos.getX() + x, pos.getY() + yPosition, pos.getZ() + z);
                                if(NOISE.sample(nPos.getX() / 15f, nPos.getY() / 15f, nPos.getZ() / 15f) <= 0.0f) {
                                    blocks.put(process(matrices, origin, new BlockPos(x, y, z), zRot, yRot, xRot), Blocks.END_STONE.getDefaultState());
                                } else {
                                    blocks.put(process(matrices, origin, new BlockPos(x, y, z), zRot, yRot, xRot), Blocks.OBSIDIAN.getDefaultState());
                                }
                            }
                        }
                    }

                    // spiral
                    double outX = Math.sin(yPosition / 10f) * width * 1.75;
                    double outZ = Math.cos(yPosition / 10f) * width * 1.75;

                    BlockPos root = new BlockPos(pos.getX() + outX, pos.getY() + yPosition, pos.getZ() + outZ);
                    for (BlockPos s : BlockPos.iterateOutwards(root, 3, 3, 3)) {
                        if(Math.sqrt(root.getSquaredDistance(s)) <= 3.0) {
                            blocks.put(process(matrices, origin, origin.subtract(s), zRot, yRot, xRot), Blocks.CRYING_OBSIDIAN.getDefaultState());
                        }
                    }
                }

                return new StructureCache.ChunkSectionedEntry(blocks);
            });
        }

        private BlockPos process(BlockTransformationMatrix matrices, BlockPos originOfSpike, BlockPos localOffset, float zRotation, float yRotation, float xRotation) {
            matrices.push();
            matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(zRotation));
            matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(yRotation));
            matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(xRotation));
            Vector4f v = new Vector4f(localOffset.getX(), localOffset.getY(), localOffset.getZ(), 1.0f);
            v.transform(matrices.peek().position());
            matrices.pop();

            return originOfSpike.add(v.getX(), v.getY(), v.getZ());
        }
    }
}
