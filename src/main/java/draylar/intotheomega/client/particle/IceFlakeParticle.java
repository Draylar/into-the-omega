package draylar.intotheomega.client.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class IceFlakeParticle extends SpriteBillboardParticle {

    private boolean reachedGround;
    private final SpriteProvider spriteProvider;

    public IceFlakeParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteProvider spriteProvider) {
        super(world, x, y, z);
        this.velocityX = velocityX * .025;
        this.velocityY = velocityY * .025;
        this.velocityZ = velocityZ * .025;
        this.colorRed = 1;
        this.colorGreen = 1;
        this.colorBlue = 1;
        this.scale *= 1;
        this.maxAge = 15 + world.random.nextInt(5);
        this.reachedGround = false;
        this.collidesWithWorld = false;
        this.spriteProvider = spriteProvider;
        this.setSpriteForAge(spriteProvider);
    }

    @Override
    public void tick() {
        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;
        this.scale += 0.01;

        // tick down transparency
        if(this.age + 3 >= this.maxAge) {
            int remaining = this.maxAge - this.age;
            this.colorAlpha = 1 - 1f / remaining;
        }

        if (this.age++ >= this.maxAge) {
            this.markDead();
        } else {
            this.setSpriteForAge(this.spriteProvider);
            if (this.onGround) {
                this.velocityY = 0.0D;
                this.reachedGround = true;
            }

            if (this.reachedGround) {
                this.velocityY += 0.002D;
            }

            this.move(this.velocityX, this.velocityY, this.velocityZ);
            if (this.y == this.prevPosY) {
                this.velocityX *= 1.1D;
                this.velocityZ *= 1.1D;
            }

            this.velocityX *= 0.9599999785423279D;
            this.velocityZ *= 0.9599999785423279D;
            if (this.reachedGround) {
                this.velocityY *= 0.9599999785423279D;
            }

        }
    }

    @Override
    public ParticleTextureSheet getType() {
        return OmegaParticle.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public float getSize(float tickDelta) {
        return this.scale * MathHelper.clamp(((float)this.age + tickDelta) / (float)this.maxAge * 32.0F, 0.0F, 1.0F) ;
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<DefaultParticleType> {

        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            return new IceFlakeParticle(clientWorld, d, e, f, g, h, i, this.spriteProvider);
        }
    }
}
