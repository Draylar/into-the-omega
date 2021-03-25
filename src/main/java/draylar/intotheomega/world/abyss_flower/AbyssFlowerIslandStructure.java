package draylar.intotheomega.world.abyss_flower;

import com.mojang.serialization.Codec;
import draylar.intotheomega.registry.OmegaStructurePieces;
import draylar.intotheomega.registry.OmegaWorld;
import draylar.intotheomega.world.api.SiftingStructureGenerator;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.structure.StructureManager;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.StructureConfig;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class AbyssFlowerIslandStructure extends StructureFeature<DefaultFeatureConfig> {

    public AbyssFlowerIslandStructure(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public StructureStartFactory<DefaultFeatureConfig> getStructureStartFactory() {
        return AbyssFlowerIslandStructureStart::new;
    }

    public static class Piece extends SiftingStructureGenerator {

        public Piece(StructureManager manager, CompoundTag tag) {
            super(OmegaStructurePieces.ABYSS_FLOWER_ISLAND, manager, tag);
        }
    }
}
