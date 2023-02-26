package draylar.intotheomega.library.gui;

import net.minecraft.client.util.math.MatrixStack;

public abstract class BackgroundRenderer extends MenuTheme {

    public static BackgroundRenderer themed(MenuTheme theme) {
        return new Themed(theme);
    }

    public static BackgroundRenderer none() {
        return new None();
    }

    @Override
    public void drawBackground(MatrixStack matrices, int x, int y, int width, int height) {

    }

    @Override
    public void drawButton(MatrixStack matrices, int x, int y, int width, int height) {

    }

    private static class Themed extends BackgroundRenderer {

        private final MenuTheme theme;

        public Themed(MenuTheme theme) {
            this.theme = theme;
        }

        @Override
        public void drawBackground(MatrixStack matrices, int x, int y, int width, int height) {
            theme.drawBackground(matrices, x, y, width, height);
        }

        @Override
        public void drawButton(MatrixStack matrices, int x, int y, int width, int height) {
            theme.drawButton(matrices, x, y, width, height);
        }
    }

    private static class None extends BackgroundRenderer {

    }
}
