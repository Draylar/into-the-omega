package draylar.intotheomega.client.entity.renderer;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.api.client.BoxRendering;
import draylar.intotheomega.entity.nova.NovaGroundBurstEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class NovaGroundBurstRenderer extends EntityRenderer<NovaGroundBurstEntity> {

    private static final Identifier TEXTURE = IntoTheOmega.id("textures/entity/nova_ground_burst.png");

    public NovaGroundBurstRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public void render(NovaGroundBurstEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);

        matrices.push();
        matrices.scale(8f, 1000f, 8f);
        matrices.translate(0, 1.0001f, 0);
        BoxRendering.renderBox(matrices, vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(getTexture(entity))), 1.0f, light, 1.0f, 1.0f, 1.0f, 0.5f);
        matrices.pop();
    }

    @Override
    public Identifier getTexture(NovaGroundBurstEntity entity) {
        return TEXTURE;
    }
}
