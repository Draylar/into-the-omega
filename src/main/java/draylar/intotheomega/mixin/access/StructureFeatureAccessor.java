package draylar.intotheomega.mixin.access;

import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(StructureFeature.class)
public interface StructureFeatureAccessor<C extends FeatureConfig> {

    @Invoker(value = "register")
    static <F extends StructureFeature<?>> F register(String name, F structureFeature, GenerationStep.Feature step) {
        throw new UnsupportedOperationException();
    }
}
