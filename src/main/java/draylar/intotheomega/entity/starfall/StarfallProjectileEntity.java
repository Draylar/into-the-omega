package draylar.intotheomega.entity.starfall;

import draylar.intotheomega.api.ParticleHelper;
import draylar.intotheomega.registry.OmegaDamageSources;
import draylar.intotheomega.registry.OmegaParticles;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class StarfallProjectileEntity extends ProjectileEntity {

    private static final TrackedData<Boolean> COLLIDED = DataTracker.registerData(StarfallProjectileEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private int collisionTicks = 0;
    private Vec3d target;

    public StarfallProjectileEntity(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void tick() {
        super.tick();

        move(MovementType.SELF, getVelocity());

        if(world instanceof ServerWorld serverWorld) {
            ParticleHelper.spawnDistanceParticles(serverWorld, OmegaParticles.STARFALL_NODE, getX() + (world.random.nextDouble() - .5), getY() + (world.random.nextDouble() - .5), getZ() + (world.random.nextDouble() - .5), 3, 0.5, 0.5, 0.5, 0.05);
            ParticleHelper.spawnDistanceParticles(serverWorld, OmegaParticles.STARFALL_SWIRL, getX() + (world.random.nextDouble() - .5), getY() + (world.random.nextDouble() - .5), getZ() + (world.random.nextDouble() - .5), 3, 1, 1, 1, 0);
        }

        if(hasCollided()) {
            collisionTicks++;
            if(!world.isClient) {
                if(collisionTicks > 5) {
                    noClip = false;
                }
                if(collisionTicks >= 10) {
                    discard();
                }
            }
        } else {
            if(world instanceof ServerWorld serverWorld) {
                if(target == null || Math.sqrt(getPos().distanceTo(target)) <= 1.0f) {
                    ParticleHelper.spawnDistanceParticles(serverWorld, OmegaParticles.STARFALL_NODE, getX() + (world.random.nextDouble() - .5), getY() + (world.random.nextDouble() - .5), getZ() + (world.random.nextDouble() - .5), 25, 1, 1, 1, 0.1);
                    ParticleHelper.spawnDistanceParticles(serverWorld, ParticleTypes.EXPLOSION_EMITTER, getX() + (world.random.nextDouble() - .5), getY() + (world.random.nextDouble() - .5), getZ() + (world.random.nextDouble() - .5), 5, 1, 1, 1, 0.1);

                    dataTracker.set(COLLIDED, true);
                    setVelocity(0, 0, 0);
                    serverWorld.playSound(null, getX(), getY(), getZ(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.AMBIENT, 3.0f, 1.0f);

                    // Damage nearby entities
                    for (Entity hit : serverWorld.getOtherEntities(this, new Box(getBlockPos()).expand(5))) {
                        if(hit != getOwner()) {
                            hit.damage(OmegaDamageSources.createStarfallProjectile(getOwner()), 20.0f);
                        }
                    }
                }
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
        dataTracker.startTracking(COLLIDED, false);
    }

    public void setTargetLocation(Vec3d target) {
        this.target = target;
    }

    public boolean hasCollided() {
        return dataTracker.get(COLLIDED);
    }

    public int getCollisionTicks() {
        return collisionTicks;
    }
}
