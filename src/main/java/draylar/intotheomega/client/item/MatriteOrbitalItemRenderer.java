package draylar.intotheomega.client.item;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.client.entity.model.MatriteEntityModel;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class MatriteOrbitalItemRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer {

    private final MatriteEntityModel model = new MatriteEntityModel();
    private float oldAge;

    @Override
    public void render(ItemStack stack, ModelTransformation.Mode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        float age = MinecraftClient.getInstance().player.age / 10f;
        float lerpedAge = MathHelper.lerp(MinecraftClient.getInstance().getTickDelta(), oldAge, age);

        matrices.push();

        matrices.translate(.5, -.75, .5);

        matrices.multiply(Vector3f.NEGATIVE_Y.getDegreesQuaternion(45));

        matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(45));
        matrices.translate(.9, -.4, 0);

        model.outer.yaw = lerpedAge;
        model.outer.pitch = lerpedAge;

        model.inner.pitch = lerpedAge;
        model.inner.roll = lerpedAge;

        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(this.model.getLayer(this.getTexture()));
        this.model.render(matrices, vertexConsumer, LightmapTextureManager.pack(15, 15), OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);

        matrices.pop();
        oldAge = MinecraftClient.getInstance().player.age / 10f;
    }

    public Identifier getTexture() {
        return IntoTheOmega.id("textures/entity/matrite.png");
    }
}
