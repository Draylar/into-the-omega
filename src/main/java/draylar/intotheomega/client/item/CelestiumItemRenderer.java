package draylar.intotheomega.client.item;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.mixin.access.BakedModelManagerAccessor;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

public class CelestiumItemRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer {

    public static final Identifier ID = IntoTheOmega.id("item/celestium_inventory");

    @Override
    public void render(ItemStack stack, ModelTransformation.Mode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        BakedModel bakedModel = ((BakedModelManagerAccessor) MinecraftClient.getInstance().getBakedModelManager()).getModels().getOrDefault(ID, null);
        float scale = (float) Math.sin(GLFW.glfwGetTime() * 5f) * 0.05f + 0.95f;

        if(bakedModel != null) {
            matrices.push();
            matrices.translate(.5f, .5f, .5f);
            matrices.scale(scale, scale, 1.0f);
            MinecraftClient.getInstance().getItemRenderer().renderItem(stack, mode, false, matrices, vertexConsumers, LightmapTextureManager.MAX_LIGHT_COORDINATE, overlay, bakedModel);
            matrices.pop();
        }
    }
}
