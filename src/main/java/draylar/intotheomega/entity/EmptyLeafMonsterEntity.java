package draylar.intotheomega.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class EmptyLeafMonsterEntity extends PathAwareEntity implements IAnimatable {

    private final AnimationFactory factory = new AnimationFactory(this);

    public EmptyLeafMonsterEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void registerControllers(AnimationData data) {

    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}
