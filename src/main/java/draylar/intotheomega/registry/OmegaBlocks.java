package draylar.intotheomega.registry;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.block.*;
import draylar.intotheomega.block.air.ThornAirBlock;
import draylar.intotheomega.block.dungeon.InvisibleDungeonBrick;
import draylar.intotheomega.block.dungeon.SlimeObeliskBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
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

    public static final Block OBSIDISHROOM = register("obsidishroom", new EndMushroomBlock(FabricBlockSettings.copyOf(Blocks.RED_MUSHROOM)), new Item.Settings().group(IntoTheOmega.GROUP));
    public static final Block ENDERSHROOM = register("endershroom", new EndMushroomBlock(FabricBlockSettings.copyOf(Blocks.RED_MUSHROOM)), new Item.Settings().group(IntoTheOmega.GROUP));
    public static final Block END_STONE_BOOKSHELF = register("end_stone_bookshelf", new Block(AbstractBlock.Settings.of(Material.STONE).strength(1.5F).sounds(BlockSoundGroup.WOOD)), new Item.Settings().group(IntoTheOmega.GROUP));
    public static final Block OBSIDIAN_BOOKSHELF = register("obsidian_bookshelf", new Block(AbstractBlock.Settings.of(Material.STONE).strength(4F).sounds(BlockSoundGroup.WOOD)), new Item.Settings().group(IntoTheOmega.GROUP));

    public static final Block VOID_MATRIX_SPAWN_BLOCK = register("void_matrix_spawn_block", new VoidMatrixSpawnBlock(FabricBlockSettings.copyOf(Blocks.OBSIDIAN).nonOpaque()), new Item.Settings().group(IntoTheOmega.GROUP));

    public static final Block CHISELED_PURPUR = register("chiseled_purpur", new Block(FabricBlockSettings.copyOf(Blocks.PURPUR_BLOCK)), new Item.Settings().group(IntoTheOmega.GROUP));

    public static final Block MATRIX_BRICKS = register("matrix_bricks", new Block(FabricBlockSettings.copyOf(Blocks.BRICKS)), new Item.Settings().group(IntoTheOmega.GROUP));
    public static final Block MATRIX_STAIRS = register("matrix_brick_stairs", new StairsBlock(MATRIX_BRICKS.getDefaultState(), FabricBlockSettings.copyOf(Blocks.BRICKS)), new Item.Settings().group(IntoTheOmega.GROUP));
    public static final Block MATRIX_SLAB = register("matrix_brick_slab", new SlabBlock(FabricBlockSettings.copyOf(Blocks.BRICKS)), new Item.Settings().group(IntoTheOmega.GROUP));
    public static final Block MATRIX_PILLAR = register("matrix_brick_pillar", new PillarBlock(FabricBlockSettings.copyOf(Blocks.BRICKS)), new Item.Settings().group(IntoTheOmega.GROUP));
    public static final Block MATRIX_GLASS = register("matrix_glass", new GlassBlock(FabricBlockSettings.copyOf(Blocks.GLASS).nonOpaque()), new Item.Settings().group(IntoTheOmega.GROUP));

    public static final Block OBSIDIAN_STAIRS = register("obsidian_stairs", new StairsBlock(Blocks.OBSIDIAN.getDefaultState(), FabricBlockSettings.copyOf(Blocks.OBSIDIAN)), new Item.Settings().group(IntoTheOmega.GROUP));
    public static final Block OBSIDIAN_SLAB = register("obsidian_slab", new SlabBlock(FabricBlockSettings.copyOf(Blocks.OBSIDIAN)), new Item.Settings().group(IntoTheOmega.GROUP));
    public static final Block CHISELED_OBSIDIAN = register("chiseled_obsidian", new Block(FabricBlockSettings.copyOf(Blocks.OBSIDIAN)), new Item.Settings().group(IntoTheOmega.GROUP));
    public static final Block OBSIDIAN_PILLAR = register("obsidian_pillar", new PillarBlock(FabricBlockSettings.copyOf(Blocks.OBSIDIAN)), new Item.Settings().group(IntoTheOmega.GROUP));

    public static final Block GILDED_OBSIDIAN = register("gilded_obsidian", new Block(FabricBlockSettings.copyOf(Blocks.OBSIDIAN)), new Item.Settings().group(IntoTheOmega.GROUP));
    public static final Block BEJEWELED_OBSIDIAN = register("bejeweled_obsidian", new Block(FabricBlockSettings.copyOf(Blocks.OBSIDIAN)), new Item.Settings().group(IntoTheOmega.GROUP));
    public static final Block MOLTEN_OBSIDIAN = register("molten_obsidian", new Block(FabricBlockSettings.copyOf(Blocks.OBSIDIAN)), new Item.Settings().group(IntoTheOmega.GROUP));
    public static final Block RAINBOW_OBSIDIAN = register("rainbow_obsidian", new Block(FabricBlockSettings.copyOf(Blocks.OBSIDIAN)), new Item.Settings().group(IntoTheOmega.GROUP));
    public static final Block BEJEWELED_OBSIDIAN_STREAK = register("bejeweled_obsidian_streak", new PillarBlock(FabricBlockSettings.copyOf(Blocks.OBSIDIAN)), new Item.Settings().group(IntoTheOmega.GROUP));

    // Omega Slime
    public static final Block OMEGA_SLIME_BLOCK = register("omega_slime_block", new Block(FabricBlockSettings.of(Material.ORGANIC_PRODUCT).slipperiness(0.8f)));
    public static final Block SLIME_OBELISK = register("slime_obelisk", new SlimeObeliskBlock(FabricBlockSettings.copy(Blocks.STONE).strength(1000000, 1000000)), new Item.Settings().group(IntoTheOmega.GROUP));
    public static final Block CONGEALED_OMEGA_SLIME = register("congealed_omega_slime", new Block(FabricBlockSettings.of(Material.ORGANIC_PRODUCT).slipperiness(0.8f).sounds(BlockSoundGroup.SLIME)), new Item.Settings().group(IntoTheOmega.GROUP));
    public static final Block CONGEALED_SLIME = register("congealed_slime", new Block(FabricBlockSettings.of(Material.ORGANIC_PRODUCT).slipperiness(0.8f)), new Item.Settings().group(IntoTheOmega.GROUP));
    public static final Block OMEGA_SLIME_FLUID = register("omega_slime_fluid", new FluidBlock(OmegaFluids.OMEGA_SLIME_STILL, FabricBlockSettings.of(Material.WATER).noCollision().ticksRandomly().strength(25.0F)) {
    });
    public static final Block SLIME_LAUNCH_PAD = register("slime_launch_pad", new SlimeLaunchPadBlock(FabricBlockSettings.copyOf(Blocks.SLIME_BLOCK)), new Item.Settings().group(IntoTheOmega.GROUP));
    public static final Block SWIRLED_MIXER = register("swirled_mixer", new SwirledMixerBlock(FabricBlockSettings.copyOf(Blocks.END_STONE)), new Item.Settings().group(IntoTheOmega.GROUP));
    public static final Block OMEGA_SLIME_BRICKS = register("omega_slime_bricks", new Block(FabricBlockSettings.of(Material.ORGANIC_PRODUCT).slipperiness(0.8f).sounds(BlockSoundGroup.SLIME).hardness(4.0f)), new Item.Settings().group(IntoTheOmega.GROUP));
    public static final Block SLERN = register("slern", new SlernBlock(FabricBlockSettings.of(Material.PLANT).slipperiness(0.8f).sounds(BlockSoundGroup.SLIME).breakInstantly().dynamicBounds().nonOpaque().noCollision()), new Item.Settings().group(IntoTheOmega.GROUP));
    public static final Block RICH_SLERN = register("rich_slern", new SlernBlock(FabricBlockSettings.of(Material.PLANT).slipperiness(0.8f).sounds(BlockSoundGroup.SLIME).breakInstantly().dynamicBounds().nonOpaque().noCollision()), new Item.Settings().group(IntoTheOmega.GROUP));

    public static final Block PHASE_PAD = register("phase_pad", new PhasePadBlock(FabricBlockSettings.copyOf(Blocks.OBSIDIAN)), new Item.Settings().group(IntoTheOmega.GROUP));
    public static final Block ENIGMA_STAND = register("enigma_stand", new EnigmaStandBlock(FabricBlockSettings.copyOf(Blocks.OBSIDIAN).nonOpaque()), new Item.Settings().group(IntoTheOmega.GROUP));

    // Dungeon Air
    public static final Block THORN_AIR = register("thorn_air", new ThornAirBlock(FabricBlockSettings.copyOf(Blocks.AIR)));
    public static final Block INVISIBLE_DUNGEON_BRICK = register("invisible_dungeon_brick", new InvisibleDungeonBrick(FabricBlockSettings.copyOf(Blocks.BEDROCK).nonOpaque()), new Item.Settings().group(IntoTheOmega.GROUP));

    // Chorus
    public static final Block CHORUS_GRASS = register("chorus_grass_block", new Block(FabricBlockSettings.copyOf(Blocks.GRASS_BLOCK)), new Item.Settings().group(IntoTheOmega.GROUP));
    public static final Block CHORUS_BLOCK = register("chorus_block", new Block(FabricBlockSettings.copyOf(Blocks.STONE)), new Item.Settings().group(IntoTheOmega.GROUP));

    // variant
    public static final Block VARIANT_BLOCK = register("variant_block", new Block(FabricBlockSettings.copyOf(Blocks.STONE)), new Item.Settings().group(IntoTheOmega.GROUP));

    // abyss
    public static final Block ABYSS_CHAIN = register("abyss_chain", new AbyssChainBlock(FabricBlockSettings.of(Material.STONE)), new Item.Settings().group(IntoTheOmega.GROUP));
    public static final Block ABYSSAL_SLATE = register("abyssal_slate", new Block(FabricBlockSettings.copy(Blocks.PURPUR_BLOCK).luminance(b -> 5)), new Item.Settings().group(IntoTheOmega.GROUP));
    public static final Block ABYSSAL_SLATE_SLAB = register("abyssal_slate_slab", new SlabBlock(FabricBlockSettings.copy(Blocks.PURPUR_BLOCK)), new Item.Settings().group(IntoTheOmega.GROUP));
    public static final Block ABYSSAL_KEY = register("abyssal_key", new AbyssalKeyBlock(FabricBlockSettings.copyOf(Blocks.END_STONE_BRICKS)), new Item.Settings().group(IntoTheOmega.GROUP));

    // Crystal
    public static final Block CRYSTALITE = register("crystalite", new Block(FabricBlockSettings.of(Material.GLASS)), new Item.Settings());

    public static final Block BEJEWELED_LOCK = register("bejeweled_lock", new BejeweledLockBlock(FabricBlockSettings.of(Material.STONE)), new Item.Settings().group(IntoTheOmega.GROUP));
    public static final Block OMEGA_LANTERN = register("omega_lantern", new LanternBlock(AbstractBlock.Settings.of(Material.METAL).requiresTool().strength(3.5F).sounds(BlockSoundGroup.LANTERN).luminance((state) -> 12).nonOpaque()), new Item.Settings().group(IntoTheOmega.GROUP));

    // Dark Sakura
    public static final Block DARK_SAKURA_LEAVES = register("dark_sakura_leaves", new DarkSakuraLeavesBlock(FabricBlockSettings.copyOf(Blocks.ACACIA_LEAVES).nonOpaque()), new Item.Settings());
    public static final Block DARK_SAKURA_LEAF_PILE = register("dark_sakura_leaf_pile", new LeafPileBlock(FabricBlockSettings.copyOf(Blocks.ACACIA_LEAVES).nonOpaque()), new Item.Settings());
    public static final Block SAKURA_EMBUED_ENDSTONE = register("sakura_embued_endstone", new Block(FabricBlockSettings.copyOf(Blocks.GRASS_BLOCK)), new Item.Settings());

    public static final Block LESSER_STAR_EDGE = register("lesser_star_edge", new Block(FabricBlockSettings.copyOf(Blocks.GLOWSTONE).luminance(15)), new Item.Settings().group(IntoTheOmega.GROUP));
    public static final Block STARFALL_BRICKS = register("starfall_bricks", new Block(FabricBlockSettings.copyOf(Blocks.GLOWSTONE).luminance(15)), new Item.Settings().group(IntoTheOmega.GROUP));

    public static final Block MIRROR_BLOCK = register("mirror_block", new MirrorBlock(FabricBlockSettings.copyOf(Blocks.GLOWSTONE)), new Item.Settings().group(IntoTheOmega.GROUP));

    public static final GalaxyFurnaceBlock GALAXY_FURNACE = register("galaxy_furnace", new GalaxyFurnaceBlock(FabricBlockSettings.copyOf(Blocks.STONE).nonOpaque()), new Item.Settings());
    public static final Block STARLIGHT = register("starlight", new StarlightBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).luminance(15).hardness(10.0f).nonOpaque()), new Item.Settings());

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
