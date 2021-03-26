package draylar.intotheomega.client.entity.renderer;

import dev.monarkhes.myron.api.Myron;
import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.entity.AbyssalRiftEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class AbyssalRiftEntityRenderer extends EntityRenderer<AbyssalRiftEntity> {

    private static final Identifier MODEL_LOCATION = IntoTheOmega.id("models/misc/abyssal_rift");

    public AbyssalRiftEntityRenderer(EntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(AbyssalRiftEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        BakedModel model = Myron.getModel(MODEL_LOCATION);
        double lerpedAge = MathHelper.lerp(tickDelta, entity.age - 1, entity.age);

        if (model != null) {
            VertexConsumer consumer = vertexConsumers.getBuffer(RenderLayer.getSolid());
            matrices.push();
            matrices.scale(7, 7, 7);
            matrices.translate(0, Math.sin(lerpedAge / 25) * .5, 0);

            MatrixStack.Entry entry = matrices.peek();
            model.getQuads(null, null, entity.world.random).forEach(quad -> {
                consumer.quad(entry, quad, 1F, 1F, 1F, light, OverlayTexture.DEFAULT_UV);
            });

            matrices.pop();
        }
    }

    @Override
    public Identifier getTexture(AbyssalRiftEntity entity) {
        return null;
    }
}
