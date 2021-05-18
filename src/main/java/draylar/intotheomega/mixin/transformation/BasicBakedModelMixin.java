package draylar.intotheomega.mixin.transformation;

import draylar.intotheomega.api.client.TransformationOverrideList;
import draylar.intotheomega.impl.ModelTransformationOverrideManipulator;
import net.minecraft.client.render.model.BasicBakedModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(BasicBakedModel.class)
public class BasicBakedModelMixin implements ModelTransformationOverrideManipulator {

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
