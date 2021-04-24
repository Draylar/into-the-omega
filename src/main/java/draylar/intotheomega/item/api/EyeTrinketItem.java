package draylar.intotheomega.item.api;

import dev.emi.trinkets.api.SlotGroups;
import dev.emi.trinkets.api.Trinket;
import dev.emi.trinkets.api.TrinketItem;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;

public class EyeTrinketItem extends TrinketItem {

    public EyeTrinketItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean canWearInSlot(String group, String slot) {
        return group.equals(SlotGroups.HEAD) && slot.equals("eye");
    }

    @Override
    public void render(String slot, MatrixStack matrixStack, VertexConsumerProvider vertexConsumer, int light, PlayerEntityModel<AbstractClientPlayerEntity> model, AbstractClientPlayerEntity player, float headYaw, float headPitch) {
        super.render(slot, matrixStack, vertexConsumer, light, model, player, headYaw, headPitch);

        matrixStack.push();
        Trinket.translateToFace(matrixStack, model, player, headYaw, headPitch);
        matrixStack.scale(.3f, .3f, .3f);
        matrixStack.translate(-.35, .1, .15);
        MinecraftClient.getInstance().getItemRenderer().renderItem(
                TrinketsApi.getTrinketComponent(player).getStack(SlotGroups.HEAD, "eye"),
                ModelTransformation.Mode.FIXED,
                light,
                OverlayTexture.DEFAULT_UV,
                matrixStack,
                vertexConsumer
        );

        matrixStack.pop();
    }
}
