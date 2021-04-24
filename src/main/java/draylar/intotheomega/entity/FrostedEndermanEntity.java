package draylar.intotheomega.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.world.World;

public class FrostedEndermanEntity extends EndermanEntity {

    public FrostedEndermanEntity(EntityType<? extends EndermanEntity> entityType, World world) {
        super(entityType, world);
    }
}
