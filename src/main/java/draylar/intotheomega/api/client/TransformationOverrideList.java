package draylar.intotheomega.api.client;

import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.render.model.json.Transformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class TransformationOverrideList {

    public static final TransformationOverrideList EMPTY = new TransformationOverrideList();
    private final List<TransformationOverride> overrides;

    private TransformationOverrideList() {
        this.overrides = Collections.emptyList();
    }

    public TransformationOverrideList(List<TransformationOverride> overrides) {
        this.overrides = overrides;
    }

    public boolean apply(BakedModel model, ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity entity, boolean leftHanded, MatrixStack matrices, ModelTransformation.Mode mode) {
        if (overrides != null && !this.overrides.isEmpty()) {
            for (TransformationOverride override : this.overrides) {
                if (override != null && override.matches(stack, world, entity)) {
                    Transformation transformation = override.getTransformation().getTransformation(mode);

                    if(transformation != Transformation.IDENTITY) {
                        transformation.apply(leftHanded, matrices);
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public boolean isEmpty() {
        return overrides.isEmpty();
    }
}
