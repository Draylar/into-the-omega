package draylar.intotheomega.mixin.armor;

import draylar.intotheomega.api.client.ArmorSetDisplayRegistry;
import draylar.intotheomega.item.api.SetArmorItem;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityRenderer.class)
public class PlayerEntityRendererSetMixin {

    @Inject(
            method = "render",
            at = @At("RETURN"))
    private void afterPlayerRenders(AbstractClientPlayerEntity player, float f, float delta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        ItemStack headStack = player.getEquippedStack(EquipmentSlot.HEAD);
        if(headStack.getItem() instanceof SetArmorItem) {
            SetArmorItem setItem = (SetArmorItem) headStack.getItem();
            if(!setItem.hasFullSet(player)) {
                return;
            }

            @Nullable ArmorSetDisplayRegistry.SetDisplay display = ArmorSetDisplayRegistry.getDisplay(setItem);
            if(display != null) {
                display.display(player, player.clientWorld, delta);
            }
        }
    }
}
