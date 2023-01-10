package draylar.intotheomega.client.item;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.mixin.access.BakedModelManagerAccessor;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;

public class NebulaGearItemRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer {

    public static final Identifier ID = IntoTheOmega.id("item/nebula_gear_inventory");

    @Override
    public void render(ItemStack stack, ModelTransformation.Mode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        BakedModel bakedModel = ((BakedModelManagerAccessor) MinecraftClient.getInstance().getBakedModelManager()).getModels().getOrDefault(ID, null);

        matrices.push();
        if(bakedModel != null) {
            matrices.translate(.5f, .5f, .5f);

            // only spin if player exists
            ClientPlayerEntity player = MinecraftClient.getInstance().player;
            if(player != null) {
                matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(player.age));
            }

            // render
            MinecraftClient.getInstance().getItemRenderer().renderItem(stack, mode, false, matrices, vertexConsumers, light, overlay, bakedModel);
        }
        matrices.pop();
    }
}
