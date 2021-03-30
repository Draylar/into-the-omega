package draylar.intotheomega.client.entity.renderer;

import dev.monarkhes.myron.api.Myron;
import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.client.entity.model.VoidMatrixModel;
import draylar.intotheomega.entity.void_matrix.VoidMatrixEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderer.geo.GeoEntityRenderer;

public class VoidMatrixEntityRenderer extends GeoEntityRenderer<VoidMatrixEntity> {

    private static final Identifier MODEL_LOCATION = IntoTheOmega.id("models/misc/void_matrix_beam");

    public VoidMatrixEntityRenderer(EntityRenderDispatcher dispatcher) {
        super(dispatcher, new VoidMatrixModel());
    }

    @Override
    public void render(GeoModel model, VoidMatrixEntity vm, float delta, RenderLayer type, MatrixStack stack, VertexConsumerProvider consumer, VertexConsumer vertexBuilder, int light, int packedOverlayIn, float red, float green, float blue, float alpha) {
        BakedModel laser = Myron.getModel(MODEL_LOCATION);

        stack.push();

//      Sync headYaw to bodyYaw (bodyYaw is not synced S2C every frame, headYaw/yaw are)
        vm.bodyYaw = vm.headYaw;

        // Fit model into hitbox
        stack.translate(0, .75, 0);

        // Rotate so eye is facing forwards
        stack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(90));

        // Rotate based on yaw
        stack.translate(0, 1.25, 0);
        stack.multiply(Vector3f.NEGATIVE_Z.getDegreesQuaternion(vm.pitch));
        stack.translate(0, -1.25, 0);

        // laser
        if(vm.isFiringLaser()) {
            float lerpedAge = MathHelper.lerp(delta, vm.age - 1, vm.age);
            renderLaser(vm, laser, consumer, stack, lerpedAge, light, delta);

            // rotate root with scale-up
            float scale = Math.min(40, MathHelper.lerp(delta, vm.getLaserTicks() - 1, vm.getLaserTicks())) / 40f;
            GeoBone root = model.getBone("root").get();
            root.setRotationX(lerpedAge * scale);
        }

        super.render(model, vm, delta, type, stack, consumer, vertexBuilder, light, packedOverlayIn, red, green, blue, alpha);
        stack.pop();

        stack.push();
//        DebugAIRenderer.render(vm, stack, consumer, packedLightIn);
        // render beam
        stack.pop();
    }

    public void renderLaser(VoidMatrixEntity vm, BakedModel model, VertexConsumerProvider vertexConsumers, MatrixStack matrices, double lerpedAge, int light, float delta) {
        if (model != null) {
            VertexConsumer consumer = vertexConsumers.getBuffer(RenderLayer.getEntitySolid(new Identifier("textures/block/white_concrete.png")));
            matrices.push();

            float lerpedLaserTicks = MathHelper.lerp(delta, vm.getLaserTicks() - 1, vm.getLaserTicks());
            float remaining = VoidMatrixEntity.MAX_LASER_TICKS - lerpedLaserTicks;
            float warmup = Math.min(40, lerpedLaserTicks) / 40f;
            float cooldown = remaining > 10 ? 1 : remaining / 10f;

            float scale = (2f + (float) Math.sin(lerpedAge / 10f) * .1f) * warmup * cooldown;
            matrices.translate(60, 1, 0);
            matrices.scale(25, scale, scale);
            matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(90));

            // render cylinder
            MatrixStack.Entry entry = matrices.peek();
            model.getQuads(null, null, vm.world.random).forEach(quad -> {
                consumer.quad(entry, quad, 1F, 1F, 1F, light, OverlayTexture.DEFAULT_UV);
            });

            matrices.pop();
        }
    }

    @Override
    public Identifier getTexture(VoidMatrixEntity entity) {
        return IntoTheOmega.id("textures/entity/void_matrix.png");
    }

    @Override
    public RenderLayer getRenderType(VoidMatrixEntity animatable, float partialTicks, MatrixStack stack, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, Identifier textureLocation) {
        return RenderLayer.getEntityTranslucent(textureLocation);
    }
}
