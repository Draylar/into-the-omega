package draylar.intotheomega.registry.world;

import draylar.intotheomega.IntoTheOmega;
import net.minecraft.structure.StructureSet;
import net.minecraft.structure.StructureSets;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.chunk.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.gen.chunk.placement.SpreadType;
import net.minecraft.world.gen.feature.ConfiguredStructureFeatures;

import java.util.List;

public class OmegaStructureSets {

    public static final RegistryEntry<StructureSet> CHORUS_MONUMENTS =
            StructureSets.register(
                    key("chorus_monuments"),
                    OmegaConfiguredStructureFeatures.SMALL_CHORUS_MONUMENT,
                    new RandomSpreadStructurePlacement(24, 16, SpreadType.LINEAR, 581572764));

    public static final RegistryEntry<StructureSet> MATRIX_PEDESTAL =
            StructureSets.register(
                    key("matrix_pedestal"),
                    OmegaConfiguredStructureFeatures.MATRIX_PEDESTAL,
                    new RandomSpreadStructurePlacement(20, 11, SpreadType.TRIANGULAR, 5145242));

    public static final RegistryEntry<StructureSet> PHANTOM_TOWER =
            StructureSets.register(
                    key("phantom_tower"),
                    new StructureSet(List.of(
                            StructureSet.createEntry(OmegaConfiguredStructureFeatures.SMALL_PHANTOM_TOWER),
                            StructureSet.createEntry(OmegaConfiguredStructureFeatures.MEDIUM_PHANTOM_TOWER)
                    ), new RandomSpreadStructurePlacement(24, 16, SpreadType.TRIANGULAR, 5145242)));

    public static final RegistryEntry<StructureSet> TEST_SF =
            StructureSets.register(
                    key("test_sf"),
                    new StructureSet(List.of(
                            StructureSet.createEntry(OmegaConfiguredStructureFeatures.TEST_SF)
                    ), new RandomSpreadStructurePlacement(10, 9, SpreadType.TRIANGULAR, 1234554)));

    public static final RegistryEntry<StructureSet> OMEGA_SLIME_SPIRAL =
            StructureSets.register(
                    key("omega_slime_spiral"),
                    new StructureSet(List.of(
                            StructureSet.createEntry(OmegaConfiguredStructureFeatures.OMEGA_SLIME_SPIRAL)
                    ), new RandomSpreadStructurePlacement(25, 20, SpreadType.TRIANGULAR, 421414)));

    public static final RegistryEntry<StructureSet> SLIME_TENDRIL =
            StructureSets.register(
                    key("slime_tendril"),
                    new StructureSet(List.of(
                            StructureSet.createEntry(OmegaConfiguredStructureFeatures.SLIME_TENDRIL)
                    ), new RandomSpreadStructurePlacement(20, 15, SpreadType.LINEAR, 31412145)));

    public static final RegistryEntry<StructureSet> SLIME_CEILING =
            StructureSets.register(
                    key("slime_ceiling"),
                    new StructureSet(List.of(
                            StructureSet.createEntry(OmegaConfiguredStructureFeatures.SLIME_CEILING)
                    ), new RandomSpreadStructurePlacement(20, 15, SpreadType.LINEAR, 87011345)));


    private static RegistryKey<StructureSet> key(String id) {
        return RegistryKey.of(Registry.STRUCTURE_SET_KEY, IntoTheOmega.id(id));
    }

    public static void init() {

    }
}
