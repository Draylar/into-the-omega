package draylar.intotheomega.api;

import net.minecraft.entity.Entity;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;

public interface SpawnHandling {

    default void onSpawnPacket(EntitySpawnS2CPacket packet) {
        ((Entity) this).setEntityId(0);
        ((Entity) this).updateTrackedPosition(packet.getX(), packet.getY(), packet.getZ());
        ((Entity) this).refreshPositionAfterTeleport(packet.getX(), packet.getY(), packet.getZ());
        ((Entity) this).pitch = (float)(packet.getPitch() * 360) / 256.0F;
        ((Entity) this).yaw = (float)(packet.getYaw() * 360) / 256.0F;
        ((Entity) this).setEntityId(packet.getId());
        ((Entity) this).setUuid(packet.getUuid());
    }
}
