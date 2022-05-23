package draylar.intotheomega;

import dev.emi.trinkets.api.TrinketsApi;
import draylar.intotheomega.command.*;
import draylar.intotheomega.config.ITOConfig;
import draylar.intotheomega.impl.ServerPlayerMirrorExtensions;
import draylar.intotheomega.impl.event.server.DragonLootTableHandler;
import draylar.intotheomega.mixin.ChunkGeneratorSettingsAccessor;
import draylar.intotheomega.network.ServerNetworking;
import draylar.intotheomega.registry.*;
import draylar.intotheomega.registry.world.*;
import draylar.intotheomega.ui.ConquestForgeScreenHandler;
import draylar.intotheomega.world.NovaZoneGenerator;
import draylar.intotheomega.world.OmegaSurfaceRules;
import draylar.intotheomega.world.feature.DarkSakuraTreeFeature;
import draylar.omegaconfig.OmegaConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.geckolib3.GeckoLib;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class IntoTheOmega implements ModInitializer {

    public static final String MODID = "intotheomega";
    public static final ItemGroup GROUP = FabricItemGroupBuilder.build(id("group"), () -> new ItemStack(OmegaItems.OMEGA_CRYSTAL));
    public static final String OMEGA = "Ω";
    public static final ScreenHandlerType<ConquestForgeScreenHandler> CF_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(id("conquest_forge"), ConquestForgeScreenHandler::new);
    public static final Logger LOGGER = LoggerFactory.getLogger("modid");
    public static final ITOConfig CONFIG = OmegaConfig.register(ITOConfig.class);

    @Override
    public void onInitialize() {
        GeckoLib.initialize();

        OmegaEntities.init();
        OmegaBlockEntities.init();
        OmegaEnchantments.init();
        OmegaBlocks.init();
        OmegaItems.init();
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
        OmegaEntityAttributes.init();

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
        DevelopmentSpawnableCommand.registerSpawnable("nova_zone", new NovaZoneGenerator(null, null, null));

        if(FabricLoader.getInstance().isDevelopmentEnvironment()) {
            CommandRegistrationCallback.EVENT.register(new UtilCommands());
        }

        if(FabricLoader.getInstance().isDevelopmentEnvironment()) {
            PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) -> {
                ServerPlayerMirrorExtensions mirror = (ServerPlayerMirrorExtensions) player;
                final BlockPos center = mirror.getOrigin();
                if(center != null) {
                    if(Math.sqrt(Math.pow(center.getX() - pos.getX(), 2) + Math.pow(center.getZ() - pos.getZ(), 2)) <= 64) {
                        BlockPos offset = pos.subtract(mirror.getOrigin());
                        int x = offset.getX();
                        int z = offset.getZ();

                        world.breakBlock(new BlockPos(center.getX() + x, pos.getY(), center.getZ() + z), false);
                        world.breakBlock(new BlockPos(center.getX() + z, pos.getY(), center.getZ() - x), false);
                        world.breakBlock(new BlockPos(center.getX() - x, pos.getY(), center.getZ() - z), false);
                        world.breakBlock(new BlockPos(center.getX() - z, pos.getY(), center.getZ() + x), false);
                    }
                }
            });
        }
    }

    public static void mirrorPlacement(PlayerEntity player, ItemStack stack, Direction side, World world, BlockPos center, BlockPos pos, Block block) {
        if(Math.sqrt(Math.pow(center.getX() - pos.getX(), 2) + Math.pow(center.getZ() - pos.getZ(), 2)) <= 64) {
            BlockPos offset = pos.subtract(center);
            int x = offset.getX();
            int z = offset.getZ();

            Vec3d origin = new Vec3d(center.getX() + x, pos.getY(), center.getZ() + z);

            BlockState state = block.getPlacementState(new ItemPlacementContext(player, Hand.MAIN_HAND, stack, new BlockHitResult(origin, side, new BlockPos(origin), false)));

            place(world, player, stack, side, origin, state);
            place(world, player, stack, side, new Vec3d(center.getX() + z, pos.getY(), center.getZ() - x), state.rotate(BlockRotation.COUNTERCLOCKWISE_90));
            place(world, player, stack, side, new Vec3d(center.getX() - x, pos.getY(), center.getZ() - z), state.rotate(BlockRotation.CLOCKWISE_180));
            place(world, player, stack, side, new Vec3d(center.getX() - z, pos.getY(), center.getZ() + x), state.rotate(BlockRotation.CLOCKWISE_90));
        }
    }

    private static void place(World world, PlayerEntity player, ItemStack stack, Direction side, Vec3d origin, BlockState state) {
        BlockPos originBlockPos = new BlockPos(origin);
        world.setBlockState(originBlockPos, state);
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

    public static List<ItemStack> collectListeningItems(PlayerEntity player) {
        Stream<ItemStack> trinketItems = TrinketsApi.getTrinketComponent(player).get().getAllEquipped().stream().map(Pair::getRight);
        List<ItemStack> relevantItems = new ArrayList<>();
        relevantItems.add(player.getEquippedStack(EquipmentSlot.MAINHAND));
        relevantItems.addAll(trinketItems.toList());
        return relevantItems;
    }
}
