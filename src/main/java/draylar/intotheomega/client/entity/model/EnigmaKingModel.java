package draylar.intotheomega.client.entity.model;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.entity.enigma.EnigmaKingEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class EnigmaKingModel extends AnimatedGeoModel<EnigmaKingEntity> {

    @Override
    public Identifier getModelLocation(EnigmaKingEntity voidMatrixEntity) {
        return IntoTheOmega.id("geo/enigma_king.geo.json");
    }

    @Override
    public Identifier getTextureLocation(EnigmaKingEntity voidMatrixEntity) {
        return IntoTheOmega.id("textures/entity/enigma_king.png");
    }

    @Override
    public Identifier getAnimationFileLocation(EnigmaKingEntity voidMatrixEntity) {
        return IntoTheOmega.id("animations/enigma_king.animation.json");
    }

    @Override
    public void setLivingAnimations(EnigmaKingEntity entity, Integer uniqueID) {

    }

    @Override
    public void setLivingAnimations(EnigmaKingEntity entity, Integer uniqueID, AnimationEvent predicate) {
        super.setLivingAnimations(entity, uniqueID, predicate);
        IBone head = this.getAnimationProcessor().getBone("Head");

        EntityModelData extraData = (EntityModelData) predicate.getExtraDataOfType(EntityModelData.class).get(0);

        if (head != null) {
            head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
            head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
        }
    }
}
