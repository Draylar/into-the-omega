package draylar.intotheomega.client.particle;

import draylar.intotheomega.api.particle.DirectParticle;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.ParticleEffect;

public class MatrixBlastWallParticle extends DirectParticle {

    public MatrixBlastWallParticle(SpriteProvider spriteProvider, ParticleEffect parameters, ClientWorld world, double x, double y, double z, double velX, double velY, double velZ) {
        super(spriteProvider, parameters, world, x, y, z, velX, velY, velZ);
        red = 1.0f;
        green = 0.3f;
        blue = 1.0f;
        velocityX = 0;
        velocityY = 0.0f + world.random.nextFloat() * 1.5;
        velocityZ = 0;
        scale = 3.0f;
        maxAge = 100;
    }

    @Override
    public void render(MatrixStack matrices, float delta, VertexConsumer buffer, Camera camera) {
        alpha = Math.max(0.0f, 1.0f - (age + delta) / maxAge);
        scale = 3.0f;
        faceCamera(matrices, camera);
        quad(matrices, buffer, LightmapTextureManager.MAX_LIGHT_COORDINATE);
    }

    @Override
    public ParticleTextureSheet getType() {
        return OmegaParticleSheets.TRANSLUCENT_ADDITION;
    }
}
