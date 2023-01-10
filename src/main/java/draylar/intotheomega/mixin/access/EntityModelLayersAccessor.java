package draylar.intotheomega.mixin.access;

import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(EntityModelLayers.class)
public interface EntityModelLayersAccessor {

    @Invoker(value = "registerMain")
    static EntityModelLayer registerMain(String id) {
        throw new UnsupportedOperationException();
    }

    @Invoker(value = "createInnerArmor")
    static EntityModelLayer createInnerArmor(String id) {
        throw new UnsupportedOperationException();
    }

    @Invoker(value = "createOuterArmor")
    static EntityModelLayer createOuterArmor(String id) {
        throw new UnsupportedOperationException();
    }
}
