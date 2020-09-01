package draylar.intotheomega.registry;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.mixin.GenerationSettingsAccessor;
import draylar.intotheomega.world.altar.TrueEyeAltarFeature;
import draylar.intotheomega.world.altar.TrueEyeAltarGenerator;
import net.earthcomputer.libstructure.LibStructure;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.chunk.StructureConfig;
import net.minecraft.world.gen.feature.*;

import java.util.ArrayList;

public class OmegaWorld {

    public static final StructureFeature<DefaultFeatureConfig> TRUE_EYE_ALTAR = new TrueEyeAltarFeature(DefaultFeatureConfig.CODEC);
    public static final ConfiguredStructureFeature<DefaultFeatureConfig, ? extends StructureFeature<DefaultFeatureConfig>> CONFIGURED_TRUE_EYE_ALTAR = register("true_eye_altar", TRUE_EYE_ALTAR.configure(new DefaultFeatureConfig()));
    public static final StructurePieceType TRUE_EYE_ALTAR_PIECE = Registry.register(Registry.STRUCTURE_PIECE, IntoTheOmega.id("true_eye_altar"), TrueEyeAltarGenerator.Piece::new);

    public static void init() {
        LibStructure.registerSurfaceAdjustingStructure(IntoTheOmega.id("true_eye_altar"), TRUE_EYE_ALTAR, GenerationStep.Feature.SURFACE_STRUCTURES, new StructureConfig(32, 8, 4003), CONFIGURED_TRUE_EYE_ALTAR);

//        BuiltinRegistries.BIOME.forEach(biome -> {
//            ((GenerationSettingsAccessor) biome.getGenerationSettings()).setStructureFeatures(
//                    new ArrayList<>(biome.getGenerationSettings().getStructureFeatures())
//            );
//
//            biome.getGenerationSettings().getStructureFeatures().add(() -> {
//                return CONFIGURED_TRUE_EYE_ALTAR;
//            });
//        });
    }

    private static <FC extends FeatureConfig, F extends StructureFeature<FC>> ConfiguredStructureFeature<FC, F> register(String id, ConfiguredStructureFeature<FC, F> configuredStructureFeature) {
        return BuiltinRegistries.add(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, IntoTheOmega.id(id), configuredStructureFeature);
    }

    private OmegaWorld() {

        // no-op
    }
}
