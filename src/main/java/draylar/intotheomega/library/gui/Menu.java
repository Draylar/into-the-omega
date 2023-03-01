package draylar.intotheomega.library.gui;

import draylar.intotheomega.library.gui.state.MutableState;
import draylar.intotheomega.library.gui.vanilla.MenuScreen;
import net.minecraft.client.MinecraftClient;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public abstract class Menu {

    private static final Map<String, Supplier<Menu>> FACTORIES = new HashMap<>();

    public static void register(String id, Supplier<Menu> factory) {
        FACTORIES.put(id, factory);
    }

    public static void open(String id) {
        @Nullable Supplier<Menu> menuSupplier = FACTORIES.get(id);
        if(menuSupplier == null) {
            throw new IllegalStateException("Unable to open client-side Menu with ID " + id);
        }

        MenuScreen screen = new MenuScreen(menuSupplier.get());
        MinecraftClient.getInstance().setScreen(screen);
    }

    public final <T> MutableState<T> state(Class<T> type, T value) {
        return new MutableState<T>(value);
    }

    public abstract MenuElement<?> compose();

    public boolean pause() {
        return false;
    }
}
