package draylar.intotheomega.impl;

import draylar.intotheomega.api.client.TransformationOverride;
import net.minecraft.client.render.model.json.JsonUnbakedModel;

import java.util.List;

public interface JsonUnbakedModelTransformationOverrideManipulator {
    void setTransformationOverrides(List<TransformationOverride> override);
    List<TransformationOverride> getTransformationOverrides(JsonUnbakedModel parent);
}
