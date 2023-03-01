package draylar.intotheomega.library.gui.widget;

import draylar.intotheomega.library.gui.*;
import draylar.intotheomega.library.gui.state.MutableState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class Dropdown extends MenuComponent<Dropdown> implements Labeled {

    private final Type type;
    private final List<String> entries;
    private final MutableState<Integer> selected;
    private boolean hovered = false;

    public Dropdown(Modifier modifier, Type type, List<String> entries, MutableState<Integer> selected) {
        super(modifier);
        this.type = type;
        this.entries = entries;
        this.selected = selected;

        for (int i = 0; i < entries.size(); i++) {
            int selectionIndex = i;
            addChild(new Button(Modifier.create().background(BackgroundRenderer.themed(MenuThemes.MODERN)), new LiteralText(entries.get(i)), () -> {
                selected.set(selectionIndex);
            }));
        }
    }

    public static Dropdown create(Modifier modifier, Type type, List<String> entries, MutableState<Integer> selected) {
        return new Dropdown(modifier, type, entries, selected);
    }

    @Override
    public boolean click(double mouseX, double mouseY, int button) {
        boolean success = false;
        if(hovered) {
            for (MenuElement<?> child : getChildren()) {
                success = success || child.click(mouseX, mouseY, button);
            }
        }

        return success;
    }

    @Override
    public void rebuiltLayout(MenuElement<?> parent) {
        super.rebuiltLayout(parent);

        int heightUp = 0;
        for (MenuElement<?> child : getChildren()) {
            if(child instanceof Button button) {
                button.setWidth(getWidth());
                button.setHeight(getHeight());

                button.setY(getY() - child.getHeight() + heightUp);
                button.setX(getX());

                heightUp -= child.getHeight();
            }
        }
    }

    @Override
    public void draw(MenuElement<?> parent, MatrixStack matrices, double mouseX, double mouseY) {
        getModifier().getBackground().drawDropdown(
                this,
                matrices,
                getX(),
                getY(),
                getWidth(),
                getHeight(),
                isHovered(mouseX, mouseY)
        );

        if(isHovered(mouseX, mouseY)) {
            for (MenuElement<?> child : getChildren()) {
                child.draw(this, matrices, mouseX, mouseY);
            }

            hovered = true;
        } else {
            hovered = false;
        }

        fill(matrices, getX(), getY() + getHeight() - 1, getX() + getWidth(), getY() + getHeight(), 0xff00eaff);
    }

    @Override
    public boolean isHovered(double mouseX, double mouseY) {
        if(hovered) {
            int maxY = getY() + getHeight();
            int minY = getY();
            for (MenuElement<?> child : getChildren()) {
                minY -= child.getHeight();
            }

            return mouseX >= getX() && mouseX <= getX() + getWidth()
                    && mouseY >= minY && mouseY <= maxY;
        }

        return super.isHovered(mouseX, mouseY);
    }

    @Override
    public Text getLabel() {
        return new LiteralText(entries.get(selected.getValue()));
    }

    public Type getType() {
        return type;
    }

    public List<String> getEntries() {
        return entries;
    }

    public int getSelected() {
        return selected.getValue();
    }

    public enum Type {
        UPWARDS,
        DOWNWARDS
    }
}
