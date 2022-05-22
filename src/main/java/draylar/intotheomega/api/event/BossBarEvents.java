package draylar.intotheomega.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class BossBarEvents {

    public static Event<AddHandler> ADD = EventFactory.createArrayBacked(AddHandler.class, listeners -> (entity, uuid, text, percent, color, style, dark, dragon, thic) -> {
        for (AddHandler event : listeners) {
//            event.addBossBar();
        }
    });

    public static Event<RemoveHandler> REMOVE = EventFactory.createArrayBacked(RemoveHandler.class, listeners -> (entity, uuid) -> {
        for (RemoveHandler event : listeners) {
//            event.removeBossBar();
        }
    });

    @FunctionalInterface
    public interface AddHandler {

        void addBossBar(@Nullable Entity entity, @NotNull UUID uuid, @NotNull Text name, float percent, @NotNull BossBar.Color color, @NotNull BossBar.Style style, boolean darkenSky, boolean dragonMusic, boolean thickenFog);
    }

    @FunctionalInterface
    public interface RemoveHandler {

        void removeBossBar(@Nullable Entity entity, @NotNull UUID uuid);
    }
}

