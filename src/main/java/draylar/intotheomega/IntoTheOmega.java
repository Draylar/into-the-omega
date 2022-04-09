package draylar.intotheomega;

import draylar.intotheomega.command.DevelopmentSpawnableCommand;
import draylar.intotheomega.command.EndCommand;
import draylar.intotheomega.command.GeneratePillarCommand;
import draylar.intotheomega.command.GeneratePortalCommand;
import draylar.intotheomega.mixin.SimpleRegistryAccessor;
import draylar.intotheomega.network.ServerNetworking;
import draylar.intotheomega.registry.*;
import draylar.intotheomega.ui.ConquestForgeScreenHandler;
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
import net.minecraft.loot.LootPool;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import software.bernie.geckolib3.GeckoLib;

public class IntoTheOmega implements ModInitializer {

    public static final String MODID = "intotheomega";
    public static final ItemGroup GROUP = FabricItemGroupBuilder.build(id("group"), () -> new ItemStack(OmegaItems.OMEGA_CRYSTAL));
    public static final String OMEGA = "Ω";
    public static final ScreenHandlerType<ConquestForgeScreenHandler> CF_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(id("conquest_forge"), ConquestForgeScreenHandler::new);

    @Override
    public void onInitialize() {
        GeckoLib.initialize();

        OmegaEnchantments.init();
        OmegaBlocks.init();
        OmegaItems.init();
        OmegaEntities.init();
        OmegaBiomes.init();
        OmegaWorld.init();
//        OmegaSurfaceBuilders.init();
        OmegaConfiguredFeatures.init();
        OmegaStructurePieces.init();
        OmegaPlacedFeatures.init();
        OmegaTags.init();
        OmegaParticles.init();
        OmegaServerPackets.init();
        OmegaEventHandlers.init();
        ServerNetworking.init();
        OmegaStatusEffects.init();

        // commands - // TODO: new registry class
        GeneratePillarCommand.initialize();
        GeneratePortalCommand.initialize();
        if(FabricLoader.getInstance().isDevelopmentEnvironment()) {
            CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
                EndCommand.register(dispatcher.getRoot());
            });
        }

        registerDragonLootAppender();

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
            if(((SimpleRegistryAccessor) Registry.ENCHANTMENT).getIdToEntry().containsKey(potentialOmegaID)) {
                return Registry.ENCHANTMENT.get(potentialOmegaID);
            }
        }

        return null;
    }

    public static void registerDragonLootAppender() {
        LootTableLoadingCallback.EVENT.register((resourceManager, lootManager, identifier, supplier, lootTableSetter) -> {
            if(identifier.toString().equals("minecraft:entities/ender_dragon")) {
                LootPool artifact = LootPool.builder()
                        .with(ItemEntry.builder(OmegaItems.CRYSTALIA))
                        .with(ItemEntry.builder(OmegaItems.DRAGON_EYE))
                        .with(ItemEntry.builder(OmegaItems.SEVENTH_PILLAR))
                        .with(ItemEntry.builder(OmegaItems.INANIS))
                        .with(ItemEntry.builder(OmegaItems.HORIZON))
                        .rolls(ConstantLootNumberProvider.create(1))
                        .build();

                LootPool scales = LootPool.builder()
                        .with(ItemEntry.builder(OmegaItems.DRAGON_SCALE).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(10F, 20F))))
                        .rolls(ConstantLootNumberProvider.create(1))
                        .build();

                supplier.withPool(artifact);
                supplier.withPool(scales);
            }
        });
    }
}
