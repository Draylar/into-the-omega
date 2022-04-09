package draylar.intotheomega.registry;

import dev.emi.trinkets.api.TrinketsApi;
import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.impl.AttackingItem;
import draylar.intotheomega.impl.DoubleJumpTrinket;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.lwjgl.system.CallbackI;

public class OmegaServerPackets {

    public static final Identifier DOUBLE_JUMP_PACKET = IntoTheOmega.id("double_jump");
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

        ServerPlayNetworking.registerGlobalReceiver(DOUBLE_JUMP_PACKET, (server, player, handler, buf, responseSender) -> {
            int slot = buf.readInt();

            server.execute(() -> {
                TrinketsApi.getTrinketComponent(player).ifPresent(component -> {
                    component.getAllEquipped().stream().filter(entry -> entry.getLeft().index() == slot).forEach(entry -> {
                        ItemStack stack = entry.getRight();
                        if(stack.getItem() instanceof DoubleJumpTrinket doubleJumpTrinket) {
                            doubleJumpTrinket.onDoubleJump(player.world, player, stack);
                        }
                    });
                });
            });
        });
    }

    private OmegaServerPackets() {
        // NO-OP
    }
}
