package draylar.intotheomega.world.structure.island;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import draylar.intotheomega.api.OpenSimplex2F;
import draylar.intotheomega.api.Pos2D;
import draylar.intotheomega.api.StructureStartCache;
import draylar.intotheomega.api.Vec2i;
import draylar.intotheomega.api.world.StructureCache;
import draylar.intotheomega.impl.StructurePieceExtensions;
import draylar.intotheomega.registry.OmegaBlocks;
import draylar.intotheomega.registry.OmegaEntities;
import draylar.intotheomega.registry.world.OmegaStructurePieces;
import net.minecraft.block.Blocks;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.structure.StructureContext;
import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.StructurePiecesCollector;
import net.minecraft.structure.StructurePiecesGenerator;
import net.minecraft.util.collection.Pool;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.StructureSpawns;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.NetherFortressFeature;

import java.util.*;

public class ChorusIslandStructure extends AbstractIslandStructure {

    private static final ImmutableMap<SpawnGroup, StructureSpawns> SPAWNS = ImmutableMap
            .<SpawnGroup, StructureSpawns>builder()
            .put(SpawnGroup.AMBIENT, new StructureSpawns(StructureSpawns.BoundingBox.PIECE, Pool.of(
                    new SpawnSettings.SpawnEntry(OmegaEntities.CHORUS_COW, 1, 2, 3)
            )))
            .build();

    public ChorusIslandStructure(Codec<DefaultFeatureConfig> codec) {
        super(codec, ChorusIslandStructure::addPieces, ChorusIslandStructure.class);
    }

    public static Map<SpawnGroup, StructureSpawns> getMonsterSpawns() {
        return SPAWNS;
    }

    @Override
    public String getId() {
        return "chorus_island";
    }

    private static void addPieces(StructurePiecesCollector collector, StructurePiecesGenerator.Context<DefaultFeatureConfig> context) {
        BlockPos blockPos = new BlockPos(context.chunkPos().getStartX(), 175, context.chunkPos().getStartZ());
        collector.addPiece(new IslandPiece(blockPos));
    }

    public static class IslandPiece extends StructurePiece {

        public IslandPiece(BlockPos pos) {
            super(OmegaStructurePieces.CHORUS_ISLAND, 0,
                    new BlockBox(pos.getX() - 128, pos.getY() - 64, pos.getZ() - 128, pos.getX() + 128, pos.getY() + 250, pos.getZ() + 128));

            setOrientation(null);
        }

        public IslandPiece(StructureContext context, NbtCompound compound) {
            super(OmegaStructurePieces.CHORUS_ISLAND, compound);
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
                Map<Vec2i, BlockPos> topPositions = new HashMap<>();

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
                                BlockPos targetPosition = new BlockPos(realX, y + height, realZ);
                                blocks.put(targetPosition, Blocks.END_STONE.getDefaultState());

                                // Override top block if needed
                                Vec2i pos2d = new Vec2i((int) realX, (int) realZ);
                                if(!topPositions.containsKey(pos2d)) {
                                    topPositions.put(pos2d, targetPosition.toImmutable());
                                } else {
                                    if(topPositions.get(pos2d).getY() < targetPosition.getY()) {
                                        topPositions.put(pos2d, targetPosition.toImmutable());
                                    }
                                }
                            }
                        }
                    }
                }

                topPositions.forEach((pos2d, pos3d) -> {
                    blocks.put(pos3d, OmegaBlocks.CHORUS_GRASS.getDefaultState());
                });

                return new StructureCache.ChunkSectionedEntry(blocks);
            });
        }
    }
}
