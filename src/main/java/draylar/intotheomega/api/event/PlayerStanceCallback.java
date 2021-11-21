package draylar.intotheomega.api.event;

import draylar.intotheomega.api.client.Stance;
import draylar.intotheomega.api.client.Stances;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.TypedActionResult;

public interface PlayerStanceCallback {

    Event<PlayerStanceCallback> EVENT = EventFactory.createArrayBacked(PlayerStanceCallback.class,
            (listeners) -> (player, current) -> {
                Stance stance = Stances.NONE;

                for (PlayerStanceCallback callback : listeners) {
                    stance = callback.getStance(player, current);
                }

                return stance;
            }
    );

    Stance getStance(PlayerEntity player, Stance current);
}
