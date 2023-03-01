package draylar.intotheomega.library.gui.vanilla;

import draylar.intotheomega.library.gui.Menu;
import draylar.intotheomega.library.gui.MenuElement;
import draylar.intotheomega.library.gui.Modifier;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;

public class MenuScreen extends Screen implements MenuElement<MenuScreen> {

    private final Menu root;
    private MenuElement<?> composed;

    public MenuScreen(Menu root) {
        super(LiteralText.EMPTY);
        this.root = root;
        this.composed = root.compose();
        rebuiltLayout(null);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        draw(this, matrices, mouseX, mouseY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        boolean any = false;
        for (MenuElement<?> element : composed.getChildren()) {
            any = any || element.click(mouseX, mouseY, button);
        }

        return any;
    }

    @Override
    public void tick() {
        composed.tick();
    }

    @Override
    public boolean shouldPause() {
        return root.pause();
    }

    @Override
    public void draw(MenuElement<?> parent, MatrixStack matrices, double mouseX, double mouseY) {
        composed.draw(parent, matrices, mouseX, mouseY);
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
    public void rebuiltLayout(MenuElement<?> parent) {
        composed.rebuiltLayout(this);
    }

    @Override
    public void resize(MinecraftClient client, int width, int height) {
        super.resize(client, width, height);
        rebuiltLayout(null);
    }
}
