package draylar.intotheomega;

import draylar.intotheomega.api.HandheldModelRegistry;
import draylar.intotheomega.api.SetBonusProvider;
import draylar.intotheomega.client.OmegaSlimeRenderingHandler;
import draylar.intotheomega.client.PhasePadUtils;
import draylar.intotheomega.client.item.MatriteOrbitalItemRenderer;
import draylar.intotheomega.client.item.NebulaGearItemRenderer;
import draylar.intotheomega.entity.block.PhasePadBlockEntity;
import draylar.intotheomega.network.ClientNetworking;
import draylar.intotheomega.registry.*;
import draylar.intotheomega.registry.client.OmegaClientPackets;
import draylar.intotheomega.registry.client.OmegaRenderers;
import draylar.intotheomega.registry.client.OmegaRendering;
import draylar.intotheomega.ui.ConquestForgeScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.entity.ai.goal.PrioritizedGoal;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.resource.ResourceType;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class IntoTheOmegaClient implements ClientModInitializer {

    public static final Map<Integer, List<PrioritizedGoal>> DEVELOPMENT_AI_SYNC = new HashMap<>();
    public static final Map<Integer, Path> DEVELOPMENT_PATH_SYNC = new HashMap<>();
    public static boolean inIceIsland = false;

    @Override
    public void onInitializeClient() {
        OmegaKeys.init();
        OmegaClientPackets.init();
        OmegaRendering.init();
        ClientNetworking.init();

        // screens
        ScreenRegistry.register(IntoTheOmega.CF_SCREEN_HANDLER, ConquestForgeScreen::new);

        OmegaRenderers.init();

        OmegaSlimeRenderingHandler.init();

        // block render layers
        BlockRenderLayerMap.INSTANCE.putBlock(OmegaBlocks.OBSIDIAN_GLASS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(OmegaBlocks.OMEGA_GLASS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(OmegaBlocks.OBSIDISHROOM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(OmegaBlocks.ENDERSHROOM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(OmegaBlocks.VOID_MATRIX_SPAWN_BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(OmegaBlocks.ENIGMA_STAND, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(OmegaBlocks.THORN_AIR, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(OmegaBlocks.OMEGA_SLIME_FLUID, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putFluid(OmegaFluids.OMEGA_SLIME_FLOWING, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putFluid(OmegaFluids.OMEGA_SLIME_STILL, RenderLayer.getTranslucent());
//        BlockRenderLayerMap.INSTANCE.putBlock(OmegaBlocks.INVISIBLE_DUNGEON_BRICK, RenderLayer.getCutout());

        BuiltinItemRendererRegistry.INSTANCE.register(OmegaItems.MATRITE_ORBITAL, new MatriteOrbitalItemRenderer());
        BuiltinItemRendererRegistry.INSTANCE.register(OmegaItems.NEBULA_GEAR, new NebulaGearItemRenderer());

        FabricModelPredicateProviderRegistry.register(OmegaItems.FERLIOUS, new Identifier("pull"), (itemStack, clientWorld, livingEntity) -> {
            if (livingEntity == null) {
                return 0.0F;
            } else {
                return livingEntity.getActiveItem() != itemStack ? 0.0F : (float)(itemStack.getMaxUseTime() - livingEntity.getItemUseTimeLeft()) / 20.0F;
            }
        });

        FabricModelPredicateProviderRegistry.register(OmegaItems.FERLIOUS, new Identifier("pulling"), (itemStack, clientWorld, livingEntity) -> {
            return livingEntity != null && livingEntity.isUsingItem() && livingEntity.getActiveItem() == itemStack ? 1.0F : 0.0F;
        });

        // Register 16x variants of 32x tools for inventories
        HandheldModelRegistry.registerWithVariant(OmegaItems.INANIS, new ModelIdentifier("intotheomega:inanis#custom"), IntoTheOmega.id("item/inanis_gui"));

        // set bonus tooltips
        ItemTooltipCallback.EVENT.register((itemStack, context, list) -> {
            if(itemStack.getItem() instanceof SetBonusProvider) {
                list.add(new LiteralText(""));
                list.add(new LiteralText("Set Bonus:").formatted(Formatting.GRAY));
                ((SetBonusProvider) itemStack.getItem()).appendSetBonusTooltip(itemStack, MinecraftClient.getInstance().world, list, context);
            }
        });

        ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> {
            out.accept(IntoTheOmega.id("item/nebula_gear_inventory"));
            out.accept(IntoTheOmega.id("item/violet_union_blue"));
            out.accept(IntoTheOmega.id("item/violet_union_pink"));
        });

        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            if(client.world == null) {
                return;
            }

            if(!client.world.getBlockState(client.player.getBlockPos().down()).getBlock().equals(OmegaBlocks.PHASE_PAD)) {
                PhasePadUtils.reset();
            } else {
                PhasePadUtils.stepOn(client.world, client.player.getBlockPos().down());

                Vec3d originPos = client.cameraEntity.getPos().multiply(1, 0, 1).add(0, client.cameraEntity.getEyeY(), 0);
                Vec3d rotationVector = client.player.getRotationVector();

                for(int i = 0; i < 32; i++) {
                    Vec3d offsetPos = originPos.add(rotationVector.multiply(i));
                    BlockPos offsetBlockPos = new BlockPos(offsetPos);

                    if(client.world.getBlockState(offsetBlockPos).getBlock().equals(OmegaBlocks.PHASE_PAD)) {
                        BlockEntity entity = client.world.getBlockEntity(offsetBlockPos);

                        if(entity != null) {
                            ((PhasePadBlockEntity) entity).highlight();
                            PhasePadUtils.setHighlighting(offsetBlockPos);
                        }
                    }
                }
            }
        });

        AttackBlockCallback.EVENT.register((playerEntity, world, hand, blockPos, direction) -> {
            if(world.isClient) {
                if(PhasePadUtils.hasHighlight()) {
                    return ActionResult.FAIL;
                }
            }

            return ActionResult.PASS;
        });

        FabricModelPredicateProviderRegistry.register(OmegaItems.VARIANT_SPARK, new Identifier("mode"), (stack, world, entity) -> {
            return stack.getOrCreateSubTag("Data").getInt("Mode");
        });
    }


    public static ModelIdentifier modelID(String name) {
        return new ModelIdentifier("intotheomega", name);
    }
}
