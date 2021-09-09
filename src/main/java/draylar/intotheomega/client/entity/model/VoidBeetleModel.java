package draylar.intotheomega.client.entity.model;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.entity.VoidBeetleEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class VoidBeetleModel extends AnimatedGeoModel<VoidBeetleEntity> {

    @Override
    public Identifier getModelLocation(VoidBeetleEntity entity) {
        return IntoTheOmega.id("geo/void_beetle.geo.json");
    }

    @Override
    public Identifier getTextureLocation(VoidBeetleEntity entity) {
        return IntoTheOmega.id("textures/entity/void_beetle.png");
    }

    @Override
    public Identifier getAnimationFileLocation(VoidBeetleEntity entity) {
        return IntoTheOmega.id("animations/void_beetle.animation.json");
    }
}