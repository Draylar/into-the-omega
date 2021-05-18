package draylar.intotheomega.impl;

import draylar.intotheomega.api.client.TransformationOverrideList;

public interface ModelTransformationOverrideManipulator {
    TransformationOverrideList getTransformationOverrides();
    void setTransformationOverrides(TransformationOverrideList overrides);
}
