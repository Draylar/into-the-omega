package draylar.intotheomega.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.World;

public class FrostedEyeEntity extends MobEntity {

    public FrostedEyeEntity(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void checkDespawn() {

    }

    @Override
    public boolean hasNoGravity() {
        return true;
    }
}
