package draylar.intotheomega.library.gui;

import net.minecraft.client.gui.DrawableHelper;

public abstract class MenuWidget<T> extends DrawableHelper implements MenuNode<T> {

    private final Modifier modifier;
    private int x;
    private int y;
    private int width;
    private int height;

    public MenuWidget(Modifier modifier) {
        this.modifier = modifier;
    }

    public final void update(MenuNode<?> parent) {
        this.x = parent.getX() + modifier.getX().get(parent);
        this.y = parent.getY() + modifier.getY().get(parent);
        this.width = modifier.getWidth().get(parent);
        this.height = modifier.getHeight().get(parent);
    }

    @Override
    public final int getX() {
        return x;
    }

    @Override
    public final int getY() {
        return y;
    }

    @Override
    public final int getWidth() {
        return width;
    }

    @Override
    public final int getHeight() {
        return height;
    }

    @Override
    public final Modifier getModifier() {
        return modifier;
    }
}
