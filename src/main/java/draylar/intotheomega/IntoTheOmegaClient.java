package draylar.intotheomega;

import draylar.intotheomega.api.HandheldModelRegistry;
import draylar.intotheomega.api.StatueRegistry;
import draylar.intotheomega.client.StatueBlockEntityRenderer;
import draylar.intotheomega.registry.OmegaBlocks;
import draylar.intotheomega.registry.OmegaEntities;
import draylar.intotheomega.registry.OmegaItems;
import draylar.intotheomega.registry.OmegaKeys;
import draylar.intotheomega.ui.ConquestForgeScreen;
import net.devtech.arrp.api.RRPCallback;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.blockstate.JState;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.EnderDragonEntityRenderer;
import net.minecraft.client.render.entity.model.EndermanEntityModel;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class IntoTheOmegaClient implements ClientModInitializer {

    public static final RuntimeResourcePack RESOURCE_PACK = RuntimeResourcePack.create("intotheomega:resource_pack");

    @Override
    public void onInitializeClient() {
        OmegaKeys.init();

        // screens
        ScreenRegistry.register(IntoTheOmega.CF_SCREEN_HANDLER, ConquestForgeScreen::new);

        // entity renderers
//        EntityRendererRegistry.INSTANCE.register(OmegaEntities.TRUE_EYE_OF_ENDER, (dispatcher, context) -> new TrueEyeOfEntityRenderer(dispatcher));
//        EntityRendererRegistry.INSTANCE.register(OmegaEntities.OMEGA_GOD, (dispatcher, context) -> new OmegaGodEntityRenderer(dispatcher));

        // block render layers
        BlockRenderLayerMap.INSTANCE.putBlock(OmegaBlocks.OBSIDIAN_GLASS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(OmegaBlocks.OMEGA_GLASS, RenderLayer.getCutout());

        // bers
        BlockEntityRendererRegistry.INSTANCE.register(OmegaEntities.STATUE, StatueBlockEntityRenderer::new);

        // statues
        StatueRegistry.register(OmegaBlocks.ENDER_DRAGON_STATUE, StatueRegistry.StatueData.of(EntityType.ENDER_DRAGON, () -> {
            EnderDragonEntityRenderer.DragonEntityModel model = new EnderDragonEntityRenderer.DragonEntityModel();
            EnderDragonEntity dragon = new EnderDragonEntity(EntityType.ENDER_DRAGON, MinecraftClient.getInstance().world);
            model.animateModel(dragon, 0, 0, 0);

            return model;
        }, .25f));

        StatueRegistry.register(OmegaBlocks.ENDERMAN_STATUE, StatueRegistry.StatueData.of(EntityType.ENDERMAN, () -> new EndermanEntityModel<>(0), .5f));

        // Register 16x variants of 32x tools for inventories
        HandheldModelRegistry.registerWithVariant(OmegaItems.INANIS, new ModelIdentifier("intotheomega:inanis#custom"), IntoTheOmega.id("item/inanis_gui"));

        // register rrp
        RRPCallback.EVENT.register(a -> a.add(RESOURCE_PACK));
    }


    public static ModelIdentifier modelID(String name) {
        return new ModelIdentifier("intotheomega", name);
    }
}
