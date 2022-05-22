package draylar.intotheomega.client.entity.model;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.entity.NovaGhoulEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class NovaGhoulModel extends AnimatedGeoModel<NovaGhoulEntity> {

    private static final Identifier GEO_FILE = IntoTheOmega.id("geo/nova_ghoul.geo.json");
    private static final Identifier TEXTURE_FILE = IntoTheOmega.id("textures/entity/nova_ghoul.png");

    @Override
    public Identifier getModelLocation(NovaGhoulEntity object) {
        return GEO_FILE;
    }

    @Override
    public Identifier getTextureLocation(NovaGhoulEntity object) {
        return TEXTURE_FILE;
    }

    @Override
    public Identifier getAnimationFileLocation(NovaGhoulEntity animatable) {
        return null;
    }
}
