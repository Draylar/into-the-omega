package draylar.intotheomega.world.chorus_island;

import com.mojang.serialization.Codec;
import draylar.intotheomega.registry.OmegaStructurePieces;
import draylar.intotheomega.world.api.BaseIslandStructure;
import draylar.intotheomega.world.api.SiftingStructureGenerator;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.structure.StructureManager;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

import java.util.Random;

public class ChorusIslandStructure extends StructureFeature<DefaultFeatureConfig> {

    public ChorusIslandStructure(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public StructureStartFactory<DefaultFeatureConfig> getStructureStartFactory() {
        return ChorusIslandStructureStart::new;
    }

    public static class Piece extends SiftingStructureGenerator {

        public Piece(Random random, int x, int z) {
            super(OmegaStructurePieces.CHORUS_ISLAND, random, x, z);
        }

        public Piece(StructureManager manager, CompoundTag tag) {
            super(OmegaStructurePieces.CHORUS_ISLAND, manager, tag);
        }
    }
}
