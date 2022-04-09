package draylar.intotheomega.world.ice;

//public class IceIslandStructureStart extends SiftingStructureStart {
//
//    public IceIslandStructureStart(StructureFeature<DefaultFeatureConfig> feature, int chunkX, int chunkZ, BlockBox box, int references, long seed) {
//        super(OmegaStructurePieces.ICE_ISLAND, feature, chunkX, chunkZ, box, references, seed);
//
//        int realX = chunkX * 16;
//        int realZ = chunkZ * 16;
//
//        this.boundingBox = new BlockBox(new int[] {
//                realX - 100, 150, realZ - 100,
//                realX + 100, 235, realZ + 100
//        });
//    }
//
//    @Override
//    public Map<BlockPos, BlockInfo> place(DynamicRegistryManager registryManager, ChunkGenerator chunkGenerator, StructureManager manager, int chunkX, int chunkZ, Biome biome, DefaultFeatureConfig config) {
//        int height = 175;
//        OpenSimplex2F noise = new OpenSimplex2F(random.nextInt(8911805));
//        Map<BlockPos, BlockInfo> blocks = new HashMap<>();
//        List<Pos2D> flatPositions = new ArrayList<>();
//        BlockPos origin = new BlockPos(chunkX * 16, height, chunkZ * 16);
//
//        int radius = 100;
//        int evalRadius = 75;
//        int minY = -radius / 10;
//        int maxY = radius / 10;
//
//        // For each position inside the flat box of our island...
//        for (int x = -radius; x <= radius; x++) {
//            for (int z = -radius; z <= radius; z++) {
//
//                // Determine the TRUE world position of these positions.
//                double realX = origin.getX() + x;
//                double realZ = origin.getZ() + z;
//
//                // Evaluate noise at this position for island shape
//                double eval = noise.noise2(realX / 50f, realZ / 50f) * 15;
//
//                // The maximum/minimum value of each x/z pairing is adjusted up to 1 by a general noise call.
//                // This makes floors/ceilings not flat.
//                double minNoise = Math.max(0, noise.noise2(realX / 25f, realZ / 25f)) * 2f;
//                double instMinY = minY - minNoise;
//                double instMaxY = maxY - minNoise;
//
//                // For each x/z pairing, store the point if it is within the noise radius and not near walls.
//                double distance2d = Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2)) - eval;
//                if (distance2d <= evalRadius * 0.7) {
//                    flatPositions.add(new Pos2D((int) realX, (int) realZ));
//                }
//
//                // Check each vertical position at this x/z pairing.
//                for (double y = instMinY; y <= instMaxY; y++) {
//                    double distance = Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2) + Math.pow(y, 2)) - eval;
//                    double positionNoise = noise.noise3_XZBeforeY(realX / 50f, y / 25f, realZ / 50f);
//                    double evalYOffset = evalRadius - positionNoise * 10;
//
//                    // Place island blocks
//                    if (distance <= evalYOffset) {
//                        if (y >= instMaxY * .9 - (positionNoise * .2) || y <= instMinY * .9 + (positionNoise * .1)) {
//                            blocks.put(new BlockPos(realX, y + height, realZ), new BlockInfo(Blocks.SNOW_BLOCK.getDefaultState(), null));
//                        } else {
//                            blocks.put(new BlockPos(realX, y + height, realZ), new BlockInfo(Blocks.END_STONE.getDefaultState(), null));
//                        }
//                    }
//                }
//            }
//        }
//
//
//        // icicles!
//        Map<BlockPos, BlockInfo> iciclePositions = new HashMap<>();
//        for (int i = 0; i < 20; i++) {
//            Pos2D randPos = flatPositions.get(random.nextInt(flatPositions.size()));
//
//            // top is ~180
//            BlockPos icicleOrigin = new BlockPos(randPos.x, 180, randPos.z);
//            placeIcicle(random, blocks, iciclePositions, icicleOrigin, 1);
//        }
//
//        // upside-down icicles!
//        for (int i = 0; i < 20; i++) {
//            Pos2D randPos = flatPositions.get(random.nextInt(flatPositions.size()));
//
//            // bottom is ~160
//            BlockPos icicleOrigin = new BlockPos(randPos.x, 165, randPos.z);
//            placeIcicle(random, blocks, iciclePositions, icicleOrigin, -1);
//        }
//
//        blocks.putAll(iciclePositions);
//
//        return blocks;
//    }
//
//    public void placeIcicle(ChunkRandom random, Map<BlockPos, BlockInfo> island, Map<BlockPos, BlockInfo> blocks, BlockPos origin, int polarity) {
//        // 1/5 icicles are large.
//        boolean large = random.nextInt(5) == 0;
//
//        // Determine size bounds information based on whether this icicle is large.
//        int topMin = large ? 25 : 10;
//        int topRand = large ? 25 : 20;
//        int radiusMin = large ? 4 : 2;
//        int radiusRand = large ? 6 : 3;
//
//        // Determine the height, and x/z offset of the tip of this icicle.
//        int top = topMin + random.nextInt(topRand);
//        int topX = random.nextInt(top) - top / 2;
//        int topZ = random.nextInt(top) - top / 2;
//
//        // Large icicles with a positive polarity are occasionally hollow with loot inside.
//        boolean hollow = large && polarity == 1 && random.nextInt(6) == 0;
//
//        // Radius is the size of the base of the icicle.
//        // To is the end tip of the icicle in real-world space.
//        int radius = radiusMin + random.nextInt(radiusRand);
//        Vec3d to = new Vec3d(origin.getX() + topX, origin.getY() + top, origin.getZ() + topZ);
//        Vec3d originVec = new Vec3d(origin.getX(), origin.getY(), origin.getZ());
//
//        // Iterate over a box at the base of the icicle.
//        for (int x = -radius; x <= radius; x++) {
//            for (int z = -radius; z <= radius; z++) {
//
//                // Do a standard circle check on the base.
//                // Each position that overlaps with the circle will draw
//                //   a line from itself to the tip.
//                double fromCenter = Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2));
//
//                // Position is inside the line, draw line now.
//                if (fromCenter <= radius) {
//                    // If this icicle is hollow, skip the interior.
//                    if(hollow) {
//                        if(fromCenter <= radius * .8) {
//                            continue;
//                        }
//                    }
//
//                    // From is the starting position of the line in real-world space.
//                    // We calculate the distance to travel over each iteration between the source
//                    //   and target position and store it in 'per'.
//                    // Current holds the current iteration after each block, and the max iteration
//                    //   cap is held in distance.
//                    //
//                    // To account for polarity, the local-space position and real-world origin
//                    //   are kept separate (current and from, respectively).
//                    // In each iteration, we add the two together, and multiply the result by polarity.
//                    Vec3d from = new Vec3d(origin.getX() + x, origin.getY(), origin.getZ() + z);
//                    double distance = from.distanceTo(to);
//                    Vec3d per = to.subtract(from).normalize();
//                    Vec3d current = Vec3d.ZERO;
//
//                    // For roughly~ each position between the source and target,
//                    // place a block using the offset and current position values.
//                    for (double i = 0; i < distance; i++) {
//                        Vec3d realWorld = from.add(current.multiply(1, polarity, 1));
//                        BlockPos targetPos = new BlockPos(realWorld);
//                        blocks.put(targetPos, new BlockInfo(Blocks.PACKED_ICE.getDefaultState(), null));
//                        current = current.add(per);
//                    }
//                }
//            }
//        }
//
//        // If this icicle was hollow, we want to place an entrance for it.
//        // To do this, we empty out an area towards the base of the icicle.
//        if(hollow) {
//            for(int x = -3; x <= 3; x++) {
//                for(int y = -3; y <= 3; y++) {
//                    for(int z = -3; z <= 3; z++) {
//                        blocks.remove(new BlockPos(x, y + 5, z).add(origin));
//                    }
//                }
//            }
//
//            // place spawner inside
//            Vec3d direction = originVec.subtract(to).normalize().multiply(-5);
//            BlockPos attempt = new BlockPos(origin.getX() + direction.getX(), 180, origin.getZ() + direction.getZ());
//            while(island.containsKey(attempt)) {
//                attempt = attempt.up();
//            }
//            blocks.put(attempt, new BlockInfo(Blocks.CHEST.getDefaultState(), null));
//            blocks.put(attempt.up(), new BlockInfo(Blocks.SPAWNER.getDefaultState(), null));
//        }
//    }
//
//    @Override
//    public void setBoundingBoxFromChildren() {
//
//    }
//}
