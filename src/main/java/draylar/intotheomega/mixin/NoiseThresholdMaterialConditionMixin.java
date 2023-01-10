package draylar.intotheomega.mixin;

import draylar.intotheomega.impl.NoiseApply;
import draylar.intotheomega.mixin.access.MaterialRuleContextAccessor;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.surfacebuilder.MaterialRules;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MaterialRules.NoiseThresholdMaterialCondition.class)
public abstract class NoiseThresholdMaterialConditionMixin implements MaterialRules.MaterialCondition, NoiseApply {

    @Shadow @Final private RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> noise;
    @Shadow @Final private double minThreshold;
    @Shadow @Final private double maxThreshold;
    @Unique private DoublePerlinNoiseSampler noiseOverride = null;
    @Unique private double minOverride;
    @Unique private double maxOverride;

    @Override
    public void set(DoublePerlinNoiseSampler noise, double min, double max) {
        this.noiseOverride = noise;
        this.minOverride = min;
        this.maxOverride = max;
    }

    // TODO: this is a test mixin exclusively for development. It does not need to ship to production.
    @Inject(method = "apply(Lnet/minecraft/world/gen/surfacebuilder/MaterialRules$MaterialRuleContext;)Lnet/minecraft/world/gen/surfacebuilder/MaterialRules$BooleanSupplier;", at = @At("HEAD"), cancellable = true)
    private void injectOverwwrideApplication(MaterialRules.MaterialRuleContext context, CallbackInfoReturnable<MaterialRules.BooleanSupplier> cir) {
        if(noiseOverride != null) {
            cir.setReturnValue(() -> {
                double d = noiseOverride.sample(context.x, 0.0, context.z);
                return d >= minOverride && d <= maxOverride;
            });
        }

        DoublePerlinNoiseSampler doublePerlinNoiseSampler = ((MaterialRuleContextAccessor) (Object) context).getSurfaceBuilder().getNoiseSampler(noise);
        cir.setReturnValue(() -> {
            double d = doublePerlinNoiseSampler.sample(context.x, 0.0, context.z);
            return d >= minThreshold && d <= maxThreshold;
        });
    }
}
