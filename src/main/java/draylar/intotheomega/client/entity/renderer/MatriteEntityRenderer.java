package draylar.intotheomega.client.entity.renderer;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.client.entity.model.MatriteEntityModel;
import draylar.intotheomega.entity.matrite.MatriteEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class MatriteEntityRenderer extends EntityRenderer<MatriteEntity> {

    private final MatriteEntityModel model = new MatriteEntityModel();

    public MatriteEntityRenderer(EntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(MatriteEntity matrite, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();

        matrices.translate(0, -1.05, 0);

        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(this.model.getLayer(this.getTexture(matrite)));

        // If the matrite is idle, it should slowly float up and down.
        // IF the matrite is not idle (flying), it should spin.
        if(matrite.idle()) {
            matrices.translate(0, Math.sin(MathHelper.lerp(tickDelta, matrite.age - 1, matrite.age) / 10) * .1, 0);
            model.setAngles(matrite, 0, 0, 0, matrite.getHeadYaw(), matrite.pitch);
        } else {
            model.setAngles(matrite, 0, 0, MathHelper.lerp(tickDelta, matrite.age - 1, matrite.age), matrite.getHeadYaw(), matrite.pitch);
        }

        // render matrite model
        this.model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);

        matrices.pop();
        super.render(matrite, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public Identifier getTexture(MatriteEntity entity) {
        return IntoTheOmega.id("textures/entity/matrite.png");
    }
}
