package draylar.intotheomega.world.island;

import com.mojang.serialization.Codec;
import draylar.intotheomega.registry.OmegaStructurePieces;
import draylar.intotheomega.world.api.BaseIslandStructure;
import draylar.intotheomega.world.api.SiftingStructureGenerator;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.structure.StructureManager;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

import java.util.Random;

public class GenericIslandStructure extends BaseIslandStructure {

    public GenericIslandStructure(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public String getId() {
        return "generic_island";
    }

    @Override
    public StructureStartFactory<DefaultFeatureConfig> getStructureStartFactory() {
        return IslandStructureStart::new;
    }

    public static class Piece extends SiftingStructureGenerator {

        public Piece(StructureManager manager, CompoundTag tag) {
            super(OmegaStructurePieces.ISLAND, manager, tag);
        }
    }
}
