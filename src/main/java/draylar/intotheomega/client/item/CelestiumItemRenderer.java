package draylar.intotheomega.client.item;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.api.client.ShapeRendering;
import draylar.intotheomega.api.client.VertexWrapper;
import draylar.intotheomega.mixin.access.BakedModelManagerAccessor;
import draylar.intotheomega.registry.client.OmegaRenderLayers;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
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

            // randomize color
            Vec3d color = Vec3d.unpackRgb(MathHelper.hsvToRgb((float) GLFW.glfwGetTime(), 1.0f, 1.0f));
            float r = (float) color.getX();
            float g = (float) color.getY();
            float b = (float) color.getZ();

            Identifier outer = new Identifier("intotheomega", "textures/item/celestium_bloom.png");
            RenderLayer layer = OmegaRenderLayers.getEntityTrueTranslucent(outer);
            VertexWrapper buffer = VertexWrapper.wrap(vertexConsumers.getBuffer(layer), VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL);

            float ringScale = (float) Math.sin(GLFW.glfwGetTime() * 5f) * 0.6f + 1.8f;

            // rainbow star
            matrices.push();
            matrices.translate(0.5f, 0.5f, 0.5f);
            matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(45));
            matrices.push();
            matrices.scale(ringScale, ringScale, ringScale);
            Matrix3f normal = matrices.peek().getNormalMatrix();
            Matrix4f position = matrices.peek().getPositionMatrix();
            ShapeRendering.quad(light, 1.0f, 1.0f, 1.0f, normal, position, buffer);
            matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion((float) GLFW.glfwGetTime() * 100.0f));
            ShapeRendering.quad(light, r, g, b, normal, position, buffer);
            matrices.scale(2.0f, 0.5f, 2.0f);
            ShapeRendering.quad(light, r, g, b, normal, position, buffer);
            matrices.scale(0.25f, 4.0f, 0.25f);
            ShapeRendering.quad(light, r, g, b, normal, position, buffer);
            matrices.pop();

            // white star
            float a = (float) Math.sin((float) GLFW.glfwGetTime() * 10.0f) * 0.5f + 0.5f;
            a = 5.0f;
            matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(45));
            matrices.push();
            matrices.scale(ringScale * 0.75f, ringScale * 0.75f, ringScale * 0.75f);
            normal = matrices.peek().getNormalMatrix();
            position = matrices.peek().getPositionMatrix();
            ShapeRendering.quad(LightmapTextureManager.MAX_LIGHT_COORDINATE, a, a, a, normal, position, buffer);
            matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion((float) GLFW.glfwGetTime() * 100.0f));
            ShapeRendering.quad(LightmapTextureManager.MAX_LIGHT_COORDINATE, a, a, a, normal, position, buffer);
            matrices.scale(2.0f, 0.5f, 2.0f);
            ShapeRendering.quad(LightmapTextureManager.MAX_LIGHT_COORDINATE, a, a, a, normal, position, buffer);
            matrices.scale(0.25f, 4.0f, 0.25f);
            ShapeRendering.quad(LightmapTextureManager.MAX_LIGHT_COORDINATE, a, a, a, normal, position, buffer);
            matrices.pop();
            matrices.pop();

            // render item on top
            matrices.push();
            matrices.translate(.5f, .5f, .5f);
            matrices.scale(scale, scale, 1.0f);
            MinecraftClient.getInstance().getItemRenderer().renderItem(stack, mode, false, matrices, vertexConsumers, LightmapTextureManager.MAX_LIGHT_COORDINATE, overlay, bakedModel);
            matrices.pop();
        }
    }
}
