package draylar.intotheomega.world.spike;

//public class SpikeStructureGenerator extends StructurePieceWithDimensions {
//
//    public static final List<Block> potentials = Arrays.asList(Blocks.STONE, Blocks.ANDESITE, Blocks.OBSIDIAN, Blocks.RED_WOOL, Blocks.GREEN_WOOL, Blocks.ORANGE_TERRACOTTA, Blocks.PURPLE_TERRACOTTA, Blocks.OAK_LOG, Blocks.SPRUCE_LOG, Blocks.PINK_TERRACOTTA, Blocks.YELLOW_TERRACOTTA, Blocks.ORANGE_GLAZED_TERRACOTTA);
//    public final Map<BlockPos, BlockState> blocks = new HashMap<>();
//
//    public SpikeStructureGenerator(Random random, int x, int z) {
//        super(OmegaStructurePieces.SPIKE, random, x, 30, z, 16, 48, 16);
//    }
//
//    public SpikeStructureGenerator(StructureManager manager, NbtCompound tag) {
//        super(OmegaStructurePieces.SPIKE, tag);
//    }
//
//    @Override
//    public void toNbt(NbtCompound tag) {
//
//    }
//
//    @Override
//    public boolean generate(StructureWorldAccess structureWorldAccess, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
//        blocks.forEach((blockPos1, blockState) -> {
//            structureWorldAccess.setBlockState(blockPos1, blockState, 3);
//        });
//
//        return true;
//    }
//}
