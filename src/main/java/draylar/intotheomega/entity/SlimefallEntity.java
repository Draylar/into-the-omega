package draylar.intotheomega.entity;

import draylar.intotheomega.api.ParticleHelper;
import draylar.intotheomega.registry.OmegaParticles;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class SlimefallEntity extends Entity {

    public SlimefallEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    public boolean isGlowing() {
        return true;
    }

    @Override
    public void tick() {
        setVelocity(0, -1, 0);
        move(MovementType.SELF, getVelocity());

        if(!world.isClient && onGround) {
            world.createExplosion(this, getX(), getY(), getZ(), 2.0f, Explosion.DestructionType.NONE);
            ((ServerWorld) world).spawnParticles(OmegaParticles.OMEGA_SLIME, getX(), getY(), getZ(), 10, 3, 3, 3, 0);
            ((ServerWorld) world).spawnParticles(ParticleTypes.END_ROD, getX(), getY(), getZ(), 10, 3, 3, 3, 0);
            world.playSound(null, getX(), getY(), getZ(), SoundEvents.ENTITY_SLIME_HURT, SoundCategory.PLAYERS, 3.0f, 1.0f);
            discard();
        }

        if(!world.isClient) {
            ParticleHelper.spawnDistanceParticles((ServerWorld) world, ParticleTypes.ITEM_SLIME, getX(), getY(), getZ(), 10, 3, 3, 3, 0);
        }

        super.tick();
    }

    @Override
    protected void initDataTracker() {

    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {

    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {

    }

    @Override
    public Packet<?> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }
}
