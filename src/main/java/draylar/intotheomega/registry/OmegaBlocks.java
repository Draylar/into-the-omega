package draylar.intotheomega.registry;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.block.ConquestForgeBlock;
import draylar.intotheomega.block.OmegaCrystalOreBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class OmegaBlocks {

    public static final Block OMEGA_CRYSTAL_ORE = register(
            "omega_crystal_ore",
            new OmegaCrystalOreBlock(
                    FabricBlockSettings
                            .of(Material.STONE)
                            .breakByTool(FabricToolTags.PICKAXES)
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
