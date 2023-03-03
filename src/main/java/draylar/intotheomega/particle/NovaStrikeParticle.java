package draylar.intotheomega.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

public class NovaStrikeParticle extends SpriteBillboardParticle {

    public NovaStrikeParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteProvider spriteProvider) {
        super(world, x, y, z);
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.velocityZ = velocityZ;
        this.collidesWithWorld = false;
        this.setSpriteForAge(spriteProvider);
    }

    @Override
    public void tick() {
        super.tick();

        float value = (float) Math.sin(age) / 2 + 0.5f;

        red = 1.0f;
        blue = 1.0f;
        green = value * 0.7f;
    }

    @Override
    public ParticleTextureSheet getType() {
        return OmegaParticle.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<DefaultParticleType> {

        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return NovaStrikeParticle.create(defaultParticleType, clientWorld, x, y, z, velocityX, velocityY, velocityZ, spriteProvider);
        }
    }

    private static NovaStrikeParticle create(DefaultParticleType type, ClientWorld clientWorld, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteProvider spriteProvider) {
        NovaStrikeParticle particle = new NovaStrikeParticle(clientWorld, x, y, z, velocityX, velocityY, velocityZ, spriteProvider);
        particle.maxAge = 20;
        particle.scale = 0.25f;
        particle.red = 1.0f;
        particle.blue = 1.0f;
        particle.green = 0.3f;
        return particle;
    }
}
