package draylar.intotheomega.mixin;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.cca.TelosComponent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudTelosMixin extends DrawableHelper {

    @Shadow @Final private MinecraftClient client;
    @Shadow private int scaledWidth;
    @Shadow private int scaledHeight;
    @Unique private static final Identifier TELOS_LOCATION = IntoTheOmega.id("textures/gui/telos_bar.png");

    @Inject(
            method = "renderStatusBars",
            at = @At("RETURN"))
    private void renderTelos(MatrixStack matrices, CallbackInfo ci) {
//        TelosComponent telos = OmegaComponents.TELOS.get(client.player);
//
//        double remaining = telos.getTelos() / telos.getMaxTelos();
//
//        int x = scaledWidth / 2 + 9 - 100;
//        int y = scaledHeight - 50;
//
//        client.getTextureManager().bindTexture(TELOS_LOCATION);
//
//        for(int i = 0; i < 10; i++) {
//            drawTexture(matrices, x + (i * 8), y, (i / 10f) < remaining ? 0 : 18, 0, 9, 8, 27, 8);
//        }
    }
}
