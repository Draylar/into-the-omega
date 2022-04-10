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

    @Override
    public MaterialRules.BooleanSupplier apply(MaterialRules.MaterialRuleContext context) {
        if(noiseOverride != null) {
            return () -> {
                double d = noiseOverride.sample(context.x, 0.0, context.z);
                return d >= minOverride && d <= maxOverride;
            };
        }

        DoublePerlinNoiseSampler doublePerlinNoiseSampler = ((MaterialRuleContextAccessor) (Object) context).getSurfaceBuilder().getNoiseSampler(noise);
        return () -> {
            double d = doublePerlinNoiseSampler.sample(context.x, 0.0, context.z);
            return d >= minThreshold && d <= maxThreshold;
        };
    }
}
