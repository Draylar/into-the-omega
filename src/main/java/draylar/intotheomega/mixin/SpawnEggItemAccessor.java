package draylar.intotheomega.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.item.SpawnEggItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(SpawnEggItem.class)
public interface SpawnEggItemAccessor {
    @Accessor
    static Map<EntityType<?>, SpawnEggItem> getSPAWN_EGGS() {
        throw new UnsupportedOperationException();
    }
}
