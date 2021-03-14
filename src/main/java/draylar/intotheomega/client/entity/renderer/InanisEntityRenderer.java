package draylar.intotheomega.client.entity.renderer;

import draylar.intotheomega.entity.InanisEntity;
import draylar.intotheomega.registry.OmegaItems;
import draylar.intotheomega.registry.OmegaParticles;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class InanisEntityRenderer extends EntityRenderer<InanisEntity> {

    public static final ItemStack stack = new ItemStack(OmegaItems.INANIS);

    public InanisEntityRenderer(EntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(InanisEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        Vec3d prevPos = new Vec3d(entity.prevX, entity.prevY, entity.prevZ);
        Vec3d diff = entity.getPos().subtract(prevPos).multiply(.04);

        if(!(diff.x == 0 && diff.y == 0 && diff.z == 0)) {
            for (int i = 0; i < 25; i++) {
                Vec3d pos = entity.getPos().add(diff.multiply(i));
                entity.world.addParticle(OmegaParticles.SMALL_OMEGA_BURST, true, pos.getX(), pos.getY(), pos.getZ(), 0, 0, 0);
            }
        }

        if (entity.age >= 2 || this.dispatcher.camera.getFocusedEntity().squaredDistanceTo(entity) >= 12.25D) {
            matrices.push();
            matrices.scale(2.5F, 2.5F, 2.5F);

            matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(MathHelper.lerp(tickDelta, entity.prevYaw, entity.yaw) - 90.0F));
            matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(MathHelper.lerp(tickDelta, entity.prevPitch, entity.pitch) + 45.0F));
            matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(180));

            MinecraftClient.getInstance().getItemRenderer().renderItem(stack, ModelTransformation.Mode.FIXED, light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers);

            matrices.multiply(Vector3f.NEGATIVE_Y.getDegreesQuaternion(90));
            matrices.multiply(Vector3f.NEGATIVE_X.getDegreesQuaternion(135));
            matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(135));

            MinecraftClient.getInstance().getItemRenderer().renderItem(stack, ModelTransformation.Mode.FIXED, light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers);

            matrices.pop();
            super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
        }
    }

    @Override
    public Identifier getTexture(InanisEntity entity) {
        return null;
    }
}
