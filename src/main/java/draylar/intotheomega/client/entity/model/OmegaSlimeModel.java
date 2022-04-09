package draylar.intotheomega.client.entity.model;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.entity.OmegaSlimeEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class OmegaSlimeModel extends AnimatedGeoModel<OmegaSlimeEntity> {

    private static final Identifier MODEL_LOCATION = IntoTheOmega.id("geo/omega_slime.geo.json");
    private static final Identifier ANIMATION_LOCATION = IntoTheOmega.id("animations/omega_slime.animation.json");
    private static final Identifier TEXTURE_LOCATION = IntoTheOmega.id("textures/entity/omega_slime.png");

    @Override
    public Identifier getModelLocation(OmegaSlimeEntity slime) {
        return MODEL_LOCATION;
    }

    @Override
    public Identifier getTextureLocation(OmegaSlimeEntity slime) {
        return TEXTURE_LOCATION;
    }

    @Override
    public Identifier getAnimationFileLocation(OmegaSlimeEntity slime) {
        return ANIMATION_LOCATION;
    }
}
