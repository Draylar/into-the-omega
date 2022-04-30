package draylar.intotheomega.entity.starfall;

import draylar.intotheomega.registry.OmegaParticles;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class StarfallProjectileEntity extends ProjectileEntity {

    private Vec3d target;

    public StarfallProjectileEntity(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void tick() {
        super.tick();

        move(MovementType.SELF, getVelocity());

        if(world instanceof ServerWorld serverWorld) {
            if(target == null || Math.sqrt(getPos().distanceTo(target)) <= 1.0f) {
                serverWorld.spawnParticles(OmegaParticles.STARFALL_NODE, getX() + (world.random.nextDouble() - .5), getY() + (world.random.nextDouble() - .5), getZ() + (world.random.nextDouble() - .5), 25, 1, 1, 1, 0.1);
                serverWorld.spawnParticles(ParticleTypes.EXPLOSION, getX() + (world.random.nextDouble() - .5), getY() + (world.random.nextDouble() - .5), getZ() + (world.random.nextDouble() - .5), 5, 1, 1, 1, 0.1);

                noClip = false;
                setVelocity(0, 0, 0);
                serverWorld.playSound(null, getX(), getY(), getZ(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.AMBIENT, 3.0f, 1.0f);
            }
        }
    }

    @Override
    public boolean shouldRender(double distance) {
        double maxDistance = 128;
        return distance < (maxDistance * Entity.getRenderDistanceMultiplier()) * maxDistance;
    }

    @Override
    public void initDataTracker() {

    }

    public void setTargetLocation(Vec3d target) {
        this.target = target;
    }
}
