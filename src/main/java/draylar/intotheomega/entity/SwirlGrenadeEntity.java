package draylar.intotheomega.entity;

import draylar.intotheomega.api.ParticleHelper;
import draylar.intotheomega.registry.OmegaItems;
import draylar.intotheomega.registry.OmegaParticles;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class SwirlGrenadeEntity extends ThrownItemEntity {

    public SwirlGrenadeEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public Item getDefaultItem() {
        return OmegaItems.SWIRL_GRENADE;
    }

    @Override
    public void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);

        if(!world.isClient) {
            world.createExplosion(this, getX(), getY(), getZ(), 4.0f, Explosion.DestructionType.NONE);
            ParticleHelper.spawnDistanceParticles((ServerWorld) world, OmegaParticles.OMEGA_SLIME, getX(), getY(), getZ(), 100, 5, 5, 5, 0);
            discard();
        }
    }
}
