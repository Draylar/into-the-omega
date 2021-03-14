package draylar.intotheomega.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.TypedActionResult;

public interface PlayerDamageCallback {

    Event<PlayerDamageCallback> EVENT = EventFactory.createArrayBacked(PlayerDamageCallback.class,
            (listeners) -> (player, source, amount) -> {
                for (PlayerDamageCallback callback : listeners) {
                    TypedActionResult<Float> res = callback.onDamage(player, source, amount);
                    amount = res.getValue();

                    if(!res.getResult().equals(ActionResult.PASS)) {
                        return res;
                    }
                }

                return TypedActionResult.pass(amount);
            }
    );

    TypedActionResult<Float> onDamage(PlayerEntity player, DamageSource source, float amount);
}
