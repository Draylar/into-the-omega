package draylar.intotheomega.registry;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.mixin.EntityTypeTagsAccessor;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public class OmegaTags {

    public static final Tag.Identified<EntityType<?>> END_CREATURE = register("end_creature");
    public static final Tag.Identified<EntityType<?>> SLIME_CREATURE = register("slime_creature");
    public static final Tag<Item> SPEARS = registerItemTag("spears");
    public static final Tag<Block> CHORUS_GROUND = registerBlockTag("chorus_ground");

    private static Tag.Identified<EntityType<?>> register(String id) {
        return EntityTypeTagsAccessor.callRegister(IntoTheOmega.id(id).toString());
    }

    private static Tag<Item> registerItemTag(String id) {
        return TagRegistry.item(IntoTheOmega.id(id));
    }

    private static Tag<Block> registerBlockTag(String id) {
        return TagRegistry.block(IntoTheOmega.id(id));
    }

    public static void init() {
        // NO-OP
    }

    private OmegaTags() {
        // NO-OP
    }
}
