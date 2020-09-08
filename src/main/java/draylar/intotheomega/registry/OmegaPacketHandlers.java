package draylar.intotheomega.registry;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.impl.AttackingItem;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class OmegaPacketHandlers {

    public static final Identifier ATTACK_PACKET = IntoTheOmega.id("attack");

    public static void init() {
        ServerSidePacketRegistry.INSTANCE.register(ATTACK_PACKET, (context, packet) -> {
            context.getTaskQueue().execute(() -> {
                ItemStack playerHeldStack = context.getPlayer().getMainHandStack();

                if(playerHeldStack.getItem() instanceof AttackingItem) {
                    ((AttackingItem) playerHeldStack.getItem()).attack(context.getPlayer(), context.getPlayer().getEntityWorld(), playerHeldStack);
                }
            });
        });
    }

    private OmegaPacketHandlers() {
        // NO-OP
    }
}
