package draylar.intotheomega.registry.world;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.mixin.access.StructureFeatureAccessor;
import draylar.intotheomega.world.TestSF;
import draylar.intotheomega.world.structure.*;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class OmegaStructureFeatures {

    public static final SmallChorusMonumentStructure SMALL_CHORUS_MONUMENT = register("small_chorus_monument", new SmallChorusMonumentStructure(), GenerationStep.Feature.SURFACE_STRUCTURES);
    public static final EyeAltarStructure EYE_ALTAR = register("eye_altar", new EyeAltarStructure(DefaultFeatureConfig.CODEC), GenerationStep.Feature.SURFACE_STRUCTURES);
    public static final SmallPhantomTowerStructure SMALL_PHANTOM_TOWER = register("small_phantom_tower", new SmallPhantomTowerStructure(DefaultFeatureConfig.CODEC), GenerationStep.Feature.SURFACE_STRUCTURES);
    public static final MediumPhantomTowerStructure MEDIUM_PHANTOM_TOWER = register("medium_phantom_tower", new MediumPhantomTowerStructure(DefaultFeatureConfig.CODEC), GenerationStep.Feature.SURFACE_STRUCTURES);
    public static final MatrixPedestalStructure MATRIX_PEDESTAL = register("matrix_pedestal", new MatrixPedestalStructure(DefaultFeatureConfig.CODEC), GenerationStep.Feature.SURFACE_STRUCTURES);
    public static final BejeweledDungeonStructure BEJEWELED_DUNGEON = register("bejeweled_dungeon", new BejeweledDungeonStructure(DefaultFeatureConfig.CODEC), GenerationStep.Feature.SURFACE_STRUCTURES);
    public static final TestSF TEST_SF = register("test_sf", new TestSF(DefaultFeatureConfig.CODEC), GenerationStep.Feature.SURFACE_STRUCTURES);
    public static final OmegaSlimeSpiralStructure OMEGA_SLIME_SPIRAL = register("omega_slime_spiral", new OmegaSlimeSpiralStructure(DefaultFeatureConfig.CODEC), GenerationStep.Feature.SURFACE_STRUCTURES);
    public static final SlimeTendrilStructure SLIME_TENDRIL = register("slime_tendril", new SlimeTendrilStructure(DefaultFeatureConfig.CODEC), GenerationStep.Feature.SURFACE_STRUCTURES);
    public static final SlimeCeilingStructure SLIME_CEILING = register("slime_ceiling", new SlimeCeilingStructure(DefaultFeatureConfig.CODEC), GenerationStep.Feature.SURFACE_STRUCTURES);
    public static final SlimeCaveStructure SLIME_CAVE = register("slime_cave", new SlimeCaveStructure(DefaultFeatureConfig.CODEC), GenerationStep.Feature.SURFACE_STRUCTURES);

    private static <SF extends StructureFeature<?>> SF register(String name, SF structureFeature, GenerationStep.Feature step) {
        return StructureFeatureAccessor.register(IntoTheOmega.id(name).toString(), structureFeature, step);
    }

    public static void init() {

    }
}
