package draylar.intotheomega.library.gui;

import net.minecraft.client.gui.DrawableHelper;

import java.util.ArrayList;
import java.util.List;

public abstract class MenuComponent<T> extends DrawableHelper implements MenuElement<T> {

    private final List<MenuElement<?>> children = new ArrayList<>();
    private final Modifier modifier;
    private int x;
    private int y;
    private int width;
    private int height;

    public MenuComponent(Modifier modifier) {
        this.modifier = modifier;
    }

    public void rebuiltLayout(MenuElement<?> parent) {
        this.x = parent.getX() + modifier.getX().get(parent, this);
        this.y = parent.getY() + modifier.getY().get(parent, this);
        this.width = modifier.getWidth().get(parent, this);
        this.height = modifier.getHeight().get(parent, this);

        for (MenuElement<?> child : getChildren()) {
            child.rebuiltLayout(this);
        }
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
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

    @Override
    public final List<MenuElement<?>> getChildren() {
        return children;
    }

    public void addChild(MenuElement<?> element) {
        children.add(element);
    }
}
