package draylar.intotheomega.library.gui;

import draylar.intotheomega.library.gui.widget.Dropdown;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public abstract class BackgroundRenderer extends MenuTheme {

    public static BackgroundRenderer themed(MenuTheme theme) {
        return new Themed(theme);
    }

    public static BackgroundRenderer none() {
        return new None();
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
        public void drawButton(Labeled button, MatrixStack matrices, int x, int y, int width, int height, boolean hovered) {
            theme.drawButton(button, matrices, x, y, width, height, hovered);
        }

        @Override
        public void drawDropdown(Dropdown dropdown, MatrixStack matrices, int x, int y, int width, int height, boolean hovered) {
            theme.drawDropdown(dropdown, matrices, x, y, width, height, hovered);
        }

        @Override
        public void drawText(Text text, MatrixStack matrices, int x, int y, int width, int height) {
            theme.drawText(text, matrices, x, y, width, height);
        }
    }

    private static class None extends BackgroundRenderer {

        @Override
        public void drawBackground(MatrixStack matrices, int x, int y, int width, int height) {

        }

        @Override
        public void drawButton(Labeled button, MatrixStack matrices, int x, int y, int width, int height, boolean hovered) {

        }
    }
}
