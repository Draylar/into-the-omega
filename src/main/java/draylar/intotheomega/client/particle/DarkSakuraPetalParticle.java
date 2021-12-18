package draylar.intotheomega.client.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

public class DarkSakuraPetalParticle extends SpriteBillboardParticle {

    private float angleSpeed = 0.0f;

    public DarkSakuraPetalParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteProvider spriteProvider) {
        super(world, x, y, z);
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.velocityZ = velocityZ;
        this.colorRed = 1;
        this.colorGreen = 1;
        this.colorBlue = 1;
        this.scale *= 1;
        this.maxAge = 250;
        this.collidesWithWorld = false;
        this.setSpriteForAge(spriteProvider);
        angleSpeed = 0.05f * world.random.nextFloat();
    }

    @Override
    public void tick() {
        super.tick();

        velocityY = Math.max(-0.03f, velocityY - 0.001f);
        velocityX = Math.sin((age / (float) maxAge) * 15) / 25f;

        prevAngle = angle;
        angle += angleSpeed;

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
            return new DarkSakuraPetalParticle(clientWorld, x, y, z, velocityX, velocityY, velocityZ, this.spriteProvider);
        }
    }
}
