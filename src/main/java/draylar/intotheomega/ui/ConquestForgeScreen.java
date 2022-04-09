package draylar.intotheomega.ui;

import com.mojang.blaze3d.systems.RenderSystem;
import draylar.intotheomega.IntoTheOmega;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ConquestForgeScreen extends HandledScreen<ConquestForgeScreenHandler> {

    public static final Identifier CONQUEST_FORGE_UI = IntoTheOmega.id("textures/gui/conquest_forge_ui.png");

    public ConquestForgeScreen(ConquestForgeScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.backgroundHeight = 236;
        this.backgroundWidth = 190;
    }

    @Override
    public void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.clearColor(1.0F, 1.0F, 1.0F, 1.0F);
        this.client.getTextureManager().bindTexture(CONQUEST_FORGE_UI);

        int middleX = (this.width - this.backgroundWidth) / 2;
        int middleY = (this.height - this.backgroundHeight) / 2;

        this.drawTexture(matrices, middleX, middleY, 0, 0, this.backgroundWidth, this.backgroundHeight);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    @Override
    public void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
        int middleX = this.backgroundWidth / 2 - client.textRenderer.getWidth(this.title) / 2;
        this.textRenderer.draw(matrices, this.title, (float) middleX, (float) this.titleY + 12, 0xffe8fa);
    }
}
