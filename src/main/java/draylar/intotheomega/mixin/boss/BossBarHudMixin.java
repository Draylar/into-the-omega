package draylar.intotheomega.mixin.boss;

import com.mojang.blaze3d.systems.RenderSystem;
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

    @Unique private static final Identifier ENIGMA_KING_TEXTURE = IntoTheOmega.id("textures/gui/enigma_king_boss_bars.png");
    @Unique private static final Identifier VOID_MATRIX_TEXTURE = IntoTheOmega.id("textures/gui/void_matrix_bar.png");
    @Unique private static final Identifier OMEGA_SLIME_EMPEROR_TEXTURE = IntoTheOmega.id("textures/gui/omega_slime_boss_bars.png");
    @Unique private static final Identifier ENDER_DRAGON_TEXTURE = IntoTheOmega.id("textures/gui/ender_dragon_boss_bars.png");

    @Redirect(
            method = "render",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/font/TextRenderer;drawWithShadow(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/text/Text;FFI)I"))
    private int onDrawBossBarName(TextRenderer textRenderer, MatrixStack matrices, Text text, float x, float y, int color) {
        if(text.toString().contains("enigma_king") || text.toString().contains("omega_slime_emperor")) {
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
    private void renderCustomBossBar(MatrixStack matrices, int x, int y, BossBar bossBar, CallbackInfo ci) {
        // Prevent issues with non-translatable text boss bar titles
        if(!(bossBar.getName() instanceof TranslatableText)) {
            return;
        }

        if(bossBar instanceof ClientBossBar && ((TranslatableText) bossBar.getName()).getKey().contains("enigma_king")) {
            renderEnigmaKingBar(matrices, x, y, bossBar);
            ci.cancel();
        }

        else if(bossBar instanceof ClientBossBar && ((TranslatableText) bossBar.getName()).getKey().contains("void_matrix")) {
            renderVoidMatrixBar(matrices, x, y, bossBar);
            ci.cancel();
        }

        else if (bossBar instanceof ClientBossBar && ((TranslatableText) bossBar.getName()).getKey().contains("omega_slime_emperor")) {
            renderOmegaSlimeEmperor(matrices, x, y, bossBar);
            ci.cancel();
        }

        else if (bossBar instanceof ClientBossBar && ((TranslatableText) bossBar.getName()).getKey().contains("ender_dragon")) {
            renderEnderDragon(matrices, x, y, bossBar);
            ci.cancel();
        }

        RenderSystem.setShaderTexture(0, BARS_TEXTURE);
    }

    @Unique
    private void renderEnigmaKingBar(MatrixStack matrices, int x, int y, BossBar bossBar) {
        RenderSystem.setShaderTexture(0, ENIGMA_KING_TEXTURE);

        int a = this.client.getWindow().getScaledWidth();
        double healthPercentage = bossBar.getPercent();
        int amount = (int) (185 * healthPercentage);

        // draw empty background bar
        this.drawTexture(matrices, x, y, 0, 21, 185, 33);
        this.drawTexture(matrices, x, y, 0, 0, amount, 15);
        LiteralText text = new LiteralText("The Enigma King");
        int m = this.client.textRenderer.getWidth(text);
        x = a / 2 - m / 2;
        y = y - 9 + 23;
        this.client.textRenderer.drawWithShadow(matrices, text, (float)x, (float)y, 16777215);

        matrices.push();
        matrices.scale(0.875f, 0.875f, 0.875f);
        matrices.translate(34, 1.99, 0);
        this.drawTexture(matrices, a / 2 - 2, y + 1, 35, 55 , 9, 9);
        matrices.pop();

        // percentage -> texture width
//            int overlayBarWidth = (int)(bossBar.getPercent() * 185.0F);

        // draw overlay
//            this.drawTexture(matrixStack, i, j, 0, 24, overlayBarWidth, 9);
    }

    @Unique
    private void renderOmegaSlimeEmperor(MatrixStack matrices, int x, int y, BossBar bossBar) {
        RenderSystem.setShaderTexture(0, OMEGA_SLIME_EMPEROR_TEXTURE);

        int a = this.client.getWindow().getScaledWidth();
        double healthPercentage = bossBar.getPercent();
        int amount = (int) (185 * healthPercentage);

        // draw empty background bar
        this.drawTexture(matrices, x, y, 0, 21, 185, 33);
        this.drawTexture(matrices, x, y, 0, 0, amount, 15);
        LiteralText text = new LiteralText("Omega Slime Emperor");
        int m = this.client.textRenderer.getWidth(text);
        x = a / 2 - m / 2;
        y = y - 9 + 23;
        this.client.textRenderer.drawWithShadow(matrices, text, (float)x, (float)y, 16777215);

        matrices.push();
        matrices.scale(0.875f, 0.875f, 0.875f);
        matrices.translate(34, 1.99, 0);
        this.drawTexture(matrices, a / 2 - 2, y + 1, 35, 55 , 9, 9);
        matrices.pop();

        // percentage -> texture width
//            int overlayBarWidth = (int)(bossBar.getPercent() * 185.0F);

        // draw overlay
//            this.drawTexture(matrixStack, i, j, 0, 24, overlayBarWidth, 9);
    }

    @Unique
    private void renderVoidMatrixBar(MatrixStack matrices, int x, int y, BossBar bossBar) {
        RenderSystem.setShaderTexture(0, VOID_MATRIX_TEXTURE);

        // draw empty background bar
        drawTexture(matrices, x, y, 0, 0, 185, 7);

        // percentage -> texture width
        int overlayBarWidth = (int) (bossBar.getPercent() * 185.0F);

        // draw overlay
        drawTexture(matrices, x, y, 0, 7, overlayBarWidth, 7);
    }

    @Unique
    private void renderEnderDragon(MatrixStack matrices, int x, int y, BossBar bossBar) {
        RenderSystem.setShaderTexture(0, ENDER_DRAGON_TEXTURE);

        // draw empty background bar
        drawTexture(matrices, x, y, 0, 0, 182, 19);

        // percentage -> texture width
        int overlayBarWidth = (int) (bossBar.getPercent() * 182.0f);

        // draw overlay
        drawTexture(matrices, x, y, 0, 19, overlayBarWidth, 19);
    }
}
