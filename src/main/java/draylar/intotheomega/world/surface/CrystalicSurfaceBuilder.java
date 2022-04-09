package draylar.intotheomega.world.surface;

//public class CrystalicSurfaceBuilder extends SurfaceBuilder<TernarySurfaceConfig> {
//
//    public CrystalicSurfaceBuilder(Codec<TernarySurfaceConfig> codec) {
//        super(codec);
//    }
//
//    @Override
//    public void generate(Random random, Chunk chunk, Biome biome, int x, int z, int height, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, TernarySurfaceConfig surfaceBlocks) {
//        if(height != 0) {
//            chunk.setBlockState(new BlockPos(x, height, z), Blocks.OBSIDIAN.getDefaultState(), false);
//            if(noise >= 0.8) {
//                chunk.setBlockState(new BlockPos(x, height, z), Blocks.END_STONE.getDefaultState(), false);
//            }
//        }
//    }
//}
