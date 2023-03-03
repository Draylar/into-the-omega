package draylar.intotheomega.client.entity.renderer;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.client.entity.model.MatriteEntityModel;
import draylar.intotheomega.entity.matrite.MatriteEntity;
import draylar.intotheomega.registry.OmegaEntityModelLayers;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class MatriteEntityRenderer extends EntityRenderer<MatriteEntity> {

    private final MatriteEntityModel model;

    public MatriteEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
        model = new MatriteEntityModel(context.getPart(OmegaEntityModelLayers.MATRITE));
    }

    @Override
    public void render(MatriteEntity matrite, float yaw, float delta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        light = LightmapTextureManager.MAX_LIGHT_COORDINATE;

        matrices.push();


        float lerpedAge = matrite.age + delta;
        float initialScale = Math.min(1.0f, lerpedAge / 20f);
        matrices.scale(initialScale, initialScale, initialScale);

        matrices.translate(0, -1.05, 0);

        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(model.getLayer(this.getTexture(matrite)));

        // If the matrite is idle, it should slowly float up and down.
        // IF the matrite is not idle (flying), it should spin.
        if(matrite.idle()) {
            matrices.translate(0, Math.sin(MathHelper.lerp(delta, matrite.age - 1, matrite.age) / 10) * .1, 0);
            model.setAngles(matrite, 0, 0, 0, matrite.getHeadYaw(), matrite.getPitch());
        } else {
            model.setAngles(matrite, 0, 0, MathHelper.lerp(delta, matrite.age - 1, matrite.age), matrite.getHeadYaw(), matrite.getPitch());
        }

        // render matrite model
        this.model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);

        matrices.pop();
        super.render(matrite, yaw, delta, matrices, vertexConsumers, light);
    }

    @Override
    public Identifier getTexture(MatriteEntity entity) {
        return IntoTheOmega.id("textures/entity/matrite.png");
    }
}
