package draylar.intotheomega.world.abyss_flower;

// IDEA:
// you know that one minecraft image we had of the mc dungeons end portal with the pillars?
// maybe we could retain the giant spike but have giant stone henges aroun the edge li kthe imag
// then have rift spawn in middle [ AE2 SYSTEM ? ]
//public class AbyssFlowerIslandStructureStart extends SiftingStructureStart {
//
//    public AbyssFlowerIslandStructureStart(StructureFeature<DefaultFeatureConfig> feature, int chunkX, int chunkZ, BlockBox box, int references, long seed) {
//        super(OmegaStructurePieces.CHORUS_ISLAND, feature, chunkX, chunkZ, box, references, seed);
//
//        int realX = chunkX * 16;
//        int realZ = chunkZ * 16;
//
//        this.boundingBox = new BlockBox(new int[]{
//                realX - 100, 150, realZ - 100,
//                realX + 100, 200, realZ + 100
//        });
//    }
//
//    @Override
//    public Map<BlockPos, BlockInfo> place(DynamicRegistryManager registryManager, ChunkGenerator chunkGenerator, StructureManager manager, int chunkX, int chunkZ, Biome biome, DefaultFeatureConfig config) {
//        int height = 30;
//        OpenSimplex2F noise = new OpenSimplex2F(random.nextInt(8911805));
//        Map<BlockPos, BlockInfo> blocks = new HashMap<>();
//        List<Pos2D> flatPositions = new ArrayList<>();
//        List<Pos2D> pillarPositions = new ArrayList<>();
//        BlockPos origin = new BlockPos(chunkX * 16, height, chunkZ * 16);
//
//        long ms = System.currentTimeMillis();
//
//        int radius = 150;
//        int evalRadius = 100;
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
//                double instMinY = minY;
//                double instMaxY = maxY;
//
//                // For each x/z pairing, store the point if it is within the noise radius and not near walls.
//                double distance2d = Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2));
//                if (distance2d <= evalRadius * 0.7) {
//                    flatPositions.add(new Pos2D((int) realX, (int) realZ));
//                } else if(distance2d >= evalRadius * .8 && distance2d <= evalRadius) {
//                    pillarPositions.add(new Pos2D((int) realX, (int) realZ));
//                }
//
//                // Check each vertical position at this x/z pairing.
//                for (double y = instMinY; y <= instMaxY * 1.05; y++) {
//                    double distance = Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2) + Math.pow(y, 2));
//
//                    // Place island blocks
//                    if (distance <= (double) evalRadius) {
//                        if (y <= instMaxY * .9) {
//                            blocks.put(new BlockPos(realX, y + height, realZ), new BlockInfo(Blocks.END_STONE.getDefaultState(), null));
//                        } else if (y <= instMaxY) {
//                            int absX = Math.abs(x);
//                            int absZ = Math.abs(z);
//                            int offset = (absX > 10) && (absZ > 10) ? -1 : 0;
//
//                            if(distance >= evalRadius * .97 || (Math.abs(x) == 10 || Math.abs(z) == 10) || Math.abs(x) == Math.abs(z)) {
//                                blocks.put(new BlockPos(realX, y + height + offset, realZ), new BlockInfo(OmegaBlocks.ABYSSAL_SLATE.getDefaultState(), null));
//                            } else {
//                                blocks.put(new BlockPos(realX, y + height + offset, realZ), new BlockInfo(Blocks.END_STONE_BRICKS.getDefaultState(), null));
//                            }
//                        }
//                    }
//                }
//            }
//        }
//
//        // Now that the island has been placed, we need to carve out a circular cone.
//        for (int size = 10; size > 0; size--) {
//            int downRadius = size * 8;
//
//            for (int x = -downRadius; x <= downRadius; x++) {
//                for (int z = -downRadius; z <= downRadius; z++) {
//                    double distance = Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2));
//
//                    // Determine the TRUE world position of these positions.
//                    double realX = origin.getX() + x;
//                    double realZ = origin.getZ() + z;
//
//                    // work inside circle
//                    if(distance <= downRadius) {
//
//                        // Operate on edges first.
//                        if (distance >= downRadius * .96) {
//                            for (int y = 0; y < (15 - size); y++) {
//                                blocks.put(new BlockPos(realX, height + maxY - y, realZ), new BlockInfo(Blocks.AIR.getDefaultState(), null));
//                            }
//
//                            // place purpur/abyssal lining on each layer at the very front
//                            if (size != 10) {
//                                if (size % 2 == 0) {
//                                    blocks.put(new BlockPos(realX, height + maxY - (15 - size) + 1, realZ), new BlockInfo(OmegaBlocks.ABYSSAL_SLATE.getDefaultState(), null));
//                                } else {
//                                    blocks.put(new BlockPos(realX, height + maxY - (15 - size) + 1, realZ), new BlockInfo(Blocks.PURPUR_BLOCK.getDefaultState(), null));
//                                }
//                            }
//
//                            // lip on far outer edge
//                            else {
//                                int absX = Math.abs(x);
//                                int absZ = Math.abs(z);
//                                int offset = (absX > 10) && (absZ > 10) ? -1 : 0;
//
//                                blocks.put(new BlockPos(realX, height + maxY - (15 - size), realZ), new BlockInfo(Blocks.END_STONE_BRICKS.getDefaultState(), null));
//                                blocks.put(new BlockPos(realX, height + maxY - (15 - size) + 1, realZ), new BlockInfo(Blocks.END_STONE_BRICKS.getDefaultState(), null));
//                                blocks.put(new BlockPos(realX, height + maxY - (15 - size) + 2, realZ), new BlockInfo(Blocks.END_STONE_BRICKS.getDefaultState(), null));
//                                blocks.put(new BlockPos(realX, height + maxY - (15 - size) + 3, realZ), new BlockInfo(Blocks.END_STONE_BRICKS.getDefaultState(), null));
//                                blocks.put(new BlockPos(realX, height + maxY - (15 - size) + 4, realZ), new BlockInfo(Blocks.END_STONE_BRICKS.getDefaultState(), null));
//                                blocks.put(new BlockPos(realX, height + maxY - (15 - size) + offset + 5, realZ), new BlockInfo(OmegaBlocks.ABYSSAL_SLATE.getDefaultState(), null));
//                            }
//                        }
//
//                        // operate on inside
//                        else {
//                            for (int y = 0; y < (15 - size); y++) {
//                                blocks.put(new BlockPos(realX, height + maxY - y, realZ), new BlockInfo(Blocks.AIR.getDefaultState(), null));
//                            }
//
//                            // only place endstone flooring for layers that are not the far outer edge
//                            if (size != 10) {
//                                blocks.put(new BlockPos(realX, height + maxY - (15 - size), realZ), new BlockInfo(Blocks.END_STONE.getDefaultState(), null));
//                            }
//                        }
//                    }
//                }
//            }
//        }
//
//        // generate tree
//        // first, place the trunk
//        // trunk is a giant cylinder that is bigger at the base
//        // iterate over each height
//        for(int y = -10; y < 50; y++) {
//            int width = (int) (- 3 * Math.log(Math.max(0.1, y + 10f)) + 15f);
//
//            // base has
//
//            for(int x = -width * 2; x <= width * 2; x++) {
//                for(int z = -width * 2; z <= width * 2; z++) {
//                    double distance = Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2));
//
//                    // Determine the TRUE world position of these positions.
//                    double realX = origin.getX() + x;
//                    double realZ = origin.getZ() + z;
//                    double eval = noise.noise2(realX / 10, realZ / 10) * width;
//
//                    if(distance <= width + eval) {
//                        blocks.put(new BlockPos(realX, height + maxY + y, realZ), new BlockInfo(Blocks.OBSIDIAN.getDefaultState(), null));
//                    }
//                }
//            }
//        }
////
////        // generate pillars
////        for(int i = 0; i < 15; i++) {
////            Pos2D pos2D = pillarPositions.get(random.nextInt(pillarPositions.size()));
////
////            // make a tall rectangle
////            int pillarRadius = 1 + random.nextInt(2);
////            for(int pillarX = -pillarRadius; pillarX <= pillarRadius; pillarX++) {
////                for(int pillarZ = -pillarRadius; pillarZ <= pillarRadius; pillarZ++) {
////                    int pillarHeight = 20 + random.nextInt(10);
////
////                    // Determine the TRUE world position of these positions.
////                    double realX = pillarX + pos2D.x;
////                    double realZ = pillarZ + pos2D.z;
////
////                    for(int y = -10; y < pillarHeight; y++) {
////                        blocks.put(new BlockPos(realX, height + maxY + y, realZ), new BlockInfo(OmegaBlocks.ABYSSAL_SLATE.getDefaultState(), null));
////                    }
////                }
////            }
////        }
//
//        // place 4 pillars
//        placePillars(blocks, origin.add(0, 15, 87));
//        placePillars(blocks, origin.add(0, 15, -87));
//        placePillars(blocks, origin.add(87, 15, 0));
//        placePillars(blocks, origin.add(-87, 15, 0));
//
//        System.out.println("Time to generate Abyss Flower: " + (System.currentTimeMillis() - ms));
//
//        return blocks;
//    }
//
//    public void placePillars(Map<BlockPos, BlockInfo> blocks, BlockPos origin) {
//        // center endstone
//        for(int x = -1; x <= 1; x++){
//            for(int z = -1; z <= 1; z++){
//                for(int y = 0; y < 8; y++) {
//                    blocks.put(origin.add(x, y, z), new BlockInfo(Blocks.END_STONE_BRICKS.getDefaultState(), null));
//                }
//            }
//        }
//
//        // outer corner
//        for(int i = 2; i <= 5; i++) {
//            Direction direction = Direction.byId(i);
//            Direction right = direction.rotateYClockwise();
//            BlockPos base = origin.offset(right, 2).offset(direction, 2);
//
//            // place
//            for(int y = 0; y < 8; y++) {
//                blocks.put(base.add(0, y, 0), new BlockInfo(OmegaBlocks.ABYSSAL_SLATE.getDefaultState(), null));
//            }
//        }
//
//        // ceiling
//        for(int x = -1; x <= 1; x++) {
//            for(int z = -1; z <= 1; z++) {
//                blocks.put(origin.add(x, 8, z), new BlockInfo(OmegaBlocks.ABYSSAL_SLATE_SLAB.getDefaultState(), null));
//            }
//        }
//    }
//
//    @Override
//    public void setBoundingBoxFromChildren() {
//
//    }
//}
