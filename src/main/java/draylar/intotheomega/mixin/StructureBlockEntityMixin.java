package draylar.intotheomega.mixin;

import net.minecraft.block.entity.StructureBlockBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(StructureBlockBlockEntity.class)
public class StructureBlockEntityMixin {

    @ModifyConstant(method = "readNbt", constant = @Constant(intValue = 48))
    private int adjustMaxStructureSize(int constant) {
        return constant * 2;
    }
}

