package draylar.intotheomega.world.ice;

//public class IceIslandStructure extends BaseIslandStructure {
//
//    public static final Pool<SpawnSettings.SpawnEntry> MOB_SPAWNS = Pool.of(
//            new SpawnSettings.SpawnEntry(OmegaEntities.FROSTED_ENDERMAN, 10, 1, 4),
//            new SpawnSettings.SpawnEntry(OmegaEntities.FROSTED_EYE, 10, 1, 2));
//
//    public IceIslandStructure(Codec<DefaultFeatureConfig> codec) {
//        super(codec);
//    }
//
//    @Override
//    public String getId() {
//        return "ice_island";
//    }
//
//    @Override
//    public StructureStartFactory<DefaultFeatureConfig> getStructureStartFactory() {
//        return IceIslandStructureStart::new;
//    }
//
//    private static void addPieces(StructurePiecesCollector collector, StructurePiecesGenerator.Context<DefaultFeatureConfig> context) {
////        collector.addPiece(new IceIslandStructureStart(this, context.chunkPos().getStartX(), context.chunkPos().getStartZ(), context.));
//    }
//
//    @Override
//    public List<SpawnSettings.SpawnEntry> getMonsterSpawns() {
//        return MOB_SPAWNS;
//    }
//
//    public static class Piece extends SiftingStructureGenerator {
//
//        public Piece(StructureManager manager, NbtCompound tag) {
//            super(OmegaStructurePieces.ICE_ISLAND, manager, tag);
//        }
//    }
//}
