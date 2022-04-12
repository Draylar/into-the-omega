package draylar.intotheomega.mixin.dev;

import draylar.intotheomega.world.OmegaSurfaceRules;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import net.minecraft.world.gen.surfacebuilder.MaterialRules;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChunkGeneratorSettings.class)
public class ChunkGeneratorSettingsMixin {

    @Shadow @Final private BlockState defaultBlock;

    @Inject(method = "surfaceRule", at = @At("HEAD"), cancellable = true)
    private void autoswapSurfaceRules(CallbackInfoReturnable<MaterialRules.MaterialRule> cir) {
        if(FabricLoader.getInstance().isDevelopmentEnvironment() && defaultBlock.getBlock().equals(Blocks.END_STONE)) {
            cir.setReturnValue(OmegaSurfaceRules.create());
        }
    }
}
