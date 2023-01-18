package draylar.intotheomega.client.particle;

import draylar.intotheomega.api.client.OmegaVertexFormats;
import draylar.intotheomega.api.client.VertexWrapper;
import draylar.intotheomega.api.particle.DirectParticle;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

public class LengthExpandIndicatorParticle extends DirectParticle {

    private final double angle;
    private final double width;
    private final double depth;

    private LengthExpandIndicatorParticle(SpriteProvider spriteProvider, DefaultParticleType parameters, ClientWorld world, double x, double y, double z, double velX, double velY, double velZ) {
        super(spriteProvider, parameters, world, x, y, z, 0.0, 0.0, 0.0);
        maxAge = 120;

        if(velX == 0) {
            width = 5f;
        } else {
            width = velX;
        }

        if(velY == 0) {
            depth = 50f;
        } else {
            depth = velY;
        }

        if(velZ == 0) {
            this.angle = 0;
        } else {
            this.angle = velZ;
        }

        this.velocityX = 0;
        this.velocityY = 0;
        this.velocityZ = 0;
    }

    @Override
    public void render(MatrixStack matrices, float delta, VertexConsumer buffer, Camera camera) {
        matrices.translate(0, 0.05, 0);
        flat(matrices, camera);
        matrices.scale(10f, 30f, 30f);

        float ageProgress = (age + delta) / (float) maxAge;
        float fade = fadeOut(delta, 0.75f);

        VertexWrapper wrapper = VertexWrapper.wrap(buffer, OmegaVertexFormats.POSITION_TEXTURE_COLOR_LIGHT_PROGRESS);
        wrapper.global(OmegaVertexFormats.FLOAT, Math.min(1.0f, ageProgress * 2.0f));
        quad(matrices, wrapper, 1.0f, 0.0f, 1.0f, fade, LightmapTextureManager.MAX_LIGHT_COORDINATE);
    }

    @Override
    protected float getMaxU() {
        return 1.0f;
    }

    @Override
    protected float getMinU() {
        return 0.0f;
    }

    @Override
    protected float getMaxV() {
        return 1.0f;
    }

    @Override
    protected float getMinV() {
        return 0.0f;
    }

    @Override
    public ParticleTextureSheet getType() {
        return OmegaParticleSheets.PROGRESS_TRANSLUCENT_LENGTH_EXPAND;
    }

    public static class VoidMatrixSlam extends LengthExpandIndicatorParticle {

        public VoidMatrixSlam(SpriteProvider provider, DefaultParticleType parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            super(provider, parameters, world, x, y, z, velocityX, velocityY, velocityZ);
        }
    }
}