package draylar.intotheomega.mixin.dev;

import net.minecraft.server.command.FillCommand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(FillCommand.class)
public class FillCommandMixin {

    @ModifyConstant(method = "execute", constant = @Constant(intValue = 32768))
    private static int adjustMaxFillSize(int original) {
        return 1_000_000;
    }
}
