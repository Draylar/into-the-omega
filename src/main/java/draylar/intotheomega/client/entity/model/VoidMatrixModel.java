package draylar.intotheomega.client.entity.model;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.entity.void_matrix.VoidMatrixEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class VoidMatrixModel extends AnimatedGeoModel<VoidMatrixEntity> {

    @Override
    public Identifier getModelLocation(VoidMatrixEntity voidMatrixEntity) {
        return IntoTheOmega.id("geo/void_matrix.geo.json");
    }

    @Override
    public Identifier getTextureLocation(VoidMatrixEntity voidMatrixEntity) {
        return IntoTheOmega.id("textures/entity/void_matrix.png");
    }

    @Override
    public Identifier getAnimationFileLocation(VoidMatrixEntity voidMatrixEntity) {
        return IntoTheOmega.id("animations/void_matrix.animation.json");
    }
}
