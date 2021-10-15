package draylar.intotheomega.registry;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.world.area.slime.SlimeWastesSurfaceBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.SurfaceConfig;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

public class OmegaSurfaceBuilders {

    public static final ConfiguredSurfaceBuilder<TernarySurfaceConfig> OBSIDIAN_SURFACE_BUILDER = SurfaceBuilder.DEFAULT
            .withConfig(new TernarySurfaceConfig(
                    Blocks.OBSIDIAN.getDefaultState(),
                    Blocks.OBSIDIAN.getDefaultState(),
                    Blocks.END_STONE.getDefaultState()));

    public static final ConfiguredSurfaceBuilder<TernarySurfaceConfig> CHORUS_FOREST_BUILDER = SurfaceBuilder.DEFAULT
            .withConfig(new TernarySurfaceConfig(
                    OmegaBlocks.CHORUS_GRASS.getDefaultState(),
                    Blocks.END_STONE.getDefaultState(),
                    Blocks.END_STONE.getDefaultState()
            ));

    public static final SurfaceBuilder<TernarySurfaceConfig> SLIME_WASTES = register("slime_wastes", new SlimeWastesSurfaceBuilder(TernarySurfaceConfig.CODEC));
    public static final ConfiguredSurfaceBuilder<TernarySurfaceConfig> CONFIGURED_SLIME_WASTES = SLIME_WASTES.withConfig(new TernarySurfaceConfig(Blocks.OBSIDIAN.getDefaultState(), Blocks.OBSIDIAN.getDefaultState(), Blocks.OBSIDIAN.getDefaultState()));


    private OmegaSurfaceBuilders() {
        // no-op
    }

    public static void init() {
        Registry.register(BuiltinRegistries.CONFIGURED_SURFACE_BUILDER, IntoTheOmega.id("obsidian"), OBSIDIAN_SURFACE_BUILDER);
        Registry.register(BuiltinRegistries.CONFIGURED_SURFACE_BUILDER, IntoTheOmega.id("slime_wastes"), CONFIGURED_SLIME_WASTES);
        Registry.register(BuiltinRegistries.CONFIGURED_SURFACE_BUILDER, IntoTheOmega.id("chorus_forest"), CHORUS_FOREST_BUILDER);
    }

    private static <C extends SurfaceConfig, F extends SurfaceBuilder<C>> F register(String id, F surfaceBuilder) {
        return Registry.register(Registry.SURFACE_BUILDER, IntoTheOmega.id(id), surfaceBuilder);
    }
}
