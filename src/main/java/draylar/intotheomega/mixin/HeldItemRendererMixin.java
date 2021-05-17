package draylar.intotheomega.mixin;

import draylar.intotheomega.registry.OmegaItems;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;
import java.util.List;

@Mixin(HeldItemRenderer.class)
public class HeldItemRendererMixin {

    @Shadow @Final private MinecraftClient client;
    @Shadow private ItemStack mainHand;
    @Shadow private ItemStack offHand;
    @Unique private static final List<Item> TO_CANCEL = Arrays.asList(OmegaItems.FROSTBUSTER);

    @Inject(
            method = "updateHeldItems",
            at = @At("HEAD"))
    private void cancelAnimationUpdates(CallbackInfo ci) {
        // Modify main hand
        ItemStack newMainStack = client.player.getMainHandStack();
        if (newMainStack.getItem().equals(mainHand.getItem())) {
            if (TO_CANCEL.contains(mainHand.getItem())) {
                mainHand = newMainStack;
            }
        }

        // Modify off-hand
        ItemStack newOffStack = client.player.getMainHandStack();
        if (newOffStack.getItem().equals(offHand.getItem())) {
            if (TO_CANCEL.contains(offHand.getItem())) {
                offHand = newOffStack;
            }
        }
    }
}
