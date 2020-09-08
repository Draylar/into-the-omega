package draylar.intotheomega;

import dev.emi.trinkets.api.SlotGroups;
import dev.emi.trinkets.api.Slots;
import dev.emi.trinkets.api.TrinketSlots;
import draylar.intotheomega.mixin.SimpleRegistryAccessor;
import draylar.intotheomega.registry.*;
import draylar.intotheomega.ui.ConquestForgeScreenHandler;
import draylar.intotheomega.world.OmegaCrystalOreFeature;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.ConstantLootTableRange;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.UniformLootTableRange;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

public class IntoTheOmega implements ModInitializer {

    public static final String MODID = "intotheomega";
    public static final ItemGroup GROUP = FabricItemGroupBuilder.build(id("group"), () -> new ItemStack(OmegaItems.OMEGA_CRYSTAL));
    public static final String OMEGA = "Ω";
    public static final ScreenHandlerType<ConquestForgeScreenHandler> CF_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(id("conquest_forge"), ConquestForgeScreenHandler::new);
    public static final EntityGroup END_CREATURE = new EntityGroup();

    public static final Feature<DefaultFeatureConfig> OMEGA_ORE_FEATURE = Registry.register(Registry.FEATURE, id("ore"), new OmegaCrystalOreFeature(DefaultFeatureConfig.CODEC));

    @Override
    public void onInitialize() {
        OmegaEnchantments.init();
        OmegaBlocks.init();
        OmegaItems.init();
        OmegaEntities.init();
        OmegaWorld.init();
        OmegaTags.init();
        OmegaParticles.init();
        OmegaPacketHandlers.init();

        registerDragonLootAppender();

        TrinketSlots.addSlot(SlotGroups.HAND, Slots.RING, new Identifier("trinkets", "textures/item/empty_trinket_slot_ring.png"));
        TrinketSlots.addSlot(SlotGroups.LEGS, Slots.BELT, new Identifier("trinkets", "textures/item/empty_trinket_slot_belt.png"));
        TrinketSlots.addSlot(SlotGroups.CHEST, Slots.CAPE, new Identifier("trinkets", "textures/item/empty_trinket_slot_cape.png"));
        TrinketSlots.addSlot(SlotGroups.HEAD, "eye", new Identifier("intotheomega", "textures/item/empty_trinket_slot_eye.png"));
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
                        .rolls(ConstantLootTableRange.create(1))
                        .build();

                LootPool scales = LootPool.builder()
                        .with(ItemEntry.builder(OmegaItems.DRAGON_SCALE).apply(SetCountLootFunction.builder(UniformLootTableRange.between(10F, 20F))))
                        .rolls(ConstantLootTableRange.create(1))
                        .build();

                supplier.withPool(artifact);
                supplier.withPool(scales);
            }
        });
    }
}
