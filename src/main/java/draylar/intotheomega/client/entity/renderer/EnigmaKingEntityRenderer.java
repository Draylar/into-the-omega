package draylar.intotheomega.client.entity.renderer;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.api.DebugAIRenderer;
import draylar.intotheomega.api.PathRenderer;
import draylar.intotheomega.client.entity.feature.EnigmaKingBladeExtensionFeatureRenderer;
import draylar.intotheomega.client.entity.feature.EnigmaKingGlowFeatureRenderer;
import draylar.intotheomega.client.entity.model.EnigmaKingModel;
import draylar.intotheomega.entity.enigma.EnigmaKingEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderer.geo.GeoEntityRenderer;

public class EnigmaKingEntityRenderer extends GeoEntityRenderer<EnigmaKingEntity> {

    public EnigmaKingEntityRenderer(EntityRenderDispatcher dispatcher) {
        super(dispatcher, new EnigmaKingModel());
        addLayer(new EnigmaKingGlowFeatureRenderer(this));
//        addLayer(new EnigmaKingBladeExtensionFeatureRenderer(this));
    }

    @Override
    public void render(EnigmaKingEntity entity, float entityYaw, float partialTicks, MatrixStack stack, VertexConsumerProvider bufferIn, int packedLightIn) {
        super.render(entity, entityYaw, partialTicks, stack, bufferIn, packedLightIn);

        stack.push();
        DebugAIRenderer.render(entity, stack, bufferIn, packedLightIn);
        PathRenderer.render(entity, stack, bufferIn, packedLightIn);
        stack.pop();
    }

    @Override
    public void render(GeoModel model, EnigmaKingEntity vm, float delta, RenderLayer layer, MatrixStack matrices, VertexConsumerProvider provider, VertexConsumer consumer, int light, int overlay, float red, float green, float blue, float alpha) {
        matrices.push();
        matrices.scale(1.25f, 1.25f, 1.25f);
        super.render(model, vm, delta, layer, matrices, provider, consumer, light, overlay, red, green, blue, alpha);
        matrices.pop();
    }

    @Override
    public Identifier getTexture(EnigmaKingEntity entity) {
        return IntoTheOmega.id("textures/entity/enigma_king.png");
    }
}
