package draylar.intotheomega.client.entity.feature;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.client.entity.model.EnigmaKingModel;
import draylar.intotheomega.entity.enigma.EnigmaKingEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderer.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderer.geo.IGeoRenderer;

public class EnigmaKingBladeExtensionFeatureRenderer extends GeoLayerRenderer<EnigmaKingEntity> {

    private final EnigmaKingModel model = new EnigmaKingModel();
    private final IGeoRenderer<EnigmaKingEntity> entityRendererIn;
    private final Identifier extension = IntoTheOmega.id("textures/entity/enigma_king_extension.png");

    public EnigmaKingBladeExtensionFeatureRenderer(IGeoRenderer<EnigmaKingEntity> renderer) {
        super(renderer);
        this.entityRendererIn = renderer;
    }

    @Override
    public void render(MatrixStack matrixStackIn, VertexConsumerProvider bufferIn, int packedLightIn, EnigmaKingEntity king, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!king.isInvisible()) {
            Identifier texture = getEntityTexture(king);
            RenderLayer layer = RenderLayer.getEyes(texture);
            entityRendererIn.render(model.getModel(model.getModelLocation(king)), king, partialTicks, layer, matrixStackIn, bufferIn, bufferIn.getBuffer(layer), packedLightIn, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1f);
        }
    }

    @Override
    public Identifier getEntityTexture(EnigmaKingEntity entityIn) {
        return extension;
    }
}
