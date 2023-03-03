package draylar.intotheomega.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class VariantFusionParticle extends SpriteBillboardParticle {

    private boolean reachedGround;
    private final SpriteProvider spriteProvider;

    public VariantFusionParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteProvider spriteProvider) {
        super(world, x, y, z);
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.velocityZ = velocityZ;
        red = MathHelper.nextFloat(this.random, 0.7176471F, 0.8745098F);
        green = MathHelper.nextFloat(this.random, 0.0F, 0.0F);
        blue = MathHelper.nextFloat(this.random, 0.8235294F, 0.9764706F);
        scale *= 1;
        maxAge = 10 + world.random.nextInt(5);
        reachedGround = false;
        collidesWithWorld = false;
        this.spriteProvider = spriteProvider;
        setSpriteForAge(spriteProvider);
    }

    @Override
    public void tick() {
        prevPosX = x;
        prevPosY = y;
        prevPosZ = z;
        if (age++ >= maxAge) {
            markDead();
        } else {
            setSpriteForAge(this.spriteProvider);
            if (onGround) {
                velocityY = 0.0D;
                reachedGround = true;
            }

            if (reachedGround) {
                velocityY += 0.002D;
            }

            move(velocityX, velocityY, velocityZ);
            if (y == prevPosY) {
                velocityX *= 1.1D;
                velocityZ *= 1.1D;
            }

            velocityX *= 0.9599999785423279D;
            velocityZ *= 0.9599999785423279D;
            if (reachedGround) {
                velocityY *= 0.9599999785423279D;
            }
        }
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
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
            return new VariantFusionParticle(clientWorld, d, e, f, g, h, i, this.spriteProvider);
        }
    }
}
