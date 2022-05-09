package draylar.intotheomega.client.entity.renderer;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.entity.StarWalkerEntity;
import draylar.intotheomega.registry.OmegaEntityModelLayers;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.util.Identifier;

public class StarWalkerEntityRenderer extends BipedEntityRenderer<StarWalkerEntity, BipedEntityModel<StarWalkerEntity>> {

    private static final Identifier TEXTURE = IntoTheOmega.id("textures/entity/star_walker.png");

    public StarWalkerEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new BipedEntityModel<>(context.getPart(OmegaEntityModelLayers.STAR_WALKER)), 0.25f);
        addFeature(new ArmorFeatureRenderer<>(this,
                new BipedEntityModel<>(context.getPart(OmegaEntityModelLayers.STAR_WALKER_INNER_ARMOR)),
                new BipedEntityModel<>(context.getPart(OmegaEntityModelLayers.STAR_WALKER_BODY_ARMOR))));
    }

    @Override
    public Identifier getTexture(StarWalkerEntity entity) {
        return TEXTURE;
    }
}
