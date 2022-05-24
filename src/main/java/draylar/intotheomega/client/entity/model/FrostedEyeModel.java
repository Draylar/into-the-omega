package draylar.intotheomega.client.entity.model;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.entity.FrostedEyeEntity;
import draylar.intotheomega.entity.OmegaSlimeEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class FrostedEyeModel extends AnimatedGeoModel<FrostedEyeEntity> {

    private static final Identifier MODEL_LOCATION = IntoTheOmega.id("geo/frosted_eye.geo.json");
    private static final Identifier ANIMATION_LOCATION = IntoTheOmega.id("animations/frosted_eye.animation.json");
    private static final Identifier TEXTURE_LOCATION = IntoTheOmega.id("textures/entity/frosted_eye.png");

    @Override
    public Identifier getModelLocation(FrostedEyeEntity eye) {
        return MODEL_LOCATION;
    }

    @Override
    public Identifier getTextureLocation(FrostedEyeEntity eye) {
        return TEXTURE_LOCATION;
    }

    @Override
    public Identifier getAnimationFileLocation(FrostedEyeEntity eye) {
        return null;
    }
}