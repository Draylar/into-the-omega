package draylar.intotheomega.api;

import net.minecraft.server.world.ServerEntityManager;
import net.minecraft.world.entity.EntityLike;

public interface EntityTrackingHandler<T extends EntityLike> {

    void startTracking(ServerEntityManager<T> manager);
}
