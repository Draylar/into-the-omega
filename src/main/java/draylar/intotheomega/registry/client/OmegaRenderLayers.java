package draylar.intotheomega.registry.client;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;

public class OmegaRenderLayers {

    public static final RenderLayer SOLID = RenderLayer.of(
            "solid",
            VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL,
            7,
            2097152,
            true,
            false,
            RenderLayer.MultiPhaseParameters.builder().depthTest(RenderPhase.ALWAYS_DEPTH_TEST).shadeModel(RenderPhase.SMOOTH_SHADE_MODEL).lightmap(RenderPhase.ENABLE_LIGHTMAP).texture(RenderPhase.MIPMAP_BLOCK_ATLAS_TEXTURE).build(true));


    public static RenderLayer getChilledVoidArmorLayer(Identifier texture) {
        RenderLayer.MultiPhaseParameters multiPhaseParameters = RenderLayer.MultiPhaseParameters
                .builder()
                .texture(new RenderPhase.Texture(texture, false, false))
                .transparency(RenderPhase.NO_TRANSPARENCY)
                .diffuseLighting(RenderPhase.ENABLE_DIFFUSE_LIGHTING)
                .alpha(RenderPhase.ONE_TENTH_ALPHA)
                .cull(RenderPhase.DISABLE_CULLING)
                .lightmap(RenderPhase.ENABLE_LIGHTMAP)
                .overlay(RenderPhase.ENABLE_OVERLAY_COLOR)
                .layering(RenderPhase.VIEW_OFFSET_Z_LAYERING)
                .build(true);

        return RenderLayer.of("chilled_void_armor", VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL, 7, 256, true, false, multiPhaseParameters);
    }

    public static RenderLayer getGalaxyStar(Identifier texture) {
        RenderLayer.MultiPhaseParameters multiPhaseParameters = RenderLayer.MultiPhaseParameters
                .builder()
                .texture(new RenderPhase.Texture(texture, false, false))
                .diffuseLighting(RenderPhase.ENABLE_DIFFUSE_LIGHTING)
                .alpha(RenderPhase.ONE_TENTH_ALPHA)
                .cull(RenderPhase.DISABLE_CULLING)
                .depthTest(RenderPhase.ALWAYS_DEPTH_TEST)
                .lightmap(RenderPhase.ENABLE_LIGHTMAP)
                .overlay(RenderPhase.ENABLE_OVERLAY_COLOR)
                .build(false);

        return RenderLayer.of("galaxy_star", VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL, 7, 256, true, true, multiPhaseParameters);
    }

    public static RenderLayer getGalaxy(Identifier texture) {
        RenderLayer.MultiPhaseParameters multiPhaseParameters = RenderLayer.MultiPhaseParameters
                .builder()
                .texture(new RenderPhase.Texture(texture, false, false))
                .diffuseLighting(RenderPhase.ENABLE_DIFFUSE_LIGHTING)
                .alpha(RenderPhase.ONE_TENTH_ALPHA)
                .cull(RenderPhase.DISABLE_CULLING)
                .depthTest(RenderPhase.ALWAYS_DEPTH_TEST)
                .lightmap(RenderPhase.ENABLE_LIGHTMAP)
                .overlay(RenderPhase.ENABLE_OVERLAY_COLOR)
                .build(false);

        return RenderLayer.of("galaxy", VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL, 7, 256, true, true, multiPhaseParameters);
    }
}
