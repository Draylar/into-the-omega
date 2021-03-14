package draylar.intotheomega.client.entity.renderer;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.api.DebugAIRenderer;
import draylar.intotheomega.client.entity.model.OmegaSlimeEmperorModel;
import draylar.intotheomega.entity.slime.OmegaSlimeEmperorEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderer.geo.GeoEntityRenderer;

public class OmegaSlimeEmperorRenderer extends GeoEntityRenderer<OmegaSlimeEmperorEntity> {

    public OmegaSlimeEmperorRenderer(EntityRenderDispatcher dispatcher) {
        super(dispatcher, new OmegaSlimeEmperorModel());
    }

    @Override
    public void render(OmegaSlimeEmperorEntity entity, float entityYaw, float partialTicks, MatrixStack stack, VertexConsumerProvider bufferIn, int packedLightIn) {
        super.render(entity, entityYaw, partialTicks, stack, bufferIn, packedLightIn);

        stack.push();
        DebugAIRenderer.render(entity, stack, bufferIn, packedLightIn);
        stack.pop();
    }

    @Override
    public void render(GeoModel model, OmegaSlimeEmperorEntity vm, float delta, RenderLayer layer, MatrixStack matrices, VertexConsumerProvider provider, VertexConsumer consumer, int light, int overlay, float red, float green, float blue, float alpha) {
        matrices.push();
        super.render(model, vm, delta, layer, matrices, provider, consumer, light, overlay, red, green, blue, alpha);
        matrices.pop();
    }

    @Override
    public Identifier getTexture(OmegaSlimeEmperorEntity entity) {
        return IntoTheOmega.id("textures/entity/omega_slime_emperor.png");
    }
}
