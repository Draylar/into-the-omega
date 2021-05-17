package draylar.intotheomega.registry.client;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;

public class OmegaRenderLayers {

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
}
