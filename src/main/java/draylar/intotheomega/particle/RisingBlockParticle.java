package draylar.intotheomega.particle;

import draylar.intotheomega.api.Easing;
import draylar.intotheomega.api.particle.DirectParticle;
import draylar.intotheomega.vfx.particle.option.RisingBlockParticleEffect;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.Vec3f;

public class RisingBlockParticle extends DirectParticle {

    private final RisingBlockParticleEffect parameters;
    private final Sprite sprite;

    public RisingBlockParticle(SpriteProvider spriteProvider, RisingBlockParticleEffect parameters, ClientWorld world, double x, double y, double z, double velX, double velY, double velZ) {
        super(spriteProvider, parameters, world, x, y, z, velX, velY, velZ);

        this.parameters = parameters;
        this.scale = parameters.scaleIn() == 0.0 ? 1.0f : 0.0f;
        this.scale = 1.0f;
        this.maxAge = parameters.duration();
        this.sprite = MinecraftClient.getInstance().getBlockRenderManager().getModels().getModelParticleSprite(parameters.blockState());

        this.velocityX = parameters.velocityX();
        this.velocityY = parameters.velocityY();
        this.velocityZ = parameters.velocityZ();
    }

    @Override
    public void render(MatrixStack matrices, float delta, VertexConsumer buffer, Camera camera) {
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion((float) parameters.rotationSpeed() * (age + delta)));
        matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion((float) parameters.rotationSpeed() * ((age + delta) + 2814.5f)));
        matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion((float) parameters.rotationSpeed() * ((age + delta) + 315583.f)));

        if(parameters.scaleIn() != 0.0) {
            float fade = fadeIn(delta, (float) parameters.scaleIn() / (float) maxAge);
            matrices.scale(fade, fade, fade);
        }

        if(parameters.scaleOut() != 0.0) {
            float fade = Easing.EASE_OUT_5EXP.apply(1.0f - fadeOut(delta, (float) parameters.scaleOut() / (float) maxAge));
            matrices.scale(fade, fade, fade);
        }

        cube(matrices, buffer, LightmapTextureManager.MAX_LIGHT_COORDINATE);
    }

    @Override
    public float getMinU() {
        return sprite.getMinU();
    }

    @Override
    public float getMaxU() {
        return sprite.getMaxU();
    }

    @Override
    public float getMinV() {
        return sprite.getMinV();
    }

    @Override
    public float getMaxV() {
        return sprite.getMaxV();
    }

    @Override
    public ParticleTextureSheet getType() {
         return OmegaParticleSheets.BLOCK;
    }
}
