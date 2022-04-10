package draylar.intotheomega.mixin.access;

import net.minecraft.world.gen.surfacebuilder.MaterialRules;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(MaterialRules.MaterialRuleContext.class)
public interface MaterialRuleContextAccessor {

    @Accessor
    SurfaceBuilder getSurfaceBuilder();
}
