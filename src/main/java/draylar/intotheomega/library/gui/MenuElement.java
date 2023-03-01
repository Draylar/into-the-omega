package draylar.intotheomega.library.gui;

import net.minecraft.client.util.math.MatrixStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public interface MenuElement<T> {

    default void draw(MenuElement<?> parent, MatrixStack matrices, double mouseX, double mouseY) {
        for (MenuElement<?> node : getChildren()) {
            node.draw(parent, matrices, mouseX, mouseY);
        }
    }

    int getX();

    int getY();

    int getWidth();

    int getHeight();

    Modifier getModifier();

    void rebuiltLayout(MenuElement<?> parent);

    default boolean click(double mouseX, double mouseY, int button) {
        boolean success = false;
        for (MenuElement<?> child : getChildren()) {
            success = success || child.click(mouseX, mouseY, button);
        }

        return success;
    }

    default void tick() {
        for (MenuElement<?> node : getChildren()) {
            node.tick();
        }
    }

    default boolean isHovered(double mouseX, double mouseY) {
        return mouseX >= getX() && mouseX <= getX() + getWidth()
                && mouseY >= getY() && mouseY <= getY() + getHeight();
    }

    default List<? extends MenuElement<?>> getChildren() {
        return Collections.emptyList();
    }

    default List<? extends MenuElement<?>> findAllChildren() {
        List<MenuElement<?>> all = new ArrayList<>(getChildren());
        for (MenuElement<?> child : getChildren()) {
            all.addAll(child.findAllChildren());
        }

        return all;
    }
}
