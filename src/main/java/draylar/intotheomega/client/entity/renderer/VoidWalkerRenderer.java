package draylar.intotheomega.client.entity.renderer;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.client.entity.model.VoidWalkerModel;
import draylar.intotheomega.entity.VoidWalkerEntity;
import draylar.intotheomega.registry.OmegaEntityModelLayers;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.util.Identifier;

public class VoidWalkerRenderer extends BipedEntityRenderer<VoidWalkerEntity, VoidWalkerModel> {

    private static final Identifier TEXTURE = IntoTheOmega.id("textures/entity/void_walker.png");

    public VoidWalkerRenderer(EntityRendererFactory.Context context) {
        super(context, new VoidWalkerModel(context.getPart(OmegaEntityModelLayers.VOID_WALKER)), 0.5F);
        this.addFeature(new ArmorFeatureRenderer<>(
                this,
                new VoidWalkerModel(context.getPart(OmegaEntityModelLayers.VOID_WALKER_INNER_ARMOR)),
                new VoidWalkerModel(context.getPart(OmegaEntityModelLayers.VOID_WALKER_OUTER_ARMOR))));
    }

    @Override
    public Identifier getTexture(VoidWalkerEntity mobEntity) {
        return TEXTURE;
    }
}
