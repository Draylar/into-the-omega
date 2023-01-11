package draylar.intotheomega.client.item;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.architectury.mixin.fabric.client.MixinTextureAtlas;
import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.mixin.access.BakedModelManagerAccessor;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.*;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.SpriteAtlasManager;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.*;
import org.lwjgl.glfw.GLFW;

public class CelestiumItemRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer {

    public static final Identifier ID = IntoTheOmega.id("item/celestium_inventory");

    @Override
    public void render(ItemStack stack, ModelTransformation.Mode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        BakedModel bakedModel = ((BakedModelManagerAccessor) MinecraftClient.getInstance().getBakedModelManager()).getModels().getOrDefault(ID, null);
        float scale = (float) Math.sin(GLFW.glfwGetTime() * 5f) * 0.05f + 0.95f;

        if(bakedModel != null) {
            DiffuseLighting.disableGuiDepthLighting();
            light = LightmapTextureManager.MAX_LIGHT_COORDINATE;

            // POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL

            // randomize color
            Vec3d color = Vec3d.unpackRgb(MathHelper.hsvToRgb((float) GLFW.glfwGetTime(), 1.0f, 1.0f));
            float r = (float) color.getX();
            float g = (float) color.getY();
            float b = (float) color.getZ();

            Identifier outer = new Identifier("intotheomega", "textures/item/celestium_bloom.png");
            RenderLayer layer = RenderLayer.getEntityTranslucent(outer);

            float ringScale = (float) Math.sin(GLFW.glfwGetTime() * 5f) * 0.3f + 0.9f;
            matrices.push();
            matrices.translate(0.5f, 0.5f, 0.5f);
            matrices.scale(ringScale, ringScale, ringScale);
            Matrix3f normal = matrices.peek().getNormalMatrix();
            Matrix4f position = matrices.peek().getPositionMatrix();
            VertexConsumer buffer = vertexConsumers.getBuffer(layer);
            quad(light, 1.0f, 1.0f, 1.0f, normal, position, buffer);
            matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion((float) GLFW.glfwGetTime() * 100.0f));
            quad(light, r, g, b, normal, position, buffer);
            matrices.scale(2.0f, 0.5f, 2.0f);
            quad(light, r, g, b, normal, position, buffer);
            matrices.scale(0.25f, 4.0f, 0.25f);
            quad(light, r, g, b, normal, position, buffer);
            matrices.pop();

            matrices.push();
            matrices.translate(.5f, .5f, .5f);
            matrices.scale(scale, scale, 1.0f);
            MinecraftClient.getInstance().getItemRenderer().renderItem(stack, mode, false, matrices, vertexConsumers, LightmapTextureManager.MAX_LIGHT_COORDINATE, overlay, bakedModel);
            matrices.pop();
        }
    }

    private static void quad(int light, float r, float g, float b, Matrix3f normal, Matrix4f position, VertexConsumer buffer) {
        buffer.vertex(position, -1.0f, -1.0f, 0.0f).color(r, g, b, 1.0f).texture(0.0f, 0.0f).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(normal, 0.0f, 1.0f, 0.0f).next();
        ;
        buffer.vertex(position, 1.0f, -1.0f, 0.0f).color(r, g, b, 1.0f).texture(1.0f, 0.0f).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(normal, 0.0f, 1.0f, 0.0f).next();
        ;
        buffer.vertex(position, 1.0f, 1.0f, 0.0f).color(r, g, b, 1.0f).texture(1.0f, 1.0f).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(normal, 0.0f, 1.0f, 0.0f).next();
        ;
        buffer.vertex(position, -1.0f, 1.0f, 0.0f).color(r, g, b, 1.0f).texture(0.0f, 1.0f).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(normal, 0.0f, 1.0f, 0.0f).next();
        ;
    }
}
