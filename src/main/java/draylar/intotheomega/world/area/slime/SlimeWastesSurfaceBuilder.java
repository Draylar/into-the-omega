package draylar.intotheomega.world.area.slime;

//public class SlimeWastesSurfaceBuilder extends SurfaceBuilder<TernarySurfaceConfig> {
//
//    private OpenSimplex2F openSimplex;
//    private JVoronoi voronoi;
//
//    public SlimeWastesSurfaceBuilder(Codec<TernarySurfaceConfig> codec) {
//        super(codec);
//    }
//
//    @Override
//    public void initSeed(long seed) {
//        super.initSeed(seed);
//        openSimplex = new OpenSimplex2F(seed);
//        voronoi = new JVoronoi(seed, 250);
//    }
//
//    @Override
//    public void generate(Random random, Chunk chunk, Biome biome, int x, int z, int height, double noise, BlockState blockState, BlockState blockState2, int l, long m, TernarySurfaceConfig nil) {
//        if(height <= 30) return;
//        BlockPos top = new BlockPos(x, height, z);
//
//        // copy bottom to top
//        BlockPos goingDown = top;
//        int down = 0;
//        while(!chunk.getBlockState(goingDown.down()).isAir()) {
//            goingDown = goingDown.down();
//            down++;
//        }
//
//        for(int i = 0; i < down / 5; i++) {
//            BlockPos pos = top.add(0, 75 + i, 0);
//            double n = openSimplex.noise3_Classic(pos.getX() / 25f, pos.getY() / 25f, pos.getZ() / 25f);
//            if(n <= -.3) {
//                chunk.setBlockState(pos, OmegaBlocks.CONGEALED_OMEGA_SLIME.getDefaultState(), false);
//            } else if (n <= 0.1) {
//                chunk.setBlockState(pos, OmegaBlocks.CONGEALED_SLIME.getDefaultState(), false);
//            } else {
//                chunk.setBlockState(pos, Blocks.END_STONE.getDefaultState(), false);
//            }
//        }
//
//        double distanceToEdge = Math.min(1, (voronoi.getDistanceToEdge(x / 4f, z / 4f) - 100) / 5); // [0, 1], where 0 is on edges
//
//        // bottom & top endstone
//        double baseNoise = openSimplex.noise2(x / 100f, z / 100f) * 3;
//
//        // Floor
//        if(random.nextDouble() <= .4) {
//            chunk.setBlockState(top, Blocks.END_STONE.getDefaultState(), false);
//        } else {
//            chunk.setBlockState(top, Blocks.END_STONE_BRICKS.getDefaultState(), false);
//        }
//
//        // slime bottom & top
//        double slimeRiverNoise = openSimplex.noise3_Classic(x / 50f, 1000, z / 50f) * distanceToEdge;
//        double omegaSlimeRiverNoise = openSimplex.noise3_Classic(x / 25f, 2000, z / 25f) * distanceToEdge;
//
//        // bottom green
//        if (slimeRiverNoise < .1 && distanceToEdge == 1) {
//            if(random.nextDouble() > .5) {
//                chunk.setBlockState(top, OmegaBlocks.CONGEALED_SLIME.getDefaultState(), false);
//            } else {
//                chunk.setBlockState(top, Blocks.SLIME_BLOCK.getDefaultState(), false);
//            }
//        }
//
//        // bottom purple
//        if(omegaSlimeRiverNoise < .025 && distanceToEdge == 1) {
//            chunk.setBlockState(top, OmegaBlocks.CONGEALED_OMEGA_SLIME.getDefaultState(), false);
//        }
//
//        // top
//        for (double i = 0; i < openSimplex.noise2(x / 100f, z / 100f) * 3; i++) {
//            chunk.setBlockState(top.add(0, -i, 0), Blocks.SLIME_BLOCK.getDefaultState(), false);
//        }
//    }
//}