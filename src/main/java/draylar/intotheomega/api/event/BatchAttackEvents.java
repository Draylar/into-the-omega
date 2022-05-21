package draylar.intotheomega.api.event;

import draylar.intotheomega.api.BatchDamage;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;

/**
 * Batch attacks against multiple enemies can be dealt through the {@link BatchDamage} API.
 * These events allow developers to react to batch attacks, before and after they occur.
 */
public class BatchAttackEvents {

    public static final Event<Before> BEFORE = EventFactory.createArrayBacked(Before.class,
            (listeners) -> (player, data) -> {
                for (Before callback : listeners) {
                    callback.attack(player, data);
                }
            }
    );

    public static final Event<After> AFTER = EventFactory.createArrayBacked(After.class,
            (listeners) -> (player, data) -> {
                for (After callback : listeners) {
                    callback.attack(player, data);
                }
            }
    );

    @FunctionalInterface
    public interface Before {

        /**
         * Called before a batch-damage (multiple target) attack takes place.
         *
         * <p>
         * The mutable {@link BatchDamage.BatchAttackData} class can be tweaked to change damage & critical information about the attack before it occurs.
         *
         * @param player player dealing the batch attack
         * @param data   mutable metadata about the attack
         * @return
         */
        void attack(PlayerEntity player, BatchDamage.BatchAttackData data);
    }

    @FunctionalInterface
    public interface After {

        /**
         * Called after a batch-damage (multiple target) attack takes place.
         *
         * @param player player dealing the batch attack
         * @param data metadata about the attack that just occurred
         */
        void attack(PlayerEntity player, BatchDamage.BatchAttackData data);
    }
}
