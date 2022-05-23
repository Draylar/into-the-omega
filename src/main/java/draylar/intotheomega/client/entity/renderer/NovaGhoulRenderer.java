package draylar.intotheomega.client.entity.renderer;

import draylar.intotheomega.client.entity.model.NovaGhoulModel;
import draylar.intotheomega.entity.nova.NovaGhoulEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class NovaGhoulRenderer extends GeoEntityRenderer<NovaGhoulEntity> {

    public NovaGhoulRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new NovaGhoulModel());
    }

    @Override
    public void render(NovaGhoulEntity entity, float yaw, float delta, MatrixStack matrices, VertexConsumerProvider bufferProvider, int light) {
        matrices.push();
        matrices.scale(1.5f, 1.5f, 1.5f);
        matrices.translate(0, 0.25f + Math.sin(GLFW.glfwGetTime() * 2) * 0.25f, 0);
        super.render(entity, yaw, delta, matrices, bufferProvider, light);
        matrices.pop();
    }

    @Override
    public RenderLayer getRenderType(NovaGhoulEntity ghoul, float delta, MatrixStack matrices, VertexConsumerProvider bufferProvider, VertexConsumer buffer, int light, Identifier texture) {
        return RenderLayer.getEntityTranslucentCull(texture);
    }
}
