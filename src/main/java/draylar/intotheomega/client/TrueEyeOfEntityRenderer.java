package draylar.intotheomega.client;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.entity.TrueEyeOfEnderEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class TrueEyeOfEntityRenderer extends MobEntityRenderer<TrueEyeOfEnderEntity, TrueEyeOfEnderEntityModel> {

    public TrueEyeOfEntityRenderer(EntityRenderDispatcher dispatcher) {
        super(dispatcher, new TrueEyeOfEnderEntityModel(), 0.5f);
    }

    @Override
    public void render(TrueEyeOfEnderEntity mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public Identifier getTexture(TrueEyeOfEnderEntity entity) {
        return IntoTheOmega.id("textures/entity/true_eoe.png");
    }
}
