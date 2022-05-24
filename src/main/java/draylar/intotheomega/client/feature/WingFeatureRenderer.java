package draylar.intotheomega.client.feature;

import dev.emi.trinkets.api.TrinketsApi;
import draylar.intotheomega.item.WingsItem;
import draylar.intotheomega.registry.OmegaItems;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3f;
import org.lwjgl.glfw.GLFW;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class WingFeatureRenderer extends FeatureRenderer<LivingEntity, BipedEntityModel<LivingEntity>> {

    private static final List<Item> WINGS = Arrays.asList(OmegaItems.STARLIGHT_WINGS, OmegaItems.PHOENIX_STARDUST_WINGS, OmegaItems.DRAGON_WINGS);

    public WingFeatureRenderer(FeatureRendererContext<LivingEntity, BipedEntityModel<LivingEntity>> context) {
        super(context);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, LivingEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        TrinketsApi.getTrinketComponent(entity).ifPresent(component -> {
            Optional<ItemStack> wingTrinket = WINGS.stream().map(component::getEquipped).filter(it -> !it.isEmpty()).map(it -> it.get(0)).map(Pair::getRight).filter(stack -> stack.getItem() instanceof WingsItem).findFirst();
            wingTrinket.ifPresent(stack -> {
                if(stack.getItem() instanceof WingsItem wingItem) {
                    Identifier wingTextureLocation = wingItem.getTexture();

                    // POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL
                    matrices.push();
                    matrices.translate(0, -1.25f, 0.2);
                    matrices.scale(3, 3, 3);

                    // Left wing
                    matrices.push();
                    matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(30 - 30f * (float) Math.sin((float) GLFW.glfwGetTime() * 5)));
                    matrices.translate(-0.25, 0, 0);
                    Matrix4f pos = matrices.peek().getPositionMatrix();
                    Matrix3f normal = matrices.peek().getNormalMatrix();
                    VertexConsumer buffer = vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(wingTextureLocation));
                    buffer.vertex(pos, -0.25f, 0.0f, 0.0f).color(1.0f, 1.0f, 1.0f, 1.0f).texture(0, 0).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(normal, 0.0f, -1.0f, 0.0f).next();
                    buffer.vertex(pos, -0.25f, 1.0f, 0.0f).color(1.0f, 1.0f, 1.0f, 1.0f).texture(0, 1.0f).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(normal, 0.0f, -1.0f, 0.0f).next();
                    buffer.vertex(pos, 0.25f, 1.0f, 0.0f).color(1.0f, 1.0f, 1.0f, 1.0f).texture(0.5f, 1.0f).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(normal, 0.0f, -1.0f, 0.0f).next();
                    buffer.vertex(pos, 0.25f, 0.0f, 0.0f).color(1.0f, 1.0f, 1.0f, 1.0f).texture(0.5f, 0).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(normal, 0.0f, -1.0f, 0.0f).next();
                    matrices.pop();

                    // Right wing
                    matrices.push();
                    matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(-30f + 30f * (float) Math.sin((float) GLFW.glfwGetTime() * 5)));
                    matrices.translate(0.25, 0, 0);
                    pos = matrices.peek().getPositionMatrix();
                    normal = matrices.peek().getNormalMatrix();
                    buffer.vertex(pos, -0.25f, 0.0f, 0.0f).color(1.0f, 1.0f, 1.0f, 1.0f).texture(0.5f, 0).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(normal, 0.0f, -1.0f, 0.0f).next();
                    buffer.vertex(pos, -0.25f, 1.0f, 0.0f).color(1.0f, 1.0f, 1.0f, 1.0f).texture(0.5f, 1.0f).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(normal, 0.0f, -1.0f, 0.0f).next();
                    buffer.vertex(pos, 0.25f, 1.0f, 0.0f).color(1.0f, 1.0f, 1.0f, 1.0f).texture(1.0f, 1.0f).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(normal, 0.0f, -1.0f, 0.0f).next();
                    buffer.vertex(pos, 0.25f, 0.0f, 0.0f).color(1.0f, 1.0f, 1.0f, 1.0f).texture(1.0f, 0).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(normal, 0.0f, -1.0f, 0.0f).next();
                    matrices.pop();


                    matrices.pop();
                }
            });
        });
    }
}
