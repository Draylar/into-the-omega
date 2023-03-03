package draylar.intotheomega.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.MathHelper;

public class StarfallNodeParticle extends SpriteBillboardParticle {

    public StarfallNodeParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteProvider spriteProvider) {
        super(world, x, y, z);
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.velocityZ = velocityZ;
        this.collidesWithWorld = false;
        maxAge = 40;
        this.setSpriteForAge(spriteProvider);
    }

    @Override
    public void tick() {
        super.tick();

        scale = ((float) age / maxAge) * 1.0f;
        prevAngle = angle;
        angle += 0.1;

        alpha = MathHelper.clamp(1 - (float) age / maxAge, 0.0f, 1.0f);

        if(onGround) {
            markDead();
        }
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
            return StarfallNodeParticle.create(defaultParticleType, clientWorld, x, y, z, velocityX, velocityY, velocityZ, spriteProvider);
        }
    }

    private static StarfallNodeParticle create(DefaultParticleType type, ClientWorld clientWorld, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteProvider spriteProvider) {
        StarfallNodeParticle particle = new StarfallNodeParticle(clientWorld, x, y, z, velocityX, velocityY, velocityZ, spriteProvider);
        particle.maxAge = 40;
        particle.scale = 0;
        return particle;
    }
}
