package draylar.intotheomega.impl.event.server;

import draylar.intotheomega.registry.OmegaItems;
import net.fabricmc.fabric.api.loot.v1.FabricLootSupplierBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.minecraft.loot.LootManager;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

public class DragonLootTableHandler implements LootTableLoadingCallback {

    @Override
    public void onLootTableLoading(ResourceManager resourceManager, LootManager manager, Identifier id, FabricLootSupplierBuilder supplier, LootTableSetter setter) {
        if(id.toString().equals("minecraft:entities/ender_dragon")) {
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
    }
}
