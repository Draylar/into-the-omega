package draylar.intotheomega.api.particle;

import net.minecraft.network.packet.s2c.play.ParticleS2CPacket;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleType;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Collection;

public class Particles {

    public static void sendTo(Collection<ServerPlayerEntity> recipients, DefaultParticleType type, double x, double y, double z, int count) {
        ParticleS2CPacket particlePacket = new ParticleS2CPacket(type, false, x, y, z, 0, 0, 0, 0, count);

        for (ServerPlayerEntity recipient : recipients) {
            recipient.networkHandler.sendPacket(particlePacket);
        }
    }
}