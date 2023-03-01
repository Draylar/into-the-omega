package draylar.intotheomega.library.gui.widget;

import draylar.intotheomega.library.gui.Labeled;
import draylar.intotheomega.library.gui.MenuElement;
import draylar.intotheomega.library.gui.MenuComponent;
import draylar.intotheomega.library.gui.Modifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class Button extends MenuComponent<Button> implements Labeled {

    private final Text label;
    private final Runnable onClick;

    public Button(Modifier modifier, Text label, Runnable onClick) {
        super(modifier);
        this.label = label;
        this.onClick = onClick;
    }

    public static Button create(Modifier modifier, Text label) {
        return create(modifier, label, () -> {});
    }

    public static Button create(Modifier modifier, Text label, Runnable onClick) {
        return new Button(modifier, label, onClick);
    }

    @Override
    public boolean click(double mouseX, double mouseY, int button) {
        if(isHovered(mouseX, mouseY)) {
            onClick.run();
            return true;
        }

        return false;
    }

    @Override
    public void draw(MenuElement<?> parent, MatrixStack matrices, double mouseX, double mouseY) {
        getModifier().getBackground().drawButton(this, matrices, getX(), getY(), getWidth(), getHeight(), isHovered(mouseX, mouseY));
    }

    @Override
    public Text getLabel() {
        return label;
    }
}
