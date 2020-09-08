package draylar.intotheomega.registry;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.block.ConquestForgeBlock;
import draylar.intotheomega.block.OmegaCrystalOreBlock;
import draylar.intotheomega.block.StatueBlock;
import draylar.intotheomega.block.WatchingEyeBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class OmegaBlocks {

    public static final Block OMEGA_BLOCK = register(
            "omega_block",
            new Block(
                    FabricBlockSettings
                            .of(Material.STONE)
                            .requiresTool()
                            .breakByTool(FabricToolTags.PICKAXES, 3)
                            .strength(20.0F, 18.0F)
            ),
            new Item.Settings()
                    .group(IntoTheOmega.GROUP)
                    .rarity(Rarity.EPIC)
    );

    public static final Block OMEGA_CRYSTAL_ORE = register(
            "omega_crystal_ore",
            new OmegaCrystalOreBlock(
                    FabricBlockSettings
                            .of(Material.STONE)
                            .requiresTool()
                            .breakByTool(FabricToolTags.PICKAXES, 3)
                            .strength(10.0F, 9.0F)
            ),
            new Item.Settings()
                    .group(IntoTheOmega.GROUP)
                    .rarity(Rarity.EPIC)
    );

    public static final Block CONQUEST_FORGE = register(
            "conquest_forge",
            new ConquestForgeBlock(
                    FabricBlockSettings
                            .of(Material.METAL)
                            .breakByTool(FabricToolTags.PICKAXES)
                            .strength(25.0F, 9.0F)
                            .sounds(BlockSoundGroup.ANVIL)
            ),
            new Item.Settings()
                    .group(IntoTheOmega.GROUP)
                    .rarity(Rarity.EPIC)
    );

    public static final Block WATCHING_EYE = register(
            "watching_eye",
            new WatchingEyeBlock(
                    FabricBlockSettings
                            .of(Material.METAL)
                            .breakByTool(FabricToolTags.PICKAXES)
                            .strength(5f, 5f)
                            .sounds(BlockSoundGroup.GLASS)
            ),
            new Item.Settings().group(IntoTheOmega.GROUP)
    );

    public static final Block OBSIDIAN_GLASS = register(
            "obsidian_glass",
            new GlassBlock(
                    FabricBlockSettings
                            .of(Material.GLASS)
                            .strength(1f, 15f)
                            .sounds(BlockSoundGroup.GLASS)
                            .nonOpaque()
            ),
            new Item.Settings().group(IntoTheOmega.GROUP)
    );

    public static final Block OMEGA_GLASS = register(
            "omega_glass",
            new GlassBlock(
                    FabricBlockSettings
                            .of(Material.GLASS)
                            .strength(1f, 1f)
                            .sounds(BlockSoundGroup.GLASS)
                            .nonOpaque()
            ),
            new Item.Settings().group(IntoTheOmega.GROUP)
    );

    public static final Block CHISELED_OBSIDIAN = register("chiseled_obsidian", new Block(FabricBlockSettings.copyOf(Blocks.OBSIDIAN)), new Item.Settings().group(IntoTheOmega.GROUP));
    public static final Block OBSIDIAN_PILLAR = register("obsidian_pillar", new PillarBlock(FabricBlockSettings.copyOf(Blocks.OBSIDIAN)), new Item.Settings().group(IntoTheOmega.GROUP));
    public static final Block GILDED_OBSIDIAN = register("gilded_obsidian", new Block(FabricBlockSettings.copyOf(Blocks.OBSIDIAN)), new Item.Settings().group(IntoTheOmega.GROUP));

    public static final Block ENDER_DRAGON_STATUE = register("ender_dragon_statue", new StatueBlock(), new Item.Settings().group(IntoTheOmega.GROUP));
    public static final Block ENDERMAN_STATUE = register("enderman_statue", new StatueBlock(), new Item.Settings().group(IntoTheOmega.GROUP));

    private static <T extends Block> T register(String name, T block, Item.Settings settings) {
        T registeredBlock = Registry.register(Registry.BLOCK, IntoTheOmega.id(name), block);
        Registry.register(Registry.ITEM, IntoTheOmega.id(name), new BlockItem(registeredBlock, settings));
        return registeredBlock;
    }

    private static <T extends Block> T register(String name, T block) {
        return Registry.register(Registry.BLOCK, IntoTheOmega.id(name), block);
    }

    public static void init() {
        // NO-OP
    }

    private OmegaBlocks() {
        // NO-OP
    }
}
