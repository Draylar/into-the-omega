package draylar.intotheomega.world.ice_island;

import com.mojang.serialization.Codec;
import draylar.intotheomega.registry.OmegaEntities;
import draylar.intotheomega.registry.OmegaStructurePieces;
import draylar.intotheomega.world.api.BaseIslandStructure;
import draylar.intotheomega.world.api.SiftingStructureGenerator;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.structure.StructureManager;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;

import java.util.Arrays;
import java.util.List;

public class IceIslandStructure extends BaseIslandStructure {

    private static final List<SpawnSettings.SpawnEntry> MOB_SPAWNS = Arrays.asList(
            new SpawnSettings.SpawnEntry(OmegaEntities.FROSTED_ENDERMAN, 10, 1, 4),
            new SpawnSettings.SpawnEntry(OmegaEntities.FROSTED_EYE, 10, 1, 2));

    public IceIslandStructure(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public String getId() {
        return "ice_island";
    }

    @Override
    public StructureStartFactory<DefaultFeatureConfig> getStructureStartFactory() {
        return IceIslandStructureStart::new;
    }

    @Override
    public List<SpawnSettings.SpawnEntry> getMonsterSpawns() {
        return MOB_SPAWNS;
    }

    public static class Piece extends SiftingStructureGenerator {

        public Piece(StructureManager manager, CompoundTag tag) {
            super(OmegaStructurePieces.ICE_ISLAND, manager, tag);
        }
    }
}
