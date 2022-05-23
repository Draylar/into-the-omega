package draylar.intotheomega.client.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

public class QuasarSlashParticle extends SpriteBillboardParticle {

    private final SpriteProvider spriteProvider;

    public QuasarSlashParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteProvider spriteProvider) {
        super(world, x, y, z);
        this.spriteProvider = spriteProvider;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.velocityZ = velocityZ;
        this.collidesWithWorld = false;
        this.setSpriteForAge(spriteProvider);
    }

    @Override
    public void tick() {
        super.tick();
        setSpriteForAge(spriteProvider);
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_LIT;
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<DefaultParticleType> {

        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return QuasarSlashParticle.create(defaultParticleType, clientWorld, x, y, z, velocityX, velocityY, velocityZ, spriteProvider);
        }
    }

    private static QuasarSlashParticle create(DefaultParticleType type, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteProvider spriteProvider) {
        QuasarSlashParticle particle = new QuasarSlashParticle(world, x, y, z, velocityX, velocityY, velocityZ, spriteProvider);

        float color = world.random.nextFloat(0.5f) + 0.5f;

        particle.maxAge = 4;
        particle.scale = 2;
        particle.red = color;
        particle.blue = color;
        particle.green = color * world.random.nextFloat();

        particle.angle = world.random.nextFloat() * 1000;
        particle.prevAngle = particle.angle;

        return particle;
    }
}
