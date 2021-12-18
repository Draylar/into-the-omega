package draylar.intotheomega.client.entity.renderer;

import draylar.intotheomega.client.entity.feature.EnigmaKingGlowFeatureRenderer;
import draylar.intotheomega.client.entity.feature.LeafMonsterFeature;
import draylar.intotheomega.client.entity.model.LeafMonsterModel;
import draylar.intotheomega.entity.EmptyLeafMonsterEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderer.geo.GeoEntityRenderer;

public class LeafMonsterRenderer extends GeoEntityRenderer<EmptyLeafMonsterEntity> {

    public LeafMonsterRenderer(EntityRenderDispatcher renderManager) {
        super(renderManager, new LeafMonsterModel());
        addLayer(new LeafMonsterFeature(this));
    }

    @Override
    public void render(EmptyLeafMonsterEntity entity, float entityYaw, float partialTicks, MatrixStack stack, VertexConsumerProvider bufferIn, int packedLightIn) {
        super.render(entity, entityYaw, partialTicks, stack, bufferIn, packedLightIn);
    }

    @Override
    public void render(GeoModel model, EmptyLeafMonsterEntity animatable, float partialTicks, RenderLayer type, MatrixStack matrices, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        matrices.push();
        super.render(model, animatable, partialTicks, type, matrices, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        matrices.pop();
    }
}
