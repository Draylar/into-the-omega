package draylar.intotheomega.library.gui;

import draylar.intotheomega.library.gui.widget.Dropdown;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;

public abstract class MenuTheme extends DrawableHelper {

    public void drawBackground(MatrixStack matrices, int x, int y, int width, int height) {
        throw new IllegalStateException();
    }

    public void drawButton(Labeled button, MatrixStack matrices, int x, int y, int width, int height, boolean hovered) {
        throw new IllegalStateException();
    }

    public void drawDropdown(Dropdown dropdown, MatrixStack matrices, int x, int y, int width, int height, boolean hovered) {
        throw new IllegalStateException();
    }

    public void drawText(Text text, MatrixStack matrices, int x, int y, int width, int height) {
        throw new IllegalStateException();
    }

    public static class ModernTheme extends MenuTheme {

        @Override
        public void drawBackground(MatrixStack matrices, int x, int y, int width, int height) {
            fill(matrices, x, y, x + width, y + height, 0xff2b2633);
        }

        @Override
        public void drawButton(Labeled button, MatrixStack matrices, int x, int y, int width, int height, boolean hovered) {
            float textScale = 0.5f;

            TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
            float centerX = x + width / 2f;
            float centerY = y + height / 2f - (8 / 2f) * textScale;
            OrderedText orderedText = button.getLabel().asOrderedText();

            if(hovered) {
                fill(matrices, x, y, x + width, y + height, 0xff94dfe3);

                matrices.push();
                matrices.translate(centerX - textRenderer.getWidth(orderedText) / 2f * textScale, centerY, 0);
                matrices.scale(textScale, textScale, textScale);
                textRenderer.draw(matrices, orderedText, 0, 0, 0xff000000);
                matrices.pop();
            } else {
                fill(matrices, x, y, x + width, y + height, 0xff433e4a);

                matrices.push();
                matrices.translate(centerX - textRenderer.getWidth(orderedText) / 2f * textScale, centerY, 0);
                matrices.scale(textScale, textScale, textScale);
                textRenderer.draw(matrices, orderedText, 0, 0, 0xffffffff);
                matrices.pop();
            }
        }

        @Override
        public void drawDropdown(Dropdown dropdown, MatrixStack matrices, int x, int y, int width, int height, boolean hovered) {
            float textScale = 0.5f;

            TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
            float centerX = x + width / 2f;
            float centerY = y + height / 2f - (8 / 2f) * textScale;
            OrderedText orderedText = dropdown.getLabel().asOrderedText();
            if(hovered) {
                fill(matrices, x, y, x + width, y + height, 0xff94dfe3);

                matrices.push();
                matrices.translate(centerX - textRenderer.getWidth(orderedText) / 2f * textScale, centerY, 0);
                matrices.scale(textScale, textScale, textScale);
                textRenderer.draw(matrices, orderedText, 0, 0, 0xff000000);
                matrices.pop();
            } else {
                fill(matrices, x, y, x + width, y + height, 0xff433e4a);

                matrices.push();
                matrices.translate(centerX - textRenderer.getWidth(orderedText) / 2f * textScale, centerY, 0);
                matrices.scale(textScale, textScale, textScale);
                textRenderer.draw(matrices, orderedText, 0, 0, 0xffffffff);
                matrices.pop();
            }
        }

        @Override
        public void drawText(Text text, MatrixStack matrices, int x, int y, int width, int height) {
            float textScale = 0.5f;

            TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
            float centerX = x + width / 2f;
            float centerY = y + height / 2f - (8 / 2f) * textScale;
            OrderedText orderedText = text.asOrderedText();

            matrices.push();
            matrices.translate(centerX - textRenderer.getWidth(orderedText) / 2f * textScale, centerY, 0);
            matrices.scale(textScale, textScale, textScale);
            textRenderer.draw(matrices, orderedText, 0, 0, 0xff000000);
            matrices.pop();
        }
    }

    public static class BedrockTheme extends MenuTheme {

        @Override
        public void drawBackground(MatrixStack matrices, int x, int y, int width, int height) {
            fill(matrices, x, y, x + width, y + height, 0xff1D1D1F);
            fill(matrices, x + 2, y + 2, x + width - 2, y + height - 2, 0xff34542C);
            fill(matrices, x + 2, y + 2, x + width - 2, y + height - 10, 0xff70A760);
            fill(matrices, x + 4, y + 4, x + width - 4, y + height - 12, 0xff3C8428);
        }

        @Override
        public void drawButton(Labeled button, MatrixStack matrices, int x, int y, int width, int height, boolean hovered) {
            fill(matrices, x, y, x + width, y + height, 0xff232325);
            fill(matrices, x + 2, y + 2, x + width - 2, y + height - 2, 0xff57595B);
            fill(matrices, x + 2, y + 2, x + width - 2, y + height - 6, 0xffEBECF0);
            fill(matrices, x + 4, y + 4, x + width - 4, y + height - 8, 0xffD0D2D4);
        }
    }
}
