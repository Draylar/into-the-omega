package draylar.intotheomega.entity;

import draylar.intotheomega.api.ParticleHelper;
import draylar.intotheomega.registry.OmegaParticles;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

import java.util.List;

public class HomingStarlitProjectileEntity extends Entity {

    private HostileEntity target;

    public HomingStarlitProjectileEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    public void tick() {
        super.tick();
        noClip = true;

        if(!world.isClient) {
            ParticleHelper.spawnDistanceParticles((ServerWorld) world,
                    OmegaParticles.STARLIGHT,
                    getX() + world.random.nextDouble() - 0.5,
                    getY() + world.random.nextDouble() - 0.5,
                    getZ() + world.random.nextDouble() - 0.5,
                    25,
                    0.25,
                    0.25,
                    0.25,
                    0);

            if(age < 5 || age % 20 == 0) {
                if(target == null) {
                    List<HostileEntity> found = world.getEntitiesByClass(HostileEntity.class, new Box(getBlockPos()).expand(64, 64, 64), Entity::isAlive);
                    if(!found.isEmpty()) {
                        target = found.get(0);
                    }
                }
            }

            if(target != null) {
                Vec3d towardsTarget = target.getEyePos().subtract(getPos()).normalize();
                setVelocity(getVelocity().add(towardsTarget.multiply(0.5)).normalize());
                move(MovementType.SELF, getVelocity());

                // Explode when we're close to the target
                if(distanceTo(target) <= 2) {
                    discard();
                    world.createExplosion(this, getX(), getY(), getZ(), 4.0f, Explosion.DestructionType.NONE);
                }

                // Kill after 10 seconds
                if(age >= 20 * 10) {
                    discard();
                }
            }
        }
    }

    @Override
    public void initDataTracker() {

    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {

    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {

    }

    @Override
    public Packet<?> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }
}
