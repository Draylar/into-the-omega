package draylar.intotheomega.library.gui.layout;

import draylar.intotheomega.library.gui.MenuComponent;
import draylar.intotheomega.library.gui.MenuElement;
import draylar.intotheomega.library.gui.Modifier;
import net.minecraft.client.util.math.MatrixStack;

import java.util.Arrays;

public class PaneLayout extends MenuComponent<PaneLayout> {

    public PaneLayout(Modifier modifier, MenuElement<?>... nodes) {
        super(modifier);
        getChildren().addAll(Arrays.asList(nodes));
    }

    public static PaneLayout create(Modifier modifier, MenuElement<?>... nodes) {
        return new PaneLayout(modifier, nodes);
    }

    @Override
    public void draw(MenuElement<?> parent, MatrixStack matrices, double mouseX, double mouseY) {
        // Draw Pane background
        getModifier().getBackground().drawBackground(
                matrices,
                getX(),
                getY(),
                getWidth(),
                getHeight()
        );

        // Draw children nodes
        super.draw(parent, matrices, mouseX, mouseY);
    }
}
