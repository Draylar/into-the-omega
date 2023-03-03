package draylar.intotheomega.api.particle;

import net.minecraft.client.particle.SpriteBillboardParticle;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;

public abstract class DirectParticle extends SpriteBillboardParticle {

    protected final SpriteProvider spriteProvider;
    protected final ParticleEffect parameters;

    public DirectParticle(SpriteProvider spriteProvider, ParticleEffect parameters, ClientWorld world, double x, double y, double z, double velX, double velY, double velZ) {
            super(world, x, y, z, velX, velY, velZ);
        this.spriteProvider = spriteProvider;
        this.parameters = parameters;
        this.scale = 1.0f;
        setSpriteForAge(spriteProvider);
    }

    @Override
    public final void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float delta) {
        MatrixStack matrices = new MatrixStack();
        matrices.push();

        Vec3d cPos = camera.getPos();
        float lX = (float) (MathHelper.lerp(delta, this.prevPosX, this.x) - cPos.getX());
        float lY = (float) (MathHelper.lerp(delta, this.prevPosY, this.y) - cPos.getY());
        float lZ = (float) (MathHelper.lerp(delta, this.prevPosZ, this.z) - cPos.getZ());

        matrices.translate(lX, lY, lZ);

        float scale = getSize(delta);
        matrices.scale(scale, scale, scale);

        matrices.push();
        render(matrices, delta, vertexConsumer, camera);
        matrices.pop();
    }

    public abstract void render(MatrixStack matrices, float delta, VertexConsumer buffer, Camera camera);

    protected final void faceCamera(MatrixStack matrices, Camera camera) {
        matrices.multiply(camera.getRotation());
    }

    protected final void flat(MatrixStack matrixStack, Camera camera) {
        matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(90));
    }

    protected final float fadeIn(float delta, float completeAgePercentage) {
        return Math.min(1.0f, MathHelper.getLerpProgress(Math.min(maxAge, age + delta), 0.0f, maxAge * completeAgePercentage));
    }

    protected final float fadeOut(float delta, float startAgePercentage) {
        return 1.0f - Math.max(0.0f, MathHelper.getLerpProgress(Math.min(maxAge, age + delta), maxAge * startAgePercentage, maxAge));
    }

    protected final void quad(MatrixStack matrices, VertexConsumer buffer, int light) {
        quad(matrices, buffer, red, green, blue, alpha, light);
    }

    protected final void quad(MatrixStack matrices, VertexConsumer buffer, float red, float green, float blue, float alpha, int light) {
        Matrix4f position = matrices.peek().getPositionMatrix();
        buffer.vertex(position, -0.5f, -0.5f, 0.0f).texture(getMaxU(), getMaxV()).color(red, green, blue, alpha).light(light).next();
        buffer.vertex(position, -0.5f, 0.5f, 0.0f).texture(getMaxU(), getMinV()).color(red, green, blue, alpha).light(light).next();
        buffer.vertex(position, 0.5f, 0.5f, 0.0f).texture(getMinU(), getMinV()).color(red, green, blue, alpha).light(light).next();
        buffer.vertex(position, 0.5f, -0.5f, 0.0f).texture(getMinU(), getMaxV()).color(red, green, blue, alpha).light(light).next();
    }

    protected final void cube(MatrixStack matrices, VertexConsumer buffer, int light) {
        cube(matrices, buffer, red, green, blue, alpha, light);
    }

    protected final void cube(MatrixStack matrices, VertexConsumer buffer, float red, float green, float blue, float alpha, int light) {
        Matrix4f position = matrices.peek().getPositionMatrix();

        // north
        buffer.vertex(position, -0.5f, -0.5f, -0.5f).texture(getMaxU(), getMaxV()).color(red, green, blue, alpha).light(light).next();
        buffer.vertex(position, -0.5f, 0.5f, -0.5f).texture(getMaxU(), getMinV()).color(red, green, blue, alpha).light(light).next();
        buffer.vertex(position, 0.5f, 0.5f, -0.5f).texture(getMinU(), getMinV()).color(red, green, blue, alpha).light(light).next();
        buffer.vertex(position, 0.5f, -0.5f, -0.5f).texture(getMinU(), getMaxV()).color(red, green, blue, alpha).light(light).next();

        // south
        buffer.vertex(position, 0.5f, -0.5f, 0.5f).texture(getMinU(), getMaxV()).color(red, green, blue, alpha).light(light).next();
        buffer.vertex(position, 0.5f, 0.5f, 0.5f).texture(getMinU(), getMinV()).color(red, green, blue, alpha).light(light).next();
        buffer.vertex(position, -0.5f, 0.5f, 0.5f).texture(getMaxU(), getMinV()).color(red, green, blue, alpha).light(light).next();
        buffer.vertex(position, -0.5f, -0.5f, 0.5f).texture(getMaxU(), getMaxV()).color(red, green, blue, alpha).light(light).next();

        // east
        buffer.vertex(position, 0.5f, 0.5f, -0.5f).texture(getMinU(), getMaxV()).color(red, green, blue, alpha).light(light).next();
        buffer.vertex(position, 0.5f, 0.5f, 0.5f).texture(getMinU(), getMinV()).color(red, green, blue, alpha).light(light).next();
        buffer.vertex(position, 0.5f, -0.5f, 0.5f).texture(getMaxU(), getMinV()).color(red, green, blue, alpha).light(light).next();
        buffer.vertex(position, 0.5f, -0.5f, -0.5f).texture(getMaxU(), getMaxV()).color(red, green, blue, alpha).light(light).next();

        // west
        buffer.vertex(position, -0.5f, -0.5f, -0.5f).texture(getMaxU(), getMaxV()).color(red, green, blue, alpha).light(light).next();
        buffer.vertex(position, -0.5f, -0.5f, 0.5f).texture(getMaxU(), getMinV()).color(red, green, blue, alpha).light(light).next();
        buffer.vertex(position, -0.5f, 0.5f, 0.5f).texture(getMinU(), getMinV()).color(red, green, blue, alpha).light(light).next();
        buffer.vertex(position, -0.5f, 0.5f, -0.5f).texture(getMinU(), getMaxV()).color(red, green, blue, alpha).light(light).next();

        // top
        buffer.vertex(position, -0.5f, 0.5f, -0.5f).texture(getMaxU(), getMaxV()).color(red, green, blue, alpha).light(light).next();
        buffer.vertex(position, -0.5f, 0.5f, 0.5f).texture(getMaxU(), getMinV()).color(red, green, blue, alpha).light(light).next();
        buffer.vertex(position, 0.5f, 0.5f, 0.5f).texture(getMinU(), getMinV()).color(red, green, blue, alpha).light(light).next();
        buffer.vertex(position, 0.5f, 0.5f, -0.5f).texture(getMinU(), getMaxV()).color(red, green, blue, alpha).light(light).next();

        // bottom
        buffer.vertex(position, 0.5f, -0.5f, -0.5f).texture(getMinU(), getMaxV()).color(red, green, blue, alpha).light(light).next();
        buffer.vertex(position, 0.5f, -0.5f, 0.5f).texture(getMinU(), getMinV()).color(red, green, blue, alpha).light(light).next();
        buffer.vertex(position, -0.5f, -0.5f, 0.5f).texture(getMaxU(), getMinV()).color(red, green, blue, alpha).light(light).next();
        buffer.vertex(position, -0.5f, -0.5f, -0.5f).texture(getMaxU(), getMaxV()).color(red, green, blue, alpha).light(light).next();
    }
}