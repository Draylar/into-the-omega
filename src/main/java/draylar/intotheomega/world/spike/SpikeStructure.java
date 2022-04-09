package draylar.intotheomega.world.spike;

//public class SpikeStructure extends StructureFeature<DefaultFeatureConfig> {
//
//    public SpikeStructure(Codec<DefaultFeatureConfig> codec) {
//        super(codec);
//    }
//
//    @Override
//    public StructureStartFactory<DefaultFeatureConfig> getStructureStartFactory() {
//        return SpikeStructureStart::new;
//    }
//
//    private static int getGenerationHeight(int chunkX, int chunkZ, ChunkGenerator chunkGenerator) {
//        Random random = new Random((long)(chunkX + chunkZ * 10387313));
//        BlockRotation blockRotation = BlockRotation.random(random);
//        int i = 5;
//        int j = 5;
//        if (blockRotation == BlockRotation.CLOCKWISE_90) {
//            i = -5;
//        } else if (blockRotation == BlockRotation.CLOCKWISE_180) {
//            i = -5;
//            j = -5;
//        } else if (blockRotation == BlockRotation.COUNTERCLOCKWISE_90) {
//            j = -5;
//        }
//
//        int k = (chunkX << 4) + 7;
//        int l = (chunkZ << 4) + 7;
//        int m = chunkGenerator.getHeightInGround(k, l, Heightmap.Type.WORLD_SURFACE_WG);
//        int n = chunkGenerator.getHeightInGround(k, l + j, Heightmap.Type.WORLD_SURFACE_WG);
//        int o = chunkGenerator.getHeightInGround(k + i, l, Heightmap.Type.WORLD_SURFACE_WG);
//        int p = chunkGenerator.getHeightInGround(k + i, l + j, Heightmap.Type.WORLD_SURFACE_WG);
//        return Math.min(Math.min(m, n), Math.min(o, p));
//    }
//
//    protected boolean shouldStartAt(ChunkGenerator chunkGenerator, BiomeSource biomeSource, long l, ChunkRandom chunkRandom, int i, int j, Biome biome, ChunkPos chunkPos, DefaultFeatureConfig defaultFeatureConfig) {
//        return getGenerationHeight(i, j, chunkGenerator) >= 60;
//    }
//}
