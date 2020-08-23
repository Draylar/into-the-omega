package draylar.intotheomega.mixin;

import draylar.intotheomega.IntoTheOmega;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeCreator;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DefaultBiomeCreator.class)
public class DefaultBiomeCreatorMixin {

    @Inject(
            method = "method_31065",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/gen/feature/DefaultBiomeFeatures;addEndMobs(Lnet/minecraft/world/biome/SpawnSettings$Builder;)V")
    )
    private static void addOmegaCrystalOre(GenerationSettings.Builder builder, CallbackInfoReturnable<Biome> cir) {
        builder.feature(
                GenerationStep.Feature.UNDERGROUND_ORES,
                IntoTheOmega.OMEGA_ORE_FEATURE.configure(DefaultFeatureConfig.DEFAULT).decorate(Decorator.RANGE.configure(new RangeDecoratorConfig(1, 0, 175)))
        );
    }
}
