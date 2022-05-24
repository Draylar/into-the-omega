package draylar.intotheomega.world.structure.island;

import com.mojang.serialization.Codec;
import draylar.intotheomega.api.OpenSimplex2F;
import draylar.intotheomega.api.Pos2D;
import draylar.intotheomega.api.StructureStartCache;
import draylar.intotheomega.api.world.StructureCache;
import draylar.intotheomega.impl.StructurePieceExtensions;
import draylar.intotheomega.registry.OmegaEntities;
import draylar.intotheomega.registry.world.OmegaStructurePieces;
import net.minecraft.block.BlockState;
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
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;

import java.util.*;

public class IceIslandStructure extends AbstractIslandStructure {

    public static final Pool<SpawnSettings.SpawnEntry> MOB_SPAWNS = Pool.of(
            new SpawnSettings.SpawnEntry(OmegaEntities.FROSTED_ENDERMAN, 10, 1, 4),
            new SpawnSettings.SpawnEntry(OmegaEntities.FROSTED_EYE, 10, 1, 2));

    public IceIslandStructure(Codec<DefaultFeatureConfig> codec) {
        super(codec, IceIslandStructure::addPieces, IceIslandStructure.class);
    }

    @Override
    public String getId() {
        return "ice_island";
    }

    private static void addPieces(StructurePiecesCollector collector, StructurePiecesGenerator.Context<DefaultFeatureConfig> context) {
        BlockPos blockPos = new BlockPos(context.chunkPos().getStartX(), 175, context.chunkPos().getStartZ());
        collector.addPiece(new IslandPiece(blockPos));
    }

    public static class IslandPiece extends StructurePiece {

        public IslandPiece(BlockPos pos) {
            super(OmegaStructurePieces.ICE_ISLAND, 0,
                    new BlockBox(pos.getX() - 128, pos.getY() - 64, pos.getZ() - 128, pos.getX() + 128, pos.getY() + 250, pos.getZ() + 128));

            setOrientation(null);
        }

        public IslandPiece(StructureContext context, NbtCompound compound) {
            super(OmegaStructurePieces.ICE_ISLAND, compound);
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
                OpenSimplex2F noise = new OpenSimplex2F(pos.hashCode()); // todo: won't this hashcode change between chunks in the same structure?
                List<Pos2D> flatPositions = new ArrayList<>();

                int radius = 100;
                int evalRadius = 75;
                int minY = -radius / 10;
                int maxY = radius / 10;

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

                        // For each x/z pairing, store the point if it is within the noise radius and not near walls.
                        double distance2d = Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2)) - eval;
                        if(distance2d <= evalRadius * 0.7) {
                            flatPositions.add(new Pos2D((int) realX, (int) realZ));
                        }

                        // Check each vertical position at this x/z pairing.
                        for (double y = instMinY; y <= instMaxY; y++) {
                            double distance = Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2) + Math.pow(y, 2)) - eval;
                            double positionNoise = noise.noise3_XZBeforeY(realX / 50f, y / 25f, realZ / 50f);
                            double evalYOffset = evalRadius - positionNoise * 10;

                            // Place island blocks
                            if(distance <= evalYOffset) {
                                if(y >= instMaxY * .9 - (positionNoise * .2) || y <= instMinY * .9 + (positionNoise * .1)) {
                                    blocks.put(new BlockPos(realX, y + height, realZ), Blocks.SNOW_BLOCK.getDefaultState());
                                } else {
                                    blocks.put(new BlockPos(realX, y + height, realZ), Blocks.END_STONE.getDefaultState());
                                }
                            }
                        }
                    }
                }


                // icicles!
                Map<BlockPos, BlockState> iciclePositions = new HashMap<>();
                for (int i = 0; i < 20; i++) {
                    Pos2D randPos = flatPositions.get(random.nextInt(flatPositions.size()));

                    // top is ~180
                    BlockPos icicleOrigin = new BlockPos(randPos.x, 180, randPos.z);
                    placeIcicle(random, blocks, iciclePositions, icicleOrigin, 1);
                }

                // upside-down icicles!
                for (int i = 0; i < 20; i++) {
                    Pos2D randPos = flatPositions.get(random.nextInt(flatPositions.size()));

                    // bottom is ~160
                    BlockPos icicleOrigin = new BlockPos(randPos.x, 165, randPos.z);
                    placeIcicle(world.getRandom(), blocks, iciclePositions, icicleOrigin, -1);
                }

                blocks.putAll(iciclePositions);
                return new StructureCache.ChunkSectionedEntry(blocks);
            });
        }

        public void placeIcicle(Random random, Map<BlockPos, BlockState> island, Map<BlockPos, BlockState> blocks, BlockPos origin, int polarity) {
            // 1/5 icicles are large.
            boolean large = random.nextInt(5) == 0;

            // Determine size bounds information based on whether this icicle is large.
            int topMin = large ? 25 : 10;
            int topRand = large ? 25 : 20;
            int radiusMin = large ? 4 : 2;
            int radiusRand = large ? 6 : 3;

            // Determine the height, and x/z offset of the tip of this icicle.
            int top = topMin + random.nextInt(topRand);
            int topX = random.nextInt(top) - top / 2;
            int topZ = random.nextInt(top) - top / 2;

            // Large icicles with a positive polarity are occasionally hollow with loot inside.
            boolean hollow = large && polarity == 1 && random.nextInt(6) == 0;

            // Radius is the size of the base of the icicle.
            // To is the end tip of the icicle in real-world space.
            int radius = radiusMin + random.nextInt(radiusRand);
            Vec3d to = new Vec3d(origin.getX() + topX, origin.getY() + top, origin.getZ() + topZ);
            Vec3d originVec = new Vec3d(origin.getX(), origin.getY(), origin.getZ());

            // Iterate over a box at the base of the icicle.
            for (int x = -radius; x <= radius; x++) {
                for (int z = -radius; z <= radius; z++) {

                    // Do a standard circle check on the base.
                    // Each position that overlaps with the circle will draw
                    //   a line from itself to the tip.
                    double fromCenter = Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2));

                    // Position is inside the line, draw line now.
                    if(fromCenter <= radius) {
                        // If this icicle is hollow, skip the interior.
                        if(hollow) {
                            if(fromCenter <= radius * .8) {
                                continue;
                            }
                        }

                        // From is the starting position of the line in real-world space.
                        // We calculate the distance to travel over each iteration between the source
                        //   and target position and store it in 'per'.
                        // Current holds the current iteration after each block, and the max iteration
                        //   cap is held in distance.
                        //
                        // To account for polarity, the local-space position and real-world origin
                        //   are kept separate (current and from, respectively).
                        // In each iteration, we add the two together, and multiply the result by polarity.
                        Vec3d from = new Vec3d(origin.getX() + x, origin.getY(), origin.getZ() + z);
                        double distance = from.distanceTo(to);
                        Vec3d per = to.subtract(from).normalize();
                        Vec3d current = Vec3d.ZERO;

                        // For roughly~ each position between the source and target,
                        // place a block using the offset and current position values.
                        for (double i = 0; i < distance; i++) {
                            Vec3d realWorld = from.add(current.multiply(1, polarity, 1));
                            BlockPos targetPos = new BlockPos(realWorld);
                            blocks.put(targetPos, Blocks.PACKED_ICE.getDefaultState());
                            current = current.add(per);
                        }
                    }
                }
            }

            // If this icicle was hollow, we want to place an entrance for it.
            // To do this, we empty out an area towards the base of the icicle.
            if(hollow) {
                for (int x = -3; x <= 3; x++) {
                    for (int y = -3; y <= 3; y++) {
                        for (int z = -3; z <= 3; z++) {
                            blocks.remove(new BlockPos(x, y + 5, z).add(origin));
                        }
                    }
                }

                // place spawner inside
                Vec3d direction = originVec.subtract(to).normalize().multiply(-5);
                BlockPos attempt = new BlockPos(origin.getX() + direction.getX(), 180, origin.getZ() + direction.getZ());
                while (island.containsKey(attempt)) {
                    attempt = attempt.up();
                }
                blocks.put(attempt, Blocks.CHEST.getDefaultState());
                blocks.put(attempt.up(), Blocks.SPAWNER.getDefaultState());
            }
        }
    }
}
