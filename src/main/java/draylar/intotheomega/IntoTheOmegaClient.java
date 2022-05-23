package draylar.intotheomega;

import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import draylar.intotheomega.api.HandheldModelRegistry;
import draylar.intotheomega.api.client.ArmorSetDisplayRegistry;
import draylar.intotheomega.api.event.ParticleEvents;
import draylar.intotheomega.api.item.SetBonusProvider;
import draylar.intotheomega.api.skybox.SkyboxManager;
import draylar.intotheomega.client.OmegaSlimeRenderingHandler;
import draylar.intotheomega.client.PhasePadUtils;
import draylar.intotheomega.client.feature.HyperionFeatureRenderer;
import draylar.intotheomega.client.feature.SkinArmorFeatureRenderer;
import draylar.intotheomega.client.feature.WingFeatureRenderer;
import draylar.intotheomega.client.trinket.EyeTrinketRenderer;
import draylar.intotheomega.client.trinket.OrbitalTrinketRenderer;
import draylar.intotheomega.impl.event.client.OmegaParticleFactoryRegistrar;
import draylar.intotheomega.impl.event.client.armor.ChilledVoidArmorDisplayHandler;
import draylar.intotheomega.impl.event.client.color.CrystaliteColorProvider;
import draylar.intotheomega.impl.event.client.color.PhasePadTickHandler;
import draylar.intotheomega.impl.event.client.predicate.BowPullPredicateProvider;
import draylar.intotheomega.impl.event.client.predicate.FrostbusterChargePredicateProvider;
import draylar.intotheomega.impl.event.client.predicate.UsingPredicateProvider;
import draylar.intotheomega.impl.event.client.predicate.VariantSparkModePredicateProvider;
import draylar.intotheomega.item.ice.ChilledVoidArmorItem;
import draylar.intotheomega.network.ClientNetworking;
import draylar.intotheomega.registry.OmegaBlocks;
import draylar.intotheomega.registry.OmegaEntityModelLayers;
import draylar.intotheomega.registry.OmegaItems;
import draylar.intotheomega.registry.OmegaKeys;
import draylar.intotheomega.registry.client.*;
import draylar.intotheomega.ui.ConquestForgeScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.PrioritizedGoal;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class IntoTheOmegaClient implements ClientModInitializer {

    private static final SkyboxManager SKYBOX = new SkyboxManager();
    public static final Map<Integer, List<PrioritizedGoal>> DEVELOPMENT_AI_SYNC = new HashMap<>();
    public static final Map<Integer, Path> DEVELOPMENT_PATH_SYNC = new HashMap<>();
    public static boolean inIceIsland = false;

    @Override
    public void onInitializeClient() {
        OmegaKeys.init();
        OmegaClientPackets.init();
        OmegaRendering.init();
        ClientNetworking.init();
        OmegaClientEventHandlers.init();
        OmegaEntityModelLayers.init();
        HandledScreens.register(IntoTheOmega.CF_SCREEN_HANDLER, ConquestForgeScreen::new);
        OmegaRenderers.init();
        OmegaSlimeRenderingHandler.init();
        OmegaBlockLayers.initialize();
        OmegaItemRenderers.initialize();

        ModelPredicateProviderRegistry.register(OmegaItems.FERLIOUS, new Identifier("pull"), new BowPullPredicateProvider());
        ModelPredicateProviderRegistry.register(OmegaItems.FROSTBUSTER, new Identifier("charge"), new FrostbusterChargePredicateProvider());
        ModelPredicateProviderRegistry.register(OmegaItems.FROSTBUSTER, new Identifier("using"), new UsingPredicateProvider());
        ModelPredicateProviderRegistry.register(OmegaItems.FERLIOUS, new Identifier("pulling"), new UsingPredicateProvider());
        ModelPredicateProviderRegistry.register(OmegaItems.VARIANT_SPARK, new Identifier("mode"), new VariantSparkModePredicateProvider());

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

        ClientTickEvents.START_CLIENT_TICK.register(new PhasePadTickHandler());

        AttackBlockCallback.EVENT.register((playerEntity, world, hand, blockPos, direction) -> {
            if(world.isClient) {
                if(PhasePadUtils.hasHighlight()) {
                    return ActionResult.FAIL;
                }
            }

            return ActionResult.PASS;
        });


        ArmorSetDisplayRegistry.register(ChilledVoidArmorItem.class, new ChilledVoidArmorDisplayHandler());
        ColorProviderRegistry.BLOCK.register(new CrystaliteColorProvider(), OmegaBlocks.CRYSTALITE);
        ParticleEvents.REGISTER_FACTORIES.register(new OmegaParticleFactoryRegistrar());

        TrinketRendererRegistry.registerRenderer(OmegaItems.EBONY_EYE, new EyeTrinketRenderer());
        TrinketRendererRegistry.registerRenderer(OmegaItems.BOUND_EYE, new EyeTrinketRenderer());
        TrinketRendererRegistry.registerRenderer(OmegaItems.DRAGON_EYE, new EyeTrinketRenderer());
        TrinketRendererRegistry.registerRenderer(OmegaItems.SOUL_BOW, new OrbitalTrinketRenderer());
        TrinketRendererRegistry.registerRenderer(OmegaItems.SOUL_TOME, new OrbitalTrinketRenderer());
        TrinketRendererRegistry.registerRenderer(OmegaItems.SOUL_SWORD, new OrbitalTrinketRenderer());
        TrinketRendererRegistry.registerRenderer(OmegaItems.SOUL_SHIELD, new OrbitalTrinketRenderer());

        LivingEntityFeatureRendererRegistrationCallback.EVENT.register((entityType, entityRenderer, registrationHelper, context) -> {
            if(entityType.equals(EntityType.PLAYER)) {
                FeatureRendererContext<LivingEntity, BipedEntityModel<LivingEntity>> playerContext = (FeatureRendererContext<LivingEntity, BipedEntityModel<LivingEntity>>) entityRenderer;
                registrationHelper.register(new HyperionFeatureRenderer(playerContext));
                registrationHelper.register(new WingFeatureRenderer(playerContext));
                registrationHelper.register(new SkinArmorFeatureRenderer((FeatureRendererContext<AbstractClientPlayerEntity, BipedEntityModel<AbstractClientPlayerEntity>>) entityRenderer, context));
            }
        });
    }

    public static ModelIdentifier modelID(String name) {
        return new ModelIdentifier("intotheomega", name);
    }

    public static SkyboxManager getSkyboxManager() {
        return SKYBOX;
    }
}
