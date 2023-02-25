package draylar.intotheomega.entity.void_matrix.beam.particle;

import draylar.intotheomega.api.particle.DirectParticle;
import draylar.intotheomega.client.particle.OmegaParticle;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

public class VoidBeamDustParticle extends DirectParticle {

    public VoidBeamDustParticle(SpriteProvider spriteProvider, DefaultParticleType parameters, ClientWorld world, double x, double y, double z, double velX, double velY, double velZ) {
        super(spriteProvider, parameters, world, x, y, z, velX, velY, velZ);
        maxAge = 30;
        scale = 1.0f;
        velocityY = 0.0;
        velocityX = velX;
        velocityZ = velZ;
    }

    @Override
    public void render(MatrixStack matrices, float delta, VertexConsumer buffer, Camera camera) {
        scale =  (1.0f - (age - delta) / maxAge) * 0.5f;

        faceCamera(matrices, camera);
        quad(matrices, buffer, LightmapTextureManager.MAX_LIGHT_COORDINATE);
    }

    @Override
    public ParticleTextureSheet getType() {
        return OmegaParticle.PARTICLE_SHEET_TRANSLUCENT;
    }
}
