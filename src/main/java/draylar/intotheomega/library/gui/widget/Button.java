package draylar.intotheomega.library.gui.widget;

import draylar.intotheomega.library.gui.MenuNode;
import draylar.intotheomega.library.gui.MenuWidget;
import draylar.intotheomega.library.gui.Modifier;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;

public class Button extends MenuWidget<Button> {

    private final Text label;

    public Button(Modifier modifier, Text label) {
        super(modifier);
        this.label = label;
    }

    public static Button create(Modifier modifier, Text label) {
        return new Button(modifier, label);
    }

    @Override
    public void draw(MenuNode<?> parent, MatrixStack matrices) {
        getModifier().getBackground().drawButton(matrices, getX(), getY(), getWidth(), getHeight());

        // Draw Label
        float centerX = getX() + getWidth() / 2f;
        float centerY = getY() + (getHeight() - 12) / 2f;
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        OrderedText orderedText = label.asOrderedText();
        textRenderer.draw(matrices, orderedText, (centerX - textRenderer.getWidth(orderedText) / 2), centerY, 0xff000000);
    }
}
