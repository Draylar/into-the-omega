package draylar.intotheomega.registry.client;

import draylar.intotheomega.registry.OmegaBlocks;
import draylar.intotheomega.registry.OmegaFluids;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

public class OmegaBlockLayers {

    public static void initialize() {
        BlockRenderLayerMap.INSTANCE.putBlock(OmegaBlocks.OBSIDIAN_GLASS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(OmegaBlocks.OMEGA_GLASS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(OmegaBlocks.OBSIDISHROOM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(OmegaBlocks.ENDERSHROOM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(OmegaBlocks.VOID_MATRIX_SPAWN_BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(OmegaBlocks.ENIGMA_STAND, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(OmegaBlocks.THORN_AIR, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(OmegaBlocks.DARK_SAKURA_LEAF_PILE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(OmegaBlocks.GALAXY_FURNACE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(OmegaBlocks.SLERN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(OmegaBlocks.RICH_SLERN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(OmegaBlocks.OMEGA_SLIME_FLUID, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putFluid(OmegaFluids.OMEGA_SLIME_FLOWING, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putFluid(OmegaFluids.OMEGA_SLIME_STILL, RenderLayer.getTranslucent());
    }
}
