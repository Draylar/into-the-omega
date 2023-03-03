package draylar.intotheomega.particle;

import draylar.intotheomega.api.client.OmegaVertexFormats;
import draylar.intotheomega.api.client.VertexWrapper;
import draylar.intotheomega.api.particle.DirectParticle;
import draylar.intotheomega.vfx.particle.option.LengthExpandParticleEffect;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.Vec3f;

public class LengthExpandIndicatorParticle extends DirectParticle {

    private final double width;
    private final double depth;
    private final double angle;

    public LengthExpandIndicatorParticle(SpriteProvider spriteProvider, LengthExpandParticleEffect parameters, ClientWorld world, double x, double y, double z, double velX, double velY, double velZ) {
        super(spriteProvider, parameters, world, x, y, z, 0.0, 0.0, 0.0);

        width = parameters.width();
        depth = parameters.depth();
        angle = parameters.angle();
        maxAge = parameters.duration();
        red = (float) (parameters.color() >> 16 & 0xFF) / 255.0f;
        green = (float) (parameters.color() >> 8 & 0xFF) / 255.0f;
        blue = (float) (parameters.color() & 0xFF) / 255.0f;

        velocityX = 0;
        velocityY = 0;
        velocityZ = 0;
    }

    @Override
    public void render(MatrixStack matrices, float delta, VertexConsumer buffer, Camera camera) {
        matrices.translate(0, 0.05, 0);
        flat(matrices, camera);
        matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion((float) angle));
        matrices.scale((float) width, (float) depth, 1f);

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
}
