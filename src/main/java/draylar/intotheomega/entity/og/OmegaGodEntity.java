package draylar.intotheomega.entity.og;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.FlyingEntity;
import net.minecraft.world.World;

public class OmegaGodEntity extends FlyingEntity {
    

    public OmegaGodEntity(EntityType<? extends FlyingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void tick() {
        super.tick();
    }
}
