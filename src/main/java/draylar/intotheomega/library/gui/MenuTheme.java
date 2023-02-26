package draylar.intotheomega.library.gui;

import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

public abstract class MenuTheme extends DrawableHelper {

    public static final MenuTheme BEDROCK = new BedrockTheme();

    public abstract void drawBackground(MatrixStack matrices, int x, int y, int width, int height);

    public abstract void drawButton(MatrixStack matrices, int x, int y, int width, int height);

    public static class BedrockTheme extends MenuTheme {

        @Override
        public void drawBackground(MatrixStack matrices, int x, int y, int width, int height) {
            fill(matrices, x, y, x + width, y + height, 0xff1D1D1F);
            fill(matrices, x + 2, y + 2, x + width - 2, y + height - 2, 0xff34542C);
            fill(matrices, x + 2, y + 2, x + width - 2, y + height - 10, 0xff70A760);
            fill(matrices, x + 4, y + 4, x + width - 4, y + height - 12, 0xff3C8428);
        }

        @Override
        public void drawButton(MatrixStack matrices, int x, int y, int width, int height) {
            fill(matrices, x, y, x + width, y + height, 0xff232325);
            fill(matrices, x + 2, y + 2, x + width - 2, y + height - 2, 0xff57595B);
            fill(matrices, x + 2, y + 2, x + width - 2, y + height - 6, 0xffEBECF0);
            fill(matrices, x + 4, y + 4, x + width - 4, y + height - 8, 0xffD0D2D4);
        }
    }
}
