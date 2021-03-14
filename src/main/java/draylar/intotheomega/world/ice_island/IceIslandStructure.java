package draylar.intotheomega.world.ice_island;

import com.mojang.serialization.Codec;
import draylar.intotheomega.registry.OmegaStructurePieces;
import draylar.intotheomega.world.api.BaseIslandStructure;
import draylar.intotheomega.world.api.SiftingStructureGenerator;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.structure.StructureManager;
import net.minecraft.util.math.BlockBox;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

import java.util.List;
import java.util.Random;

public class IceIslandStructure extends StructureFeature<DefaultFeatureConfig> {

    public IceIslandStructure(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public StructureStartFactory<DefaultFeatureConfig> getStructureStartFactory() {
        return IceIslandStructureStart::new;
    }

    public static class Piece extends SiftingStructureGenerator {

        public Piece(Random random, int x, int z) {
            super(OmegaStructurePieces.ICE_ISLAND, random, x, z);
        }

        public Piece(StructureManager manager, CompoundTag tag) {
            super(OmegaStructurePieces.ICE_ISLAND, manager, tag);
        }
    }

    @Override
    public List<SpawnSettings.SpawnEntry> getMonsterSpawns() {
        return super.getMonsterSpawns();
    }
}
