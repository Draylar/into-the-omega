package draylar.intotheomega.registry;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.world.api.SiftingStructureGenerator;
import draylar.intotheomega.world.generator.EyeAltarGenerator;
import draylar.intotheomega.world.generator.MediumPhantomTowerGenerator;
import draylar.intotheomega.world.generator.SmallPhantomTowerGenerator;
import draylar.intotheomega.world.spike.SpikeStructureGenerator;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.util.registry.Registry;

public class OmegaStructurePieces {

    public static final StructurePieceType SMALL_PHANTOM_TOWER = register("small_phantom_tower", SmallPhantomTowerGenerator.Piece::new);
    public static final StructurePieceType MEDIUM_PHANTOM_TOWER = register("medium_phantom_tower", MediumPhantomTowerGenerator.Piece::new);
    public static final StructurePieceType EYE_ALTAR = register("eye_altar", EyeAltarGenerator.Piece::new);
    public static final StructurePieceType ISLAND = register("island", SiftingStructureGenerator::new);
    public static final StructurePieceType SPIKE = register("spike", SpikeStructureGenerator::new);
    public static final StructurePieceType SIFTING_PIECE = register("sifting_piece", SiftingStructureGenerator::new);

    public static void init() {

    }

    public static StructurePieceType register(String id, StructurePieceType type) {
        return  Registry.register(Registry.STRUCTURE_PIECE, IntoTheOmega.id(id), type);
    }
}
