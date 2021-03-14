package draylar.intotheomega.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.ActionResult;
import net.minecraft.util.TypedActionResult;

public interface ExplosionDamageEntityCallback {

    Event<ExplosionDamageEntityCallback> EVENT = EventFactory.createArrayBacked(ExplosionDamageEntityCallback.class,
            (listeners) -> (hit, source, amount) -> {
                for (ExplosionDamageEntityCallback callback : listeners) {
                    TypedActionResult<Float> res = callback.onDamage(hit, source, amount);
                    amount = res.getValue();

                    if(!res.getResult().equals(ActionResult.PASS)) {
                        return res;
                    }
                }

                return TypedActionResult.pass(amount);
            }
    );

    TypedActionResult<Float> onDamage(Entity hit, DamageSource source, float amount);
}
