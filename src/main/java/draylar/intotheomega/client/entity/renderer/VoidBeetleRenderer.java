package draylar.intotheomega.client.entity.renderer;

import draylar.intotheomega.client.entity.model.VoidBeetleModel;
import draylar.intotheomega.entity.VoidBeetleEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class VoidBeetleRenderer extends GeoEntityRenderer<VoidBeetleEntity> {

    public VoidBeetleRenderer(EntityRendererFactory.Context context) {
        super(context, new VoidBeetleModel());
    }

    @Override
    public void render(VoidBeetleEntity beetle, float yaw, float delta, MatrixStack matrices, VertexConsumerProvider vertexProvider, int light) {
        matrices.scale(1.5f, 1.5f, 1.5f);
        super.render(beetle, yaw, delta, matrices, vertexProvider, light);
    }
}
