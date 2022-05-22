package draylar.intotheomega.entity;

import draylar.intotheomega.registry.OmegaParticles;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class NovaStrikeEntity extends Entity {

    public NovaStrikeEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    public void tick() {
        super.tick();

        move(MovementType.SELF, getVelocity());

        if(world.isClient) {
            int clarity = 10;
            Vec3d direction = getVelocity().normalize().multiply(1 / 5f);
            Vec3d base = getPos();
            for (int i = 0; i < clarity; i++) {
                base = base.subtract(direction);
                world.addParticle(OmegaParticles.NOVA_STRIKE, true, base.getX(), base.getY(), base.getZ(), 0, 0, 0);
            }
        } else {
            if(getVelocity().getX() == 0 && getVelocity().getZ() == 0) {
                discard();
            }

            if(age >= 20 * 10) {
                discard();
            }

            if(onGround) {
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
