package draylar.intotheomega.registry.client;

import draylar.intotheomega.mixin.access.RenderLayerAccessor;
import net.minecraft.client.render.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.function.BiFunction;
import java.util.function.Supplier;

public class OmegaRenderLayers {

    public static final RenderLayer SOLID = RenderLayerAccessor.of(
            "solid",
            VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL,
            VertexFormat.DrawMode.QUADS,
            2097152,
            true,
            false,
            RenderLayer.MultiPhaseParameters.builder().depthTest(RenderPhase.ALWAYS_DEPTH_TEST)
//                    .shadeModel(RenderPhase.SMOOTH_SHADE_MODEL)
                    .lightmap(RenderPhase.ENABLE_LIGHTMAP).texture(RenderPhase.MIPMAP_BLOCK_ATLAS_TEXTURE).build(true));


    public static RenderLayer getChilledVoidArmorLayer(Identifier texture) {
        RenderLayer.MultiPhaseParameters multiPhaseParameters = RenderLayer.MultiPhaseParameters
                .builder()
                .texture(new RenderPhase.Texture(texture, false, false))
                .transparency(RenderPhase.NO_TRANSPARENCY)
//                .diffuseLighting(RenderPhase.ENABLE_DIFFUSE_LIGHTING)
//                .alpha(RenderPhase.ONE_TENTH_ALPHA)
                .cull(RenderPhase.DISABLE_CULLING)
                .lightmap(RenderPhase.ENABLE_LIGHTMAP)
                .overlay(RenderPhase.ENABLE_OVERLAY_COLOR)
                .layering(RenderPhase.VIEW_OFFSET_Z_LAYERING)
                .build(true);

        return RenderLayerAccessor.of("chilled_void_armor", VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL, VertexFormat.DrawMode.QUADS, 256, true, false, multiPhaseParameters);
    }

    public static RenderLayer getGalaxyStar(Identifier texture) {
        RenderLayer.MultiPhaseParameters multiPhaseParameters = RenderLayer.MultiPhaseParameters
                .builder()
                .texture(new RenderPhase.Texture(texture, false, false))
//                .diffuseLighting(RenderPhase.ENABLE_DIFFUSE_LIGHTING)
//                .alpha(RenderPhase.ONE_TENTH_ALPHA)
                .cull(RenderPhase.DISABLE_CULLING)
                .depthTest(RenderPhase.ALWAYS_DEPTH_TEST)
                .lightmap(RenderPhase.ENABLE_LIGHTMAP)
                .overlay(RenderPhase.ENABLE_OVERLAY_COLOR)
                .build(false);

        return RenderLayerAccessor.of("galaxy_star", VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL, VertexFormat.DrawMode.QUADS, 256, true, true, multiPhaseParameters);
    }

    public static RenderLayer getGalaxy(Identifier texture) {
        RenderLayer.MultiPhaseParameters multiPhaseParameters = RenderLayer.MultiPhaseParameters
                .builder()
                .texture(new RenderPhase.Texture(texture, false, false))
//                .diffuseLighting(RenderPhase.ENABLE_DIFFUSE_LIGHTING)
//                .alpha(RenderPhase.ONE_TENTH_ALPHA)
                .cull(RenderPhase.DISABLE_CULLING)
                .depthTest(RenderPhase.ALWAYS_DEPTH_TEST)
                .lightmap(RenderPhase.ENABLE_LIGHTMAP)
                .overlay(RenderPhase.ENABLE_OVERLAY_COLOR)
                .build(false);

        return RenderLayerAccessor.of("galaxy", VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL, VertexFormat.DrawMode.QUADS, 256, true, true, multiPhaseParameters);
    }

    private static final BiFunction<Identifier, Boolean, RenderLayer> ENTITY_TRANSLUCENT_FOGLESS = Util.memoize((texture, affectsOutline) -> {
        RenderLayer.MultiPhaseParameters multiPhaseParameters = RenderLayer.MultiPhaseParameters.builder()
                .shader(new RenderPhase.Shader(OmegaShaders.ENTITY_TRANSLUCENT_FOGLESS_SHADER::getShader))
                .texture(new RenderPhase.Texture(texture, false, false))
                .transparency(RenderPhase.TRANSLUCENT_TRANSPARENCY)
                .cull(RenderPhase.DISABLE_CULLING)
                .lightmap(RenderPhase.ENABLE_LIGHTMAP)
                .overlay(RenderPhase.ENABLE_OVERLAY_COLOR)
                .build(affectsOutline);

        return RenderLayerAccessor.of("entity_translucent_fogless", VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL, VertexFormat.DrawMode.QUADS, 256, true, true, multiPhaseParameters);
    });

    private static final BiFunction<Identifier, Boolean, RenderLayer> ENTITY_TRUE_TRANSLUCENT = Util.memoize((texture, affectsOutline) -> {
        RenderLayer.MultiPhaseParameters multiPhaseParameters = RenderLayer.MultiPhaseParameters.builder()
                .shader(new RenderPhase.Shader(OmegaShaders.ENTITY_TRANSLUCENT_TRUE_TRANSLUCENT_SHADER::getShader))
                .texture(new RenderPhase.Texture(texture, false, false))
                .transparency(RenderPhase.TRANSLUCENT_TRANSPARENCY)
                .cull(RenderPhase.DISABLE_CULLING)
                .lightmap(RenderPhase.ENABLE_LIGHTMAP)
                .overlay(RenderPhase.ENABLE_OVERLAY_COLOR)
                .build(affectsOutline);

        return RenderLayerAccessor.of("entity_true_translucent", VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL, VertexFormat.DrawMode.QUADS, 256, true, true, multiPhaseParameters);
    });

    public static RenderLayer getEntityTranslucentFogless(Identifier texture, boolean affectsOutline) {
        return ENTITY_TRANSLUCENT_FOGLESS.apply(texture, affectsOutline);
    }

    public static RenderLayer getEntityTrueTranslucent(Identifier texture) {
        return ENTITY_TRUE_TRANSLUCENT.apply(texture, false);
    }

    public static RenderLayer entityShader(Identifier texture, Supplier<Shader> shader) {
        var multiPhaseParameters = RenderLayer.MultiPhaseParameters.builder()
                .shader(new RenderPhase.Shader(shader))
                .texture(new RenderPhase.Texture(texture, false, false))
                .transparency(RenderPhase.TRANSLUCENT_TRANSPARENCY)
                .cull(RenderPhase.DISABLE_CULLING)
                .lightmap(RenderPhase.ENABLE_LIGHTMAP)
                .overlay(RenderPhase.ENABLE_OVERLAY_COLOR)
                .build(true);

        return RenderLayerAccessor.of("entity_true_translucent", VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL, VertexFormat.DrawMode.QUADS, 256, true, true, multiPhaseParameters);
    }
}
