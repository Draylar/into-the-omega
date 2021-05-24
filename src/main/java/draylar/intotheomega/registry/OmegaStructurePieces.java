package draylar.intotheomega.registry;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.world.abyss_flower.AbyssFlowerIslandStructure;
import draylar.intotheomega.world.chorus_island.ChorusIslandStructure;
import draylar.intotheomega.world.generator.EyeAltarGenerator;
import draylar.intotheomega.world.generator.MediumPhantomTowerGenerator;
import draylar.intotheomega.world.generator.SmallPhantomTowerGenerator;
import draylar.intotheomega.world.ice.IceIslandStructure;
import draylar.intotheomega.world.island.GenericIslandStructure;
import draylar.intotheomega.world.spike.SpikeStructureGenerator;
import draylar.intotheomega.world.structure.BejeweledDungeonStructure;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.util.registry.Registry;

public class OmegaStructurePieces {

    public static final StructurePieceType SMALL_PHANTOM_TOWER = register("small_phantom_tower", SmallPhantomTowerGenerator.Piece::new);
    public static final StructurePieceType MEDIUM_PHANTOM_TOWER = register("medium_phantom_tower", MediumPhantomTowerGenerator.Piece::new);
    public static final StructurePieceType EYE_ALTAR = register("eye_altar", EyeAltarGenerator.Piece::new);
    public static final StructurePieceType BEJEWELED_DUNGEON = register("bejeweled_dungeon", BejeweledDungeonStructure.Generator.Piece::new);
    public static final StructurePieceType SPIKE = register("spike", SpikeStructureGenerator::new);

    // sifting structures
    public static final StructurePieceType ISLAND = register("island", GenericIslandStructure.Piece::new);
    public static final StructurePieceType ICE_ISLAND = register("ice_island", IceIslandStructure.Piece::new);
    public static final StructurePieceType CHORUS_ISLAND = register("chorus_island", ChorusIslandStructure.Piece::new);
    public static final StructurePieceType ABYSS_FLOWER_ISLAND = register("abyss_flower_island", AbyssFlowerIslandStructure.Piece::new);

    public static void init() {

    }

    public static StructurePieceType register(String id, StructurePieceType type) {
        return  Registry.register(Registry.STRUCTURE_PIECE, IntoTheOmega.id(id), type);
    }
}
