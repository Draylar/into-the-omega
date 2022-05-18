package draylar.intotheomega.registry.world;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.world.TestSF;
import draylar.intotheomega.world.generator.*;
import draylar.intotheomega.world.structure.*;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.util.registry.Registry;

public class OmegaStructurePieces {

    public static final StructurePieceType EYE_ALTAR = register("eye_altar", EyeAltarGenerator::new);
    public static final StructurePieceType SMALL_PHANTOM_TOWER = register("small_phantom_tower", SmallPhantomTowerGenerator::new);
    public static final StructurePieceType MEDIUM_PHANTOM_TOWER = register("medium_phantom_tower", MediumPhantomTowerGenerator::new);
    public static final StructurePieceType SMALL_CHORUS_MONUMENT = register("small_chorus_monument", SmallChorusMonumentGenerator::new);
    public static final StructurePieceType BEJEWELED_DUNGEON = register("bejeweled_dungeon", MatrixPedestalGenerator::new);
    public static final StructurePieceType TEST_PIECE = register("test_piece", TestSF.TestPiece::new);
    public static final StructurePieceType OMEGA_SLIME_SPIRAL = register("omega_slime_spiral", OmegaSlimeSpiralStructure.OmegaSlimeSpiralPiece::new);
    public static final StructurePieceType OMEGA_SLIME_SPIRAL_VOID = register("omega_slime_spiral_void", OmegaSlimeSpiralStructure.OmegaSlimeSpiralVoid::new);
    public static final StructurePieceType SLIME_TENDRIL = register("slime_tendril", SlimeTendrilStructure.Piece::new);
    public static final StructurePieceType SLIME_CEILING = register("slime_ceiling", SlimeCeilingStructure.Piece::new);
    public static final StructurePieceType SLIME_CAVE = register("slime_cave", SlimeCaveStructure.Piece::new);
    public static final StructurePieceType END_THORN = register("end_thorn", EndThornStructure.Piece::new);
    public static final StructurePieceType STARFIELD = register("starfield", StarfieldStructure.Piece::new);
    public static final StructurePieceType SPIRAL_DUNGEON = register("spiral_dungeon", SpiralDungeonPiece::new);


//    public static final StructurePieceType SPIKE = register("spike", SpikeStructureGenerator::new);

    // sifting structures
//    public static final StructurePieceType ISLAND = register("island", GenericIslandStructure.Piece::new);
//    public static final StructurePieceType ICE_ISLAND = register("ice_island", IceIslandStructure.Piece::new);
//    public static final StructurePieceType CHORUS_ISLAND = register("chorus_island", ChorusIslandStructure.Piece::new);
//    public static final StructurePieceType ABYSS_FLOWER_ISLAND = register("abyss_flower_island", AbyssFlowerIslandStructure.Piece::new);

    public static void init() {

    }

    public static StructurePieceType register(String id, StructurePieceType type) {
        return Registry.register(Registry.STRUCTURE_PIECE, IntoTheOmega.id(id), type);
    }
}
