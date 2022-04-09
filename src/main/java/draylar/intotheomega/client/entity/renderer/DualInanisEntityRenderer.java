package draylar.intotheomega.client.entity.renderer;

import draylar.intotheomega.entity.DualInanisEntity;
import draylar.intotheomega.registry.OmegaItems;
import draylar.intotheomega.registry.OmegaParticles;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;

public class DualInanisEntityRenderer extends EntityRenderer<DualInanisEntity> {

    public static final ItemStack stack = new ItemStack(OmegaItems.DUAL_INANIS);

    public DualInanisEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public void render(DualInanisEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        Vec3d prevPos = new Vec3d(entity.prevX, entity.prevY, entity.prevZ);
        Vec3d diff = entity.getPos().subtract(prevPos).multiply(.04);

        if(!(diff.x == 0 && diff.y == 0 && diff.z == 0)) {
            for (int i = 0; i < 25; i++) {
                Vec3d pos = entity.getPos().add(diff.multiply(i));
                entity.world.addParticle(OmegaParticles.SMALL_BLUE_OMEGA_BURST, true, pos.getX(), pos.getY(), pos.getZ(), 0, 0, 0);

                entity.world.addParticle(OmegaParticles.SMALL_PINK_OMEGA_BURST, true, pos.getX() + Math.sin(i), pos.getY() + Math.cos(i), pos.getZ() + Math.cos(i), 0, 0, 0);
            }
        }

        if(entity.age >= 2 || this.dispatcher.camera.getFocusedEntity().squaredDistanceTo(entity) >= 12.25D) {
            matrices.push();
            matrices.scale(2.5F, 2.5F, 2.5F);

            matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(MathHelper.lerp(tickDelta, entity.prevYaw, entity.getYaw()) - 90.0F));
            matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(MathHelper.lerp(tickDelta, entity.prevPitch, entity.getPitch()) + 45.0F));
            matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(180));

            MinecraftClient.getInstance().getItemRenderer().renderItem(stack, ModelTransformation.Mode.FIXED, light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, 0);

            matrices.multiply(Vec3f.NEGATIVE_Y.getDegreesQuaternion(90));
            matrices.multiply(Vec3f.NEGATIVE_X.getDegreesQuaternion(135));
            matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(135));

            MinecraftClient.getInstance().getItemRenderer().renderItem(stack, ModelTransformation.Mode.FIXED, light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, 0);

            matrices.pop();
            super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
        }
    }

    @Override
    public Identifier getTexture(DualInanisEntity entity) {
        return null;
    }
}
