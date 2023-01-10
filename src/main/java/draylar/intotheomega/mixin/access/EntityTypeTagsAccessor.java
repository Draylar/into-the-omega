package draylar.intotheomega.mixin.access;

import net.minecraft.entity.EntityType;
import net.minecraft.tag.EntityTypeTags;
import net.minecraft.tag.TagKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(EntityTypeTags.class)
public interface EntityTypeTagsAccessor {

    @Invoker(value = "register")
    static TagKey<EntityType<?>> register(String id) {
        throw new UnsupportedOperationException();
    }
}
