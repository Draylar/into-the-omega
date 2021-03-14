package draylar.intotheomega.registry;

import draylar.intotheomega.IntoTheOmega;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

public class OmegaSurfaceBuilders {

    public static final ConfiguredSurfaceBuilder<TernarySurfaceConfig> OBSIDIAN_SURFACE_BUILDER = SurfaceBuilder.DEFAULT
            .withConfig(new TernarySurfaceConfig(
                    Blocks.OBSIDIAN.getDefaultState(),
                    Blocks.OBSIDIAN.getDefaultState(),
                    Blocks.END_STONE.getDefaultState()));

    private OmegaSurfaceBuilders() {
        // no-op
    }

    public static void init() {
        Registry.register(BuiltinRegistries.CONFIGURED_SURFACE_BUILDER, IntoTheOmega.id("obsidian"), OBSIDIAN_SURFACE_BUILDER);
    }
}
