package draylar.intotheomega.library.gui;

import net.minecraft.client.util.math.MatrixStack;

public interface MenuNode<T> {

    void draw(MenuNode<?> parent, MatrixStack matrices);

    int getX();

    int getY();

    int getWidth();

    int getHeight();

    Modifier getModifier();

    void update(MenuNode<?> parent);
}
