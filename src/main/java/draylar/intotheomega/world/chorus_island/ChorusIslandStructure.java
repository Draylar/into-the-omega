package draylar.intotheomega.world.chorus_island;

import com.mojang.serialization.Codec;
import draylar.intotheomega.registry.OmegaStructurePieces;
import draylar.intotheomega.world.api.BaseIslandStructure;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.structure.StructureManager;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

import java.util.Random;

//public class ChorusIslandStructure extends BaseIslandStructure {
//
//    public ChorusIslandStructure(Codec<DefaultFeatureConfig> codec) {
//        super(codec);
//    }
//
//    @Override
//    public String getId() {
//        return "chorus_island";
//    }
//
//    @Override
//    public StructureStartFactory<DefaultFeatureConfig> getStructureStartFactory() {
//        return ChorusIslandStructureStart::new;
//    }
//
//    public static class Piece extends SiftingStructureGenerator {
//
//        public Piece(StructureManager manager, NbtCompound tag) {
//            super(OmegaStructurePieces.CHORUS_ISLAND, manager, tag);
//        }
//    }
//}
