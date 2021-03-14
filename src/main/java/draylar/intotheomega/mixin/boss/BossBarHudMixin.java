package draylar.intotheomega.mixin.boss;

import draylar.intotheomega.IntoTheOmega;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.BossBarHud;
import net.minecraft.client.gui.hud.ClientBossBar;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BossBarHud.class)
public abstract class BossBarHudMixin extends DrawableHelper {

    @Shadow @Final private MinecraftClient client;
    @Shadow @Final private static Identifier BARS_TEXTURE;

    @Unique
    private static final Identifier ENIGMA_KING_TEXTURE = IntoTheOmega.id("textures/gui/enigma_king_boss_bars.png");

    @Unique
    private static final Identifier VOID_MATRIX_TEXTURE = IntoTheOmega.id("textures/gui/void_matrix_bar.png");

    @Redirect(
            method = "render",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/font/TextRenderer;drawWithShadow(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/text/Text;FFI)I"))
    private int onDrawBossBarName(TextRenderer textRenderer, MatrixStack matrices, Text text, float x, float y, int color) {
        if(text.toString().contains("enigma_king")) {
            return 0;
        } else {
            return this.client.textRenderer.drawWithShadow(matrices, text, x, y , color);
        }
    }

    @Inject(
            method = "renderBossBar",
            at = @At("HEAD"),
            cancellable = true
    )
    private void renderCustomBossBar(MatrixStack matrixStack, int i, int j, BossBar bossBar, CallbackInfo ci) {
        // Prevent issues with non-translatable text boss bar titles
        if(!(bossBar.getName() instanceof TranslatableText)) {
            return;
        }

        if(bossBar instanceof ClientBossBar && ((TranslatableText) bossBar.getName()).getKey().contains("enigma_king")) {
            this.client.getTextureManager().bindTexture(ENIGMA_KING_TEXTURE);

            int a = this.client.getWindow().getScaledWidth();
            this.client.getTextureManager().bindTexture(ENIGMA_KING_TEXTURE);
            double healthPercentage = bossBar.getPercent();
            int amount = (int) (185 * healthPercentage);

            // draw empty background bar
            this.drawTexture(matrixStack, i, j, 0, 21, 185, 33);
            this.drawTexture(matrixStack, i, j, 0, 0, amount, 15);
            LiteralText text = new LiteralText("The Enigma King");
            int m = this.client.textRenderer.getWidth(text);
            int x = a / 2 - m / 2;
            int y = j - 9 + 23;
            this.client.textRenderer.drawWithShadow(matrixStack, text, (float)x, (float)y, 16777215);

            matrixStack.push();
            matrixStack.scale(0.875f, 0.875f, 0.875f);
            matrixStack.translate(34, 1.99, 0);
            this.drawTexture(matrixStack, a / 2 - 2, j + 1, 35, 55 , 9, 9);
            matrixStack.pop();

            // percentage -> texture width
//            int overlayBarWidth = (int)(bossBar.getPercent() * 185.0F);

            // draw overlay
//            this.drawTexture(matrixStack, i, j, 0, 24, overlayBarWidth, 9);

            ci.cancel();
        }

        else if(bossBar instanceof ClientBossBar && ((TranslatableText) bossBar.getName()).getKey().contains("void_matrix")) {
            this.client.getTextureManager().bindTexture(VOID_MATRIX_TEXTURE);

            // draw empty background bar
            this.drawTexture(matrixStack, i, j, 0, 0, 185, 7);

            // percentage -> texture width
            int overlayBarWidth = (int) (bossBar.getPercent() * 185.0F);

            // draw overlay
            this.drawTexture(matrixStack, i, j, 0, 7, overlayBarWidth, 7);

            ci.cancel();
        }

        this.client.getTextureManager().bindTexture(BARS_TEXTURE);
    }


}
