package draylar.intotheomega.client.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.model.PlayerEntityModel;

public class SectionedPlayerEntityModel extends PlayerEntityModel<AbstractClientPlayerEntity> {

    public Part active;

    public SectionedPlayerEntityModel(ModelPart root, boolean thinArms) {
        super(root, thinArms);
    }

    @Override
    public Iterable<ModelPart> getBodyParts() {
        if(active != null) {
            return switch(active) {
                case HEAD -> ImmutableList.of();
                case BOOTS -> ImmutableList.of(leftPants, rightPants);
                case CHEST -> ImmutableList.of(rightSleeve, rightArm, leftSleeve, leftArm, body, jacket);
                case LEGGINGS -> ImmutableList.of(leftLeg, rightLeg);
            };
        }

        return ImmutableList.of();
    }

    @Override
    public Iterable<ModelPart> getHeadParts() {
        return active == Part.HEAD ? ImmutableList.of(head, hat) : ImmutableList.of();
    }

    public void setActive(Part active) {
        this.active = active;
    }

    public enum Part {
        HEAD,
        CHEST,
        LEGGINGS,
        BOOTS;
    }
}
