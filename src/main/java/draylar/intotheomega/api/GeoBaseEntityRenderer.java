package draylar.intotheomega.api;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;
import org.lwjgl.glfw.GLFW;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.GeoModelProvider;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

public class GeoBaseEntityRenderer<T extends Entity & IAnimatable> extends EntityRenderer<T> implements IGeoRenderer<T> {

    private final AnimatedGeoModel<T> model;

    public GeoBaseEntityRenderer(EntityRendererFactory.Context dispatcher, AnimatedGeoModel<T> model) {
        super(dispatcher);
        this.model = model;
    }

    @Override
    public void render(T entity, float yaw, float delta, MatrixStack matrices, VertexConsumerProvider bufferProvider, int light) {
        super.render(entity, yaw, delta, matrices, bufferProvider, light);


        matrices.push();
        GeoModel model = this.model.getModel(this.model.getModelLocation(entity));
        RenderLayer layer = getRenderType(entity, delta, matrices, bufferProvider, null, light, getTexture(entity));
        render(model, entity, delta, layer, matrices, bufferProvider, null, light, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
        matrices.pop();
    }

    @Override
    public Identifier getTexture(T entity) {
        return model.getTextureLocation(entity);
    }

    @Override
    public GeoModelProvider<T> getGeoModelProvider() {
        return model;
    }

    @Override
    public Identifier getTextureLocation(T instance) {
        return model.getTextureLocation(instance);
    }
}
