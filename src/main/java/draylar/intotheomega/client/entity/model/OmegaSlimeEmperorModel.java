package draylar.intotheomega.client.entity.model;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.entity.slime.OmegaSlimeEmperorEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class OmegaSlimeEmperorModel extends AnimatedGeoModel<OmegaSlimeEmperorEntity> {

    @Override
    public Identifier getModelLocation(OmegaSlimeEmperorEntity voidMatrixEntity) {
        return IntoTheOmega.id("geo/omega_slime_emperor.geo.json");
    }

    @Override
    public Identifier getTextureLocation(OmegaSlimeEmperorEntity voidMatrixEntity) {
        return IntoTheOmega.id("textures/entity/omega_slime_emperor.png");
    }

    @Override
    public Identifier getAnimationFileLocation(OmegaSlimeEmperorEntity voidMatrixEntity) {
        return IntoTheOmega.id("animations/omega_slime_emperor.animation.json");
    }
}
