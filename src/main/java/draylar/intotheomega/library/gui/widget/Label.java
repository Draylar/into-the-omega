package draylar.intotheomega.library.gui.widget;

import draylar.intotheomega.library.gui.MenuComponent;
import draylar.intotheomega.library.gui.MenuElement;
import draylar.intotheomega.library.gui.Modifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class Label extends MenuComponent<Button> {

    private final Text label;

    public Label(Modifier modifier, Text label) {
        super(modifier);
        this.label = label;
    }

    public static Label create(Modifier modifier, Text label) {
        return new Label(modifier, label);
    }

    @Override
    public void draw(MenuElement<?> parent, MatrixStack matrices, double mouseX, double mouseY) {
        getModifier().getBackground().drawText(label, matrices, getX(), getY(), getWidth(), getHeight());
    }
}
