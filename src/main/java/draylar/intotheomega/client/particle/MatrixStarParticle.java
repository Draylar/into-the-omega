package draylar.intotheomega.client.particle;

import draylar.intotheomega.api.Easing;
import draylar.intotheomega.api.particle.DirectParticle;
import draylar.intotheomega.registry.OmegaParticles;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.Vec3f;

public class MatrixStarParticle extends DirectParticle {

    public MatrixStarParticle(SpriteProvider spriteProvider, ParticleEffect parameters, ClientWorld world, double x, double y, double z, double velX, double velY, double velZ) {
        super(spriteProvider, parameters, world, x, y, z, velX, velY, velZ);
        maxAge = 30;
        scale = 0;
        velocityX = 0;
        velocityY = 0.1;
        velocityZ = 0;
    }

    @Override
    public void render(MatrixStack matrices, float delta, VertexConsumer buffer, Camera camera) {
        float progress = (age + delta) / maxAge;

        this.scale = Easing.EASE_IN_CUBIC.apply(progress) * 2.0f * Easing.EASE_OUT_5EXP.apply(progress);
        float rotation = Easing.EASE_IN_CUBIC.apply(progress);

        red = 1.0f;
        green = 0.95f + progress * 0.05f;
        blue = 1.0f;

        faceCamera(matrices, camera);

        matrices.push();
        matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(rotation * 100));
        quad(matrices, buffer, LightmapTextureManager.MAX_LIGHT_COORDINATE);
        matrices.pop();
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public ParticleTextureSheet getType() {
        return OmegaParticle.PARTICLE_SHEET_TRANSLUCENT;
    }
}
