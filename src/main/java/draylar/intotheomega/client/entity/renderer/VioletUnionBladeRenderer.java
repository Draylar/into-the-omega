package draylar.intotheomega.client.entity.renderer;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.entity.VioletUnionBladeEntity;
import draylar.intotheomega.mixin.access.BakedModelManagerAccessor;
import draylar.intotheomega.mixin.access.ItemRendererAccessor;
import draylar.intotheomega.registry.OmegaItems;
import draylar.intotheomega.registry.OmegaParticles;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.*;

public class VioletUnionBladeRenderer extends EntityRenderer<VioletUnionBladeEntity> {

    public static final Identifier BLUE = IntoTheOmega.id("item/violet_union_blue");
    public static final Identifier PINK = IntoTheOmega.id("item/violet_union_pink");

    public VioletUnionBladeRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    public void render(VioletUnionBladeEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumerProvider, int light) {
        ItemStack stack = new ItemStack(OmegaItems.INANIS);

        Vec3d prevPos = new Vec3d(entity.prevX, entity.prevY, entity.prevZ);
        Vec3d diff = entity.getPos().subtract(prevPos).multiply(.04);

        if(!(diff.x == 0 && diff.y == 0 && diff.z == 0)) {
            for (int i = 0; i < 25; i++) {
                Vec3d pos = entity.getPos().add(diff.multiply(i));
                entity.world.addParticle(OmegaParticles.VARIANT_FUSION, true, pos.getX(), pos.getY(), pos.getZ(), 0, 0, 0);
            }
        }

        if(entity.age >= 2 || this.dispatcher.camera.getFocusedEntity().squaredDistanceTo(entity) >= 12.25D) {
            matrices.push();
            matrices.scale(1F, 1, 1F);

            matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(MathHelper.lerp(tickDelta, entity.prevYaw, entity.getYaw()) - 90.0F));
            matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(MathHelper.lerp(tickDelta, entity.prevPitch, entity.getPitch()) + 45.0F));
            matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(180));

            renderItem(entity, light, matrices, vertexConsumerProvider);
//            MinecraftClient.getInstance().getItemRenderer().renderItem(stack, ModelTransformation.Mode.FIXED, light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumerProvider);

            matrices.multiply(Vec3f.NEGATIVE_Y.getDegreesQuaternion(90));
            matrices.multiply(Vec3f.NEGATIVE_X.getDegreesQuaternion(135));
            matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(135));

            renderItem(entity, light, matrices, vertexConsumerProvider);
//            MinecraftClient.getInstance().getItemRenderer().renderItem(stack, ModelTransformation.Mode.FIXED, light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumerProvider);

            matrices.pop();
            super.render(entity, yaw, tickDelta, matrices, vertexConsumerProvider, light);
        }
    }

    public void renderItem(VioletUnionBladeEntity entity, int light, MatrixStack matrices, VertexConsumerProvider vcp) {
        BakedModel bakedModel = ((BakedModelManagerAccessor) MinecraftClient.getInstance().getBakedModelManager()).getModels().getOrDefault(entity.getId() % 2 == 0 ? BLUE : PINK, null);
        ItemStack stack = new ItemStack(OmegaItems.INANIS);
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        BakedModel model = itemRenderer.getModel(stack, entity.world, null, 0);
        matrices.push();
        model.getTransformation().getTransformation(ModelTransformation.Mode.FIXED).apply(false, matrices);
        matrices.translate(-0.5D, -0.5D, -0.5D);
        RenderLayer renderLayer = RenderLayers.getItemLayer(stack, true);
        VertexConsumer vertexConsumer4 = ItemRenderer.getItemGlintConsumer(vcp, renderLayer, true, stack.hasGlint());
        ((ItemRendererAccessor) itemRenderer).callRenderBakedItemModel(bakedModel, stack, light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumer4);
        matrices.pop();
    }

    public void vertex(Matrix4f matrix4f, Matrix3f matrix3f, VertexConsumer vertexConsumer, int x, int y, int z, float u, float v, int nx, int nz, int ny, int light) {
        vertexConsumer.vertex(matrix4f, (float) x, (float) y, (float) z).color(255, 255, 255, 255).texture(u, v).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(matrix3f, (float) nx, (float) ny, (float) nz).next();
    }

    @Override
    public Identifier getTexture(VioletUnionBladeEntity entity) {
        return entity.getId() % 2 == 0 ? BLUE : PINK;
    }
}

