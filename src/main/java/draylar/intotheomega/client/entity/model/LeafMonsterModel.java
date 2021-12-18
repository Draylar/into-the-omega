package draylar.intotheomega.client.entity.model;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.entity.EmptyLeafMonsterEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class LeafMonsterModel extends AnimatedGeoModel<EmptyLeafMonsterEntity> {

    public static final Identifier MODEL_LOCATION = IntoTheOmega.id("geo/leaf_monster.geo.json");
    public static final Identifier ANIMATION = IntoTheOmega.id("animations/leaf_monster.animation.json");
    public static final Identifier TEXTURE_LOCATION = IntoTheOmega.id("textures/entity/expanded_void_web.png");

    @Override
    public Identifier getModelLocation(EmptyLeafMonsterEntity object) {
        return MODEL_LOCATION;
    }

    @Override
    public Identifier getTextureLocation(EmptyLeafMonsterEntity object) {
        return IntoTheOmega.id("textures/entity/expanded_void_web.png");
    }

    @Override
    public Identifier getAnimationFileLocation(EmptyLeafMonsterEntity animatable) {
        return ANIMATION;
    }
}
