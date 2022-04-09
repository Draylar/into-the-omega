package draylar.intotheomega.client.entity.renderer;

import draylar.intotheomega.IntoTheOmega;
import net.minecraft.client.render.entity.EndermanEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.util.Identifier;

public class FrostedEndermanEntityRenderer extends EndermanEntityRenderer {

    public static final Identifier TEXTURE = IntoTheOmega.id("textures/entity/frosted_enderman.png");

    public FrostedEndermanEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(EndermanEntity endermanEntity) {
        return TEXTURE;
    }
}
