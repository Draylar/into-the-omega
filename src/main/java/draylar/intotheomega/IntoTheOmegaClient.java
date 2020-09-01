package draylar.intotheomega;

import draylar.intotheomega.client.TrueEyeOfEntityRenderer;
import draylar.intotheomega.client.og.OmegaGodEntityRenderer;
import draylar.intotheomega.registry.OmegaBlocks;
import draylar.intotheomega.registry.OmegaEntities;
import draylar.intotheomega.ui.ConquestForgeScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.client.render.RenderLayer;

@Environment(EnvType.CLIENT)
public class IntoTheOmegaClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ScreenRegistry.register(IntoTheOmega.CF_SCREEN_HANDLER, ConquestForgeScreen::new);

        // entity renderers
//        EntityRendererRegistry.INSTANCE.register(OmegaEntities.TRUE_EYE_OF_ENDER, (dispatcher, context) -> new TrueEyeOfEntityRenderer(dispatcher));
//        EntityRendererRegistry.INSTANCE.register(OmegaEntities.OMEGA_GOD, (dispatcher, context) -> new OmegaGodEntityRenderer(dispatcher));

        // block render layers
        BlockRenderLayerMap.INSTANCE.putBlock(OmegaBlocks.ENDER_GLASS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(OmegaBlocks.OMEGA_GLASS, RenderLayer.getCutout());
    }
}
