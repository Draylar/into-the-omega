package draylar.intotheomega.mixin;

import net.minecraft.entity.ai.control.MoveControl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(MoveControl.class)
public interface MoveControlAccessor {
    @Accessor
    void setState(MoveControl.State state);
}
