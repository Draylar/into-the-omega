package draylar.intotheomega.registry;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.mixin.access.EntityTypeTagsAccessor;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class OmegaTags {

    public static final TagKey<EntityType<?>> END_CREATURE = register("end_creature");
    public static final TagKey<EntityType<?>> SLIME_CREATURE = register("slime_creature");
    public static final TagKey<Item> SPEARS = registerItemTag("spears");
    public static final TagKey<Block> CHORUS_GROUND = registerBlockTag("chorus_ground");

    private static TagKey<EntityType<?>> register(String id) {
        return EntityTypeTagsAccessor.register(IntoTheOmega.id(id).toString());
    }

    private static TagKey<Item> registerItemTag(String id) {
        return TagKey.of(Registry.ITEM_KEY, IntoTheOmega.id(id));
    }

    private static TagKey<Block> registerBlockTag(String id) {
        return TagKey.of(Registry.BLOCK_KEY, new Identifier(id));
    }

    public static void init() {
        // NO-OP
    }

    private OmegaTags() {
        // NO-OP
    }
}
