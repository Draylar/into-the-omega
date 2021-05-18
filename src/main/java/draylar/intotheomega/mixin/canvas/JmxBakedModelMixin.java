package draylar.intotheomega.mixin.canvas;

import draylar.intotheomega.api.client.TransformationOverrideList;
import draylar.intotheomega.impl.ModelTransformationOverrideManipulator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(targets = "grondag.jmx.json.model.JmxBakedModel")
public class JmxBakedModelMixin implements ModelTransformationOverrideManipulator {

    @Unique
    private TransformationOverrideList overrides;


    @Override
    public TransformationOverrideList getTransformationOverrides() {
        return overrides;
    }

    @Override
    public void setTransformationOverrides(TransformationOverrideList overrides) {
        this.overrides = overrides;
    }
}
