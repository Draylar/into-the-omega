package draylar.intotheomega.entity.nova;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.world.World;

public class NovaGroundBurstEntity extends Entity {

    private int delay = 0;

    public NovaGroundBurstEntity(EntityType<?> type, World world) {
        super(type, world);
        ignoreCameraFrustum = true;
    }

    @Override
    public void tick() {
        super.tick();

        if(!world.isClient) {
            delay++;
            if(delay >= 20 * 3) {

            }
        }
    }

    @Override
    public boolean shouldRender(double cameraX, double cameraY, double cameraZ) {
        return true;
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
