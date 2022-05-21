package draylar.intotheomega.client.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;
import org.jetbrains.annotations.Nullable;

public class OriginNovaParticle extends SpriteBillboardParticle {
    
    private float prevRed = 0.0f;
    private float prevGreen = 0.0f;
    private float prevBlue = 0.0f;

    public OriginNovaParticle(ClientWorld clientWorld, double d, double e, double f) {
        super(clientWorld, d, e, f);
    }

    @Override
    public void tick() {
        super.tick();
        
        prevRed = red;
        prevBlue = blue;
        prevGreen = green;

        float progress = (float) age / maxAge;
        float inverseProgress = 1.0f - progress;
        float progressScalar = 1.0f + progress;

        prevAngle = angle;
        angle += 0.1;

        float color = 1.0f - (float) Math.sin(((age / (float) maxAge)));

        if(age >= maxAge - 40) {
            if(age >= maxAge - 20) {
                scale -= 0.4;
                float scale = ((maxAge - age) / 20f);
            }
            
            red = (float) Math.min(1.0f, red + 0.05);
            green = (float) Math.min(1.0f, green + 0.05);
            blue = (float) Math.min(1.0f, blue + 0.05);
        } else {
            scale += 0.3;
            if(age <= 15) {
                float scalar = 1.0f - age / 15f;
                red = scalar;
                blue = scalar;
                green = scalar;
            } else if(age <= 40) {
                float scalar = (age - 15) / 25f;
                red = scalar * color;
                blue = scalar * color;
                green = scalar * color * 0.15f;
            } else {
                red = color;
                blue = color;
                green = color * 0.15f;
            }
        }
    }

    @Override
    public void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {
        Vec3d cameraPos = camera.getPos();
        float x = (float) (MathHelper.lerp(tickDelta, prevPosX, this.x) - cameraPos.getX());
        float y = (float) (MathHelper.lerp(tickDelta, prevPosY, this.y) - cameraPos.getY());
        float z = (float) (MathHelper.lerp(tickDelta, prevPosZ, this.z) - cameraPos.getZ());

        Quaternion quaternion;
        if (this.angle == 0.0f) {
            quaternion = camera.getRotation();
        } else {
            quaternion = new Quaternion(camera.getRotation());
            float i = MathHelper.lerp(tickDelta, this.prevAngle, this.angle);
            quaternion.hamiltonProduct(Vec3f.POSITIVE_Z.getRadialQuaternion(i));
        }

        float offset = age / (float) maxAge;
        Vec3d awayFromCamera = camera.getPos().subtract(this.x, this.y, this.z).normalize().multiply(offset);
        Vec3f[] vec3fs = new Vec3f[]{new Vec3f(-1.0f, -1.0f, 0.0f), new Vec3f(-1.0f, 1.0f, 0.0f), new Vec3f(1.0f, 1.0f, 0.0f), new Vec3f(1.0f, -1.0f, 0.0f)};
        float j = this.getSize(tickDelta);
        for (int k = 0; k < 4; ++k) {
            Vec3f vec3f2 = vec3fs[k];
            vec3f2.rotate(quaternion);
            vec3f2.scale(j);
            vec3f2.add(x, y, z);

            // Offset from camera for layering effect
            vec3f2.add(-(float) awayFromCamera.getX(), -(float) awayFromCamera.getY(), -(float) awayFromCamera.getZ());
        }

        float lerpedRed = MathHelper.lerp(tickDelta, prevRed, red);
        float lerpedGreen = MathHelper.lerp(tickDelta, prevGreen, green);
        float lerpedBlue = MathHelper.lerp(tickDelta, prevBlue, blue);
        
        float minU = getMinU();
        float maxU = getMaxU();
        float minV = getMinV();
        float maxV = getMaxV();
        int light = LightmapTextureManager.MAX_BLOCK_LIGHT_COORDINATE;
        vertexConsumer.vertex(vec3fs[0].getX(), vec3fs[0].getY(), vec3fs[0].getZ()).texture(maxU, maxV).color(lerpedRed, lerpedGreen, lerpedBlue, this.alpha).light(light).next();
        vertexConsumer.vertex(vec3fs[1].getX(), vec3fs[1].getY(), vec3fs[1].getZ()).texture(maxU, minV).color(lerpedRed, lerpedGreen, lerpedBlue, this.alpha).light(light).next();
        vertexConsumer.vertex(vec3fs[2].getX(), vec3fs[2].getY(), vec3fs[2].getZ()).texture(minU, minV).color(lerpedRed, lerpedGreen, lerpedBlue, this.alpha).light(light).next();
        vertexConsumer.vertex(vec3fs[3].getX(), vec3fs[3].getY(), vec3fs[3].getZ()).texture(minU, maxV).color(lerpedRed, lerpedGreen, lerpedBlue, this.alpha).light(light).next();
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static class Factory implements ParticleFactory<DefaultParticleType> {

        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Nullable
        @Override
        public Particle createParticle(DefaultParticleType parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return OriginNovaParticle.createParticle(parameters, world, x, y, z, velocityX, velocityY, velocityZ, spriteProvider);
        }
    }

    private static Particle createParticle(DefaultParticleType parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteProvider spriteProvider) {
        OriginNovaParticle particle = new OriginNovaParticle(world, x, y, z);
        particle.setSpriteForAge(spriteProvider);
        particle.maxAge = 100;
        particle.scale = 0;
        return particle;
    }
}
