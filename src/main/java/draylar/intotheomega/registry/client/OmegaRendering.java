package draylar.intotheomega.registry.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexFormats;

@Environment(EnvType.CLIENT)
public class OmegaRendering {

    public static final RenderLayer SOLID = RenderLayer.of(
            "solid",
            VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL,
            7,
            2097152,
            true,
            false,
            RenderLayer.MultiPhaseParameters.builder().depthTest(RenderPhase.ALWAYS_DEPTH_TEST).shadeModel(RenderPhase.SMOOTH_SHADE_MODEL).lightmap(RenderPhase.ENABLE_LIGHTMAP).texture(RenderPhase.MIPMAP_BLOCK_ATLAS_TEXTURE).build(true));

    public static void init() {

    }

    private OmegaRendering() {
        // NO-OP
    }
}
