package draylar.intotheomega.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.world.World;

public class AbyssalRiftEntity extends PathAwareEntity {

    public AbyssalRiftEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
        ignoreCameraFrustum = true;
    }

    @Override
    public boolean hasNoGravity() {
        return true;
    }

    @Override
    public void pushAway(Entity entity) {

    }

    @Override
    public void pushAwayFrom(Entity entity) {

    }

    @Override
    public boolean shouldRender(double distance) {
        return true;
    }

    @Override
    public boolean shouldRender(double cameraX, double cameraY, double cameraZ) {
         return true;
    }
}
