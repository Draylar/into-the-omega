package draylar.intotheomega.world.surface;

//public class ChorusForestSurfaceBuilder extends SurfaceBuilder<TernarySurfaceConfig> {
//
//    public ChorusForestSurfaceBuilder(Codec<TernarySurfaceConfig> codec) {
//        super(codec);
//    }
//
//    @Override
//    public void generate(Random random, Chunk chunk, Biome biome, int x, int z, int height, double noise, BlockState defaultBlock, BlockState fluidBlock, int seaLevel, long seed, TernarySurfaceConfig ternarySurfaceConfig) {
//        @Nullable TheEndBiomeSource cached = EndBiomeSourceCache.cached;
//
//        double modifier = 1.0f;
//        if(cached != null) {
//            // TODO: cache
//            List<Biome> nearbyBiomes = new ArrayList<>(cached.getBiomesInArea(x, 60, z, 16));
//
//            // collect biome -> key pairings
//            Map<Biome, RegistryKey<Biome>> biomeByKey = new HashMap<>();
//            nearbyBiomes.forEach(nearby -> {
//                RegistryKey<Biome> biomeKey = EndBiomeSourceCache.manager.get(Registry.BIOME_KEY).getKey(nearby).get(); // TODO: BAD
//
//                if(!biomeKey.equals(ChorusForestBiome.KEY)) {
//                    biomeByKey.put(nearby, biomeKey);
//                }
//            });
//
//            for (Biome nearby : biomeByKey.keySet()) {
//                BlockPos biomeLocation = cached.locateBiome(x, 60, z, 16, found -> biomeByKey.containsKey(found) && biomeByKey.get(found).equals(biomeByKey.get(nearby)), random);
//
//                if(biomeLocation != null) {
//                    modifier = Math.min(modifier, Math.sqrt(Math.pow(biomeLocation.getX() - x, 2) + Math.pow(biomeLocation.getZ() - z, 2)) / 16d);
//                }
//            }
//        }
//
//        // Top block is always Chorus Grass.
//        if(height != 0) {
//            chunk.setBlockState(new BlockPos(x, height, z), OmegaBlocks.CHORUS_GRASS.getDefaultState(), false);
//            if(noise >= 0.8) {
//                int top = (int) (noise * 10);
//                for (int i = 0; i <= top * modifier; i++) {
//                    if(i == top) {
//                        chunk.setBlockState(new BlockPos(x, height + i - 5, z), OmegaBlocks.CHORUS_GRASS.getDefaultState(), false);
//                    } else {
//                        chunk.setBlockState(new BlockPos(x, height + i - 5, z), Blocks.END_STONE.getDefaultState(), false);
//                    }
//                }
//            }
//        }
//    }
//}
