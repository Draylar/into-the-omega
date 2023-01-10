package draylar.intotheomega.util;

import draylar.intotheomega.mixin.access.ServerWorldAccessor;
import net.minecraft.network.packet.s2c.play.ParticleS2CPacket;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

public class ParticleUtils {

    public static <T extends ParticleEffect> int spawnParticles(ServerWorld world, T particle, boolean longDistance, double x, double y, double z, int count, double deltaX, double deltaY, double deltaZ, double speed) {
        ParticleS2CPacket particleS2CPacket = new ParticleS2CPacket(particle, longDistance, x, y, z, (float)deltaX, (float)deltaY, (float)deltaZ, (float)speed, count);
        int i = 0;

        for(int j = 0; j < world.getPlayers().size(); ++j) {
            ServerPlayerEntity serverPlayerEntity = world.getPlayers().get(j);
            if (((ServerWorldAccessor) world).callSendToPlayerIfNearby(serverPlayerEntity, longDistance, x, y, z, particleS2CPacket)) {
                ++i;
            }
        }

        return i;
    }
}
