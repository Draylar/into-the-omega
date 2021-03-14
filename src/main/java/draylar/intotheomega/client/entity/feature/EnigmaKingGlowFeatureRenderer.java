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

public class EnigmaKingGlowFeatureRenderer extends GeoLayerRenderer<EnigmaKingEntity> {

    private final EnigmaKingModel model = new EnigmaKingModel();
    private final IGeoRenderer<EnigmaKingEntity> entityRendererIn;
    private final Identifier defaultTexture = IntoTheOmega.id("textures/entity/enigma_king_glow.png");
    private final Identifier rageTexture = IntoTheOmega.id("textures/entity/enigma_king_rage_glow.png");

    public EnigmaKingGlowFeatureRenderer(IGeoRenderer<EnigmaKingEntity> renderer) {
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
    protected Identifier getEntityTexture(EnigmaKingEntity entityIn) {
        if(entityIn.getHealth() < entityIn.getMaxHealth() / 4) {
            return rageTexture;
        } else {
            return defaultTexture;
        }
    }
}
