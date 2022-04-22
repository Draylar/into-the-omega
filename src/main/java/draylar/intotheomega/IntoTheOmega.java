package draylar.intotheomega;

import draylar.intotheomega.command.DevelopmentSpawnableCommand;
import draylar.intotheomega.command.EndCommand;
import draylar.intotheomega.command.GeneratePillarCommand;
import draylar.intotheomega.command.GeneratePortalCommand;
import draylar.intotheomega.impl.event.server.DragonLootTableHandler;
import draylar.intotheomega.mixin.ChunkGeneratorSettingsAccessor;
import draylar.intotheomega.network.ServerNetworking;
import draylar.intotheomega.registry.*;
import draylar.intotheomega.registry.world.*;
import draylar.intotheomega.ui.ConquestForgeScreenHandler;
import draylar.intotheomega.world.OmegaSurfaceRules;
import draylar.intotheomega.world.feature.DarkSakuraTreeFeature;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.geckolib3.GeckoLib;

public class IntoTheOmega implements ModInitializer {

    public static final String MODID = "intotheomega";
    public static final ItemGroup GROUP = FabricItemGroupBuilder.build(id("group"), () -> new ItemStack(OmegaItems.OMEGA_CRYSTAL));
    public static final String OMEGA = "Ω";
    public static final ScreenHandlerType<ConquestForgeScreenHandler> CF_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(id("conquest_forge"), ConquestForgeScreenHandler::new);
    public static final Logger LOGGER = LoggerFactory.getLogger("modid");

    @Override
    public void onInitialize() {
        GeckoLib.initialize();

        OmegaEnchantments.init();
        OmegaBlocks.init();
        OmegaItems.init();
        OmegaEntities.init();
        OmegaBiomes.init();
        OmegaWorld.init();
        OmegaConfiguredFeatures.init();
        OmegaStructurePieces.init();
        OmegaPlacedFeatures.init();
        OmegaTags.init();
        OmegaParticles.init();
        OmegaServerPackets.init();
        OmegaEventHandlers.init();
        ServerNetworking.init();
        OmegaStatusEffects.init();
        OmegaStructureFeatures.init();
        OmegaConfiguredStructureFeatures.init();
        OmegaStructureSets.init();
        OmegaBiomeTags.init();
        OmegaNoiseKeys.init();

        // This looks horrific, but the class we'd normally return from is loaded before Fabric hooks into ID syncing for blocks.
        // If we return our own blocks any sooner, the int ID is saved as -1, which fails when it is sent to clients.
        ((ChunkGeneratorSettingsAccessor) (Object) BuiltinRegistries.CHUNK_GENERATOR_SETTINGS.get(ChunkGeneratorSettings.END)).setSurfaceRule(OmegaSurfaceRules.create());

        // commands - // TODO: new registry class
        GeneratePillarCommand.initialize();
        GeneratePortalCommand.initialize();
        if(FabricLoader.getInstance().isDevelopmentEnvironment()) {
            CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
                EndCommand.register(dispatcher.getRoot());
            });
        }

        LootTableLoadingCallback.EVENT.register(new DragonLootTableHandler());

//        TheEndBiomes.addHighlandsBiome(OmegaBiomes.SHALLOWS_KEY, 50);
//        TheEndBiomes.addMidlandsBiome(OmegaBiomes.SHALLOWS_KEY, OmegaBiomes.SHALLOWS_KEY, 1000);
//        TheEndBiomes.addBarrensBiome(OmegaBiomes.SHALLOWS_KEY, OmegaBiomes.SHALLOWS_KEY, 1000);

        DevelopmentSpawnableCommand.initialize();
        DevelopmentSpawnableCommand.registerSpawnable("dark_sakura_tree", new DarkSakuraTreeFeature());
    }

    public static Identifier id(String name) {
        return new Identifier("intotheomega", name);
    }

    public static Enchantment getOmegaVariant(Enchantment enchantment) {
        Identifier testID = Registry.ENCHANTMENT.getId(enchantment);

        if(testID != null) {
            Identifier potentialOmegaID = IntoTheOmega.id(String.format("omega_%s", testID.getPath()));
            if(Registry.ENCHANTMENT.containsId(potentialOmegaID)) {
                return Registry.ENCHANTMENT.get(potentialOmegaID);
            }
        }

        return null;
    }
}
