package draylar.intotheomega.client.entity.renderer;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.client.entity.model.MatrixBombEntityModel;
import draylar.intotheomega.entity.MatrixBombEntity;
import draylar.intotheomega.registry.OmegaEntityModelLayers;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class MatrixBombEntityRenderer extends EntityRenderer<MatrixBombEntity> {

    public final MatrixBombEntityModel model;

    public MatrixBombEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
        model = new MatrixBombEntityModel(context.getPart(OmegaEntityModelLayers.MATRITE));
    }

    @Override
    public void render(MatrixBombEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();

        matrices.translate(0, -1.05, 0);

        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(this.model.getLayer(this.getTexture(entity)));
        model.setAngles(entity, 0, 0, MathHelper.lerp(tickDelta, entity.age - 1, entity.age), entity.getHeadYaw(), entity.getPitch());
        model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);

        matrices.pop();
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }



    @Override
    public Identifier getTexture(MatrixBombEntity entity) {
        return IntoTheOmega.id("textures/entity/matrite.png");
    }
}
