package draylar.intotheomega.client.entity.renderer;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.client.entity.model.VoidMatrixModel;
import draylar.intotheomega.entity.void_matrix.VoidMatrixEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderer.geo.GeoEntityRenderer;

public class VoidMatrixEntityRenderer extends GeoEntityRenderer<VoidMatrixEntity> {

    public static final ItemStack GLASS = new ItemStack(Items.GLASS);

    public VoidMatrixEntityRenderer(EntityRenderDispatcher dispatcher) {
        super(dispatcher, new VoidMatrixModel());
    }

    @Override
    public void render(GeoModel model, VoidMatrixEntity vm, float partialTicks, RenderLayer type, MatrixStack matrixStackIn, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        matrixStackIn.push();

//      Sync headYaw to bodyYaw (bodyYaw is not synced S2C every frame, headYaw/yaw are)
        vm.bodyYaw = vm.headYaw;

        // Fit model into hitbox
        matrixStackIn.translate(0, .75, 0);

        // Rotate so eye is facing forwards
        matrixStackIn.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(90));
        // Rotate based on yaw
        matrixStackIn.translate(0, 1.25, 0);
        matrixStackIn.multiply(Vector3f.NEGATIVE_Z.getDegreesQuaternion(vm.pitch));
        matrixStackIn.translate(0, -1.25, 0);

        super.render(model, vm, partialTicks, type, matrixStackIn, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        matrixStackIn.pop();
    }

    @Override
    public Identifier getTexture(VoidMatrixEntity entity) {
        return IntoTheOmega.id("textures/entity/void_matrix.png");
    }

    @Override
    public RenderLayer getRenderType(VoidMatrixEntity animatable, float partialTicks, MatrixStack stack, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, Identifier textureLocation) {
        return RenderLayer.getEntityTranslucent(textureLocation);
    }
}
