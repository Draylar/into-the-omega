package draylar.intotheomega.world.structure.island;

import com.mojang.serialization.Codec;
import draylar.intotheomega.api.OpenSimplex2F;
import draylar.intotheomega.api.StructureStartCache;
import draylar.intotheomega.api.world.StructureCache;
import draylar.intotheomega.impl.StructurePieceExtensions;
import draylar.intotheomega.registry.OmegaEntities;
import draylar.intotheomega.registry.world.OmegaStructurePieces;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.structure.StructureContext;
import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.StructurePiecesCollector;
import net.minecraft.structure.StructurePiecesGenerator;
import net.minecraft.util.collection.Pool;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;

import java.util.*;

public class EndIslandStructure extends AbstractIslandStructure {

    public static final Pool<SpawnSettings.SpawnEntry> MOB_SPAWNS = Pool.of(
            new SpawnSettings.SpawnEntry(OmegaEntities.FROSTED_ENDERMAN, 10, 1, 4),
            new SpawnSettings.SpawnEntry(OmegaEntities.FROSTED_EYE, 10, 1, 2));

    public EndIslandStructure(Codec<DefaultFeatureConfig> codec) {
        super(codec, EndIslandStructure::addPieces, EndIslandStructure.class);
    }

    @Override
    public String getId() {
        return "end_island";
    }

    private static void addPieces(StructurePiecesCollector collector, StructurePiecesGenerator.Context<DefaultFeatureConfig> context) {
        BlockPos blockPos = new BlockPos(context.chunkPos().getStartX(), 175, context.chunkPos().getStartZ());
        collector.addPiece(new IslandPiece(blockPos));
    }

    public static class IslandPiece extends StructurePiece {

        public IslandPiece(BlockPos pos) {
            super(OmegaStructurePieces.END_ISLAND, 0,
                    new BlockBox(pos.getX() - 128, pos.getY() - 64, pos.getZ() - 128, pos.getX() + 128, pos.getY() + 250, pos.getZ() + 128));

            setOrientation(null);
        }

        public IslandPiece(StructureContext context, NbtCompound compound) {
            super(OmegaStructurePieces.END_ISLAND, compound);
            setOrientation(null);
        }

        @Override
        public void writeNbt(StructureContext context, NbtCompound nbt) {

        }

        @Override
        public void generate(StructureWorldAccess world, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox chunkBox, ChunkPos chunkPos, BlockPos pos) {
            StructureCache cache = StructureStartCache.get(StructurePieceExtensions.get(this).getStructureStart()).getPlacementCache();
            cache.placeOrCompute(0, world, this, chunkBox, chunkPos, (blocks) -> {
                int height = 175;
                OpenSimplex2F noise = new OpenSimplex2F(pos.hashCode());

                int radius = 64;
                int evalRadius = 48;
                int minY = -radius / 6;
                int maxY = radius / 6;

                // For each position inside the flat box of our island...
                for (int x = -radius; x <= radius; x++) {
                    for (int z = -radius; z <= radius; z++) {

                        // Determine the TRUE world position of these positions.
                        double realX = pos.getX() + x;
                        double realZ = pos.getZ() + z;

                        // Evaluate noise at this position for island shape
                        double eval = noise.noise2(realX / 50f, realZ / 50f) * 15;

                        // The maximum/minimum value of each x/z pairing is adjusted up to 1 by a general noise call.
                        // This makes floors/ceilings not flat.
                        double minNoise = Math.max(0, noise.noise2(realX / 25f, realZ / 25f)) * 2f;
                        double instMinY = minY - minNoise;
                        double instMaxY = maxY - minNoise;

                        // Check each vertical position at this x/z pairing.
                        for (double y = instMinY; y <= instMaxY; y++) {
                            double distance = Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2) + Math.pow(y, 2)) - eval;

                            // Place island blocks
                            if(distance <= evalRadius - noise.noise3_XZBeforeY(realX / 50f, y / 25f, realZ / 50f) * 10) {
                                blocks.put(new BlockPos(realX, y + height, realZ), Blocks.END_STONE.getDefaultState());
                            }
                        }
                    }
                }

                return new StructureCache.ChunkSectionedEntry(blocks);
            });
        }
    }
}
