package draylar.intotheomega.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.TypedActionResult;

public interface EntityJumpCallback {

    Event<EntityJumpCallback> EVENT = EventFactory.createArrayBacked(EntityJumpCallback.class,
            (listeners) -> (entity, amount) -> {
                for (EntityJumpCallback callback : listeners) {
                    TypedActionResult<Float> result = callback.jump(entity, amount);
                    amount = result.getValue();
                    if(result.getResult() != ActionResult.PASS) {
                        return result;
                    }
                }

                return TypedActionResult.pass(amount);
            }
    );

    TypedActionResult<Float> jump(LivingEntity entity, float velocity);
}
