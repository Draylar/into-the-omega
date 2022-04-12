package draylar.intotheomega.world;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.world.gen.surfacebuilder.MaterialRules;

public record DynamicNoiseMaterialCondition(DoublePerlinNoiseSampler noise, double minThreshold, double maxThreshold) implements MaterialRules.MaterialCondition {

    static final Codec<DynamicNoiseMaterialCondition> CONDITION_CODEC = RecordCodecBuilder
            .create(instance -> instance.group((
                    Codec.DOUBLE.fieldOf("min_threshold").forGetter(DynamicNoiseMaterialCondition::minThreshold)),
                    Codec.DOUBLE.fieldOf("max_threshold").forGetter(DynamicNoiseMaterialCondition::maxThreshold))
                    .apply(instance, DynamicNoiseMaterialCondition::new));

    public DynamicNoiseMaterialCondition(double minThreshold, double maxThreshold) {
        this(null, minThreshold, maxThreshold);
    }

    @Override
    public Codec<? extends MaterialRules.MaterialCondition> codec() {
        return CONDITION_CODEC;
    }

    @Override
    public MaterialRules.BooleanSupplier apply(MaterialRules.MaterialRuleContext context) {
        return () -> {
            double d = noise.sample(context.x, 0.0, context.z);
            return d >= minThreshold && d <= maxThreshold;
        };
    }
}
