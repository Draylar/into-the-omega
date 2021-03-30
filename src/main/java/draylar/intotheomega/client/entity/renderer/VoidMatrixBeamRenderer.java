package draylar.intotheomega.client.entity.renderer;

import dev.monarkhes.myron.api.Myron;
import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.entity.void_matrix.VoidMatrixBeamEntity;
import draylar.intotheomega.registry.OmegaParticles;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.Random;

public class VoidMatrixBeamRenderer extends EntityRenderer<VoidMatrixBeamEntity> {

    private static final Identifier MODEL_LOCATION = IntoTheOmega.id("models/misc/void_matrix_beam");

    public VoidMatrixBeamRenderer(EntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(VoidMatrixBeamEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        BakedModel model = Myron.getModel(MODEL_LOCATION);
        double lerpedAge = MathHelper.lerp(tickDelta, entity.age - 1, entity.age);
        Camera camera = dispatcher.camera;

        if (model != null) {
            VertexConsumer consumer = vertexConsumers.getBuffer(RenderLayer.getEyes(new Identifier("textures/block/white_concrete.png")));
            matrices.push();
            matrices.scale(.25f, 50, .25f);

            // For the first second, the Void Matrix Beam slowly expands out as a white beam in the world.
            float scaleUp = (float) Math.min(1, Math.sqrt(lerpedAge) / 4.5) * .5f;
            matrices.scale(scaleUp, 1, scaleUp);

            // After the 2 seconds, a second beam shoots out
            if(lerpedAge > 40) {
                float scale = MathHelper.clamp((float) (1 - (41 - lerpedAge)), 0, 8);
                matrices.scale(scale, 1, scale);
            }

            // After 40 seconds, play particles going down
            if(lerpedAge >= 38 && lerpedAge <= VoidMatrixBeamEntity.DEATH_TIME - 5) {
                Vec3d pos = entity.getPos();
                Random rand = entity.world.random;

                for(double i = -25; i <= 25; i += 0.5) {
                    double velX = (1 - rand.nextInt(3)) * .1;
                    double velZ = (1 - rand.nextInt(3)) * .1;
                    entity.world.addParticle(OmegaParticles.MATRIX_EXPLOSION, true, pos.getX(), pos.getY() + i, pos.getZ(), velX, 0, velZ);
                }
            }

            // last second, beam goes down
            if(lerpedAge >= VoidMatrixBeamEntity.DEATH_TIME - 5) {
                float remaining = Math.max(0, (float) (VoidMatrixBeamEntity.DEATH_TIME - lerpedAge) / 5);
                matrices.scale(remaining, 1, remaining);
            }

            // render cylinder
            MatrixStack.Entry entry = matrices.peek();
            model.getQuads(null, null, entity.world.random).forEach(quad -> {
                consumer.quad(entry, quad, 1F, 1F, 1F, light, OverlayTexture.DEFAULT_UV);
            });

            matrices.pop();
        }
    }

    @Override
    public Identifier getTexture(VoidMatrixBeamEntity entity) {
        return null;
    }
}
