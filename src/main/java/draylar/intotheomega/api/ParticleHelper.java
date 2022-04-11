package draylar.intotheomega.api;

import net.minecraft.network.packet.s2c.play.ParticleS2CPacket;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

public class ParticleHelper {

    public static <T extends ParticleEffect> void spawnDistanceParticles(ServerWorld world, T particle, double x, double y, double z, int count, double deltaX, double deltaY, double deltaZ, double speed) {
        ParticleS2CPacket packet = new ParticleS2CPacket(particle, true, x, y, z, (float) deltaX, (float) deltaY, (float) deltaZ, (float) speed, count);
        for (int j = 0; j < world.getPlayers().size(); ++j) {
            ServerPlayerEntity Player = world.getPlayers().get(j);
            Player.networkHandler.sendPacket(packet);
        }
    }
}
