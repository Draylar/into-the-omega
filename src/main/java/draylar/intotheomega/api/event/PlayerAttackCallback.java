package draylar.intotheomega.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

public interface PlayerAttackCallback {

    Event<PlayerAttackCallback> EVENT = EventFactory.createArrayBacked(PlayerAttackCallback.class,
            (listeners) -> (hit, player, amount) -> {
                for (PlayerAttackCallback callback : listeners) {
                    amount = callback.attack(hit, player, amount);
                }

                return amount;
            }
    );

    float attack(Entity hit, PlayerEntity player, float amount);
}
