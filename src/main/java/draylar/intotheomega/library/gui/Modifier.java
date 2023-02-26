package draylar.intotheomega.library.gui;

import java.util.function.Consumer;

public class Modifier {

    private Size width = Size.fixed(100);
    private Size height = Size.fixed(100);
    private Size x = Size.fixed(0);
    private Size y = Size.fixed(0);
    private BackgroundRenderer background = BackgroundRenderer.none();
    private MenuTheme theme;

    public static Modifier create() {
        return new Modifier();
    }

    public static Modifier create(Consumer<Modifier> consumer) {
        Modifier modifier = new Modifier();
        consumer.accept(modifier);
        return modifier;
    }

    public Modifier size(Size size) {
        height(size);
        width(size);
        return this;
    }

    public Modifier x(Size size) {
        this.x = size;
        return this;
    }

    public Modifier y(Size size) {
        this.y = size;
        return this;
    }

    public Modifier height(Size size) {
        this.height = size;
        return this;
    }

    public Modifier width(Size size) {
        this.width = size;
        return this;
    }

    public Modifier background(BackgroundRenderer background) {
        this.background = background;
        return this;
    }

    public void theme(MenuTheme theme) {
        this.theme = theme;
    }

    public Size getX() {
        return x;
    }

    public Size getY() {
        return y;
    }

    public Size getWidth() {
        return width;
    }

    public Size getHeight() {
        return height;
    }

    public BackgroundRenderer getBackground() {
        return background;
    }

    public MenuTheme getTheme() {
        return theme;
    }
}
