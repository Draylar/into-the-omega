package draylar.intotheomega.client.entity.renderer;

import draylar.intotheomega.entity.AbyssalKnightEntity;
import draylar.intotheomega.entity.EntwinedEntity;
import draylar.intotheomega.registry.OmegaEntityModelLayers;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class AbyssalKnightEntityRenderer extends BipedEntityRenderer<AbyssalKnightEntity, BipedEntityModel<AbyssalKnightEntity>> {

    public AbyssalKnightEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new BipedEntityModel<>(context.getPart(OmegaEntityModelLayers.ABYSSAL_KNIGHT)), 0.25f);
        addFeature(new ArmorFeatureRenderer<>(this,
                new BipedEntityModel<>(context.getPart(OmegaEntityModelLayers.ABYSSAL_KNIGHT_LEG_ARMOR)),
                new BipedEntityModel<>(context.getPart(OmegaEntityModelLayers.ABYSSAL_KNIGHT_BODY_ARMOR))));
    }

    @Nullable
    @Override
    public RenderLayer getRenderLayer(AbyssalKnightEntity entity, boolean showBody, boolean translucent, boolean showOutline) {
        return RenderLayer.getEndPortal();
    }

    @Override
    public Identifier getTexture(AbyssalKnightEntity entity) {
        return null;
    }
}
