package draylar.intotheomega.library.gui.vanilla;

import draylar.intotheomega.library.gui.Menu;
import draylar.intotheomega.library.gui.MenuNode;
import draylar.intotheomega.library.gui.Modifier;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;

public class MenuScreen extends Screen implements MenuNode<MenuScreen> {

    private final Menu root;
    private MenuNode<?> composed;

    public MenuScreen(Menu root) {
        super(LiteralText.EMPTY);
        this.root = root;
        this.composed = root.compose();
        update(null);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        draw(this, matrices);
    }

    @Override
    public boolean shouldPause() {
        return root.pause();
    }

    @Override
    public void draw(MenuNode<?> parent, MatrixStack matrices) {
        composed.draw(parent, matrices);
    }

    @Override
    public int getX() {
        return 0;
    }

    @Override
    public int getY() {
        return 0;
    }

    @Override
    public int getWidth() {
        return MinecraftClient.getInstance().getWindow().getScaledWidth();
    }

    @Override
    public int getHeight() {
        return MinecraftClient.getInstance().getWindow().getScaledHeight();
    }

    @Override
    public Modifier getModifier() {
        return null;
    }

    @Override
    public void update(MenuNode<?> parent) {
        composed.update(this);
    }

    @Override
    public void resize(MinecraftClient client, int width, int height) {
        super.resize(client, width, height);
        update(null);
    }
}
