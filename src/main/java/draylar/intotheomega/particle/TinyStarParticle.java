package draylar.intotheomega.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.MathHelper;

public class TinyStarParticle extends SpriteBillboardParticle {

    private final SpriteProvider spriteProvider;

    public TinyStarParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteProvider spriteProvider) {
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
        this.setSpriteForAge(spriteProvider);

        alpha = MathHelper.clamp((float) Math.sin(Math.PI * (age / (float) maxAge)), 0.0f, 1.0f);
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<DefaultParticleType> {

        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return TinyStarParticle.create(defaultParticleType, clientWorld, x, y, z, velocityX, velocityY, velocityZ, spriteProvider);
        }
    }

    private static TinyStarParticle create(DefaultParticleType type, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteProvider spriteProvider) {
        TinyStarParticle particle = new TinyStarParticle(world, x, y, z, velocityX, velocityY, velocityZ, spriteProvider);
        particle.maxAge = 60 + world.random.nextInt(30);
        particle.scale = 0.1f;
        particle.alpha = 0.0f;
        return particle;
    }
}
