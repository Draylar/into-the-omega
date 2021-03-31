package draylar.intotheomega.api;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface TelosProvider {

    /**
     * Returns the raw numerical max-Telos bonus of this provider.
     * @param player  player being operated on
     * @param world   world of the player
     * @param stack   stack that is providing the telos
     * @return        the amount of max-Telos to provide the player from this stack
     */
    default double getCapacityRaw(PlayerEntity player, World world, ItemStack stack) {
        return 0.0;
    }

    /**
     * Returns the max-Telos multiplier bonus of this provider.
     * Multiplication bonuses are applied after all {@link TelosProvider} raw bonuses have been summed.
     * Keep in mind this will jump very quickly (1.1, for example, is already a large jump).
     *
     * @param player  player being operated on
     * @param world   world of the player
     * @param stack   stack that is providing the telos
     * @return        the max-Telos multiplier for the player from this stack
     */
    default double getCapacityMultiplier(PlayerEntity player, World world, ItemStack stack) {
        return 1.0;
    }

    default double getRegenerationRaw() {
        return 0.0;
    }

    default double getRegenerationMultiplier() {
        return 0.0;
    }
}
