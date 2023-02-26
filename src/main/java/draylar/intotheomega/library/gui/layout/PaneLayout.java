package draylar.intotheomega.library.gui.layout;

import draylar.intotheomega.library.gui.MenuNode;
import draylar.intotheomega.library.gui.MenuWidget;
import draylar.intotheomega.library.gui.Modifier;
import net.minecraft.client.util.math.MatrixStack;

public class PaneLayout extends MenuWidget<PaneLayout> {

    private final MenuNode<?>[] nodes;

    public PaneLayout(Modifier modifier, MenuNode<?>... nodes) {
        super(modifier);
        this.nodes = nodes;
    }

    public static PaneLayout create(Modifier modifier, MenuNode<?>... nodes) {
        return new PaneLayout(modifier, nodes);
    }

    @Override
    public void draw(MenuNode<?> parent, MatrixStack matrices) {
        // Draw Pane background
        getModifier().getBackground().drawBackground(
                matrices,
                getX(),
                getY(),
                getWidth(),
                getHeight()
        );

        // Draw children nodes
        for (MenuNode<?> node : nodes) {
            node.update(this);
            node.draw(parent, matrices);
        }
    }
}
