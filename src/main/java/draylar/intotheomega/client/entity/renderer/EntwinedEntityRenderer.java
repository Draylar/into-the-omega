package draylar.intotheomega.client.entity.renderer;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.entity.EntwinedEntity;
import draylar.intotheomega.registry.OmegaEntityModelLayers;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.util.Identifier;

public class EntwinedEntityRenderer extends BipedEntityRenderer<EntwinedEntity, BipedEntityModel<EntwinedEntity>> {

    private static final Identifier TEXTURE = IntoTheOmega.id("textures/entity/entwined.png");

    public EntwinedEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new BipedEntityModel<>(context.getPart(OmegaEntityModelLayers.ENTWINED)), 0.25f);
    }

    @Override
    public Identifier getTexture(EntwinedEntity entity) {
        return TEXTURE;
    }
}
