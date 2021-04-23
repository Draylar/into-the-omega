package draylar.intotheomega.client.entity.renderer;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.client.entity.model.VoidWalkerModel;
import draylar.intotheomega.entity.VoidWalkerEntity;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.util.Identifier;

public class VoidWalkerRenderer extends BipedEntityRenderer<VoidWalkerEntity, VoidWalkerModel> {

    private static final Identifier TEXTURE = IntoTheOmega.id("textures/entity/void_walker.png");

    public VoidWalkerRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new VoidWalkerModel(0.0F, false), 0.5F);
        this.addFeature(new ArmorFeatureRenderer(this, new VoidWalkerModel(0.5F, true), new VoidWalkerModel(1.0F, true)));
    }

    @Override
    public Identifier getTexture(VoidWalkerEntity mobEntity) {
        return TEXTURE;
    }
}
