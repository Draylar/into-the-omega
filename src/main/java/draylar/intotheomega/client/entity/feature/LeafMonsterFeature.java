package draylar.intotheomega.client.entity.feature;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.client.entity.model.LeafMonsterModel;
import draylar.intotheomega.entity.EmptyLeafMonsterEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

public class LeafMonsterFeature extends GeoLayerRenderer<EmptyLeafMonsterEntity> {

    private final LeafMonsterModel model = new LeafMonsterModel();
    private final IGeoRenderer<EmptyLeafMonsterEntity> entityRendererIn;
    private final Identifier leafTexture = IntoTheOmega.id("textures/entity/expanded_dark_sakura_leaves.png");

    public LeafMonsterFeature(IGeoRenderer<EmptyLeafMonsterEntity> renderer) {
        super(renderer);
        this.entityRendererIn = renderer;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider bufferIn, int packedLightIn, EmptyLeafMonsterEntity leafMonster, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        matrices.push();
        if(!leafMonster.isInvisible()) {
            Identifier texture = getEntityTexture(leafMonster);
            RenderLayer layer = RenderLayer.getEntityTranslucent(texture);
            matrices.scale(1.05f, 1.05f, 1.05f);
            entityRendererIn.render(model.getModel(model.getModelLocation(leafMonster)), leafMonster, partialTicks, layer, matrices, bufferIn, bufferIn.getBuffer(layer), packedLightIn, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1f);
        }

        matrices.pop();
    }

    @Override
    public Identifier getEntityTexture(EmptyLeafMonsterEntity entityIn) {
        return IntoTheOmega.id("textures/entity/expanded_dark_sakura_leaves.png");
    }
}