package draylar.intotheomega.registry.client;

import draylar.intotheomega.api.client.StanceProvider;
import draylar.intotheomega.api.client.Stances;
import draylar.intotheomega.api.event.PlayerStanceCallback;
import draylar.intotheomega.entity.VoidFloaterEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

public class OmegaClientEventHandlers {

    public static void init() {
        registerItemStanceHandler();
        registerVoidFloaterHandler();
    }

    private static void registerItemStanceHandler() {
        PlayerStanceCallback.EVENT.register((player, current) -> {
            ItemStack stack = player.getStackInHand(Hand.MAIN_HAND);

            if(true) {
                return Stances.SCYTHE_SPIN;
            }

            if (stack.isEmpty() || !(stack.getItem() instanceof StanceProvider)) {
                return current;
            } else {
                if (player.getActiveHand() == Hand.MAIN_HAND && player.getItemUseTimeLeft() > 0) {
                    StanceProvider stance = (StanceProvider) stack.getItem();
                    return stance.getUseStance(stack);
                }
            }

            return current;
        });
    }

    private static void registerVoidFloaterHandler() {
        PlayerStanceCallback.EVENT.register((player, current) -> {
            if(player.getVehicle() instanceof VoidFloaterEntity) {
                return Stances.GLIDING;
            }

            return current;
        });
    }
}
