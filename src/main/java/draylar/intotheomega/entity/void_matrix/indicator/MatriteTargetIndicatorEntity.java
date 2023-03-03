package draylar.intotheomega.entity.void_matrix.indicator;

import draylar.intotheomega.registry.OmegaParticles;
import draylar.intotheomega.util.ParticleUtils;
import draylar.intotheomega.vfx.VFX;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.world.World;

public class MatriteTargetIndicatorEntity extends Entity {

    private boolean hasSpawnedParticle = false;

    public MatriteTargetIndicatorEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    public void tick() {
        super.tick();

        if(!world.isClient) {
            if(!hasSpawnedParticle) {
                VFX.circleIndicator(world, getX(), getY(), getZ(), 5.0, 0xFFFF00FF, 70);
                ParticleUtils.spawnParticles(world, OmegaParticles.MATRIX_STAR, true, getX(), getY() + 6, getZ(), 25, 1, 3, 1, 1.0);
                hasSpawnedParticle = true;
            }

            if(age >= 100) {
                discard();
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
