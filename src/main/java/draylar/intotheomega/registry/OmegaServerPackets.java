package draylar.intotheomega.registry;

import dev.emi.trinkets.api.TrinketsApi;
import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.impl.AttackingItem;
import draylar.intotheomega.impl.DoubleJumpTrinket;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
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

        ServerSidePacketRegistry.INSTANCE.register(DOUBLE_JUMP_PACKET, (context, packet) -> {
            int slot = packet.readInt();
            PlayerEntity player = context.getPlayer();

            context.getTaskQueue().execute(() -> {
                Inventory inventory = TrinketsApi.getTrinketsInventory(player);
                ItemStack stack = inventory.getStack(slot);
                Item item = stack.getItem();

                if(item instanceof DoubleJumpTrinket) {
                    ((DoubleJumpTrinket) item).onDoubleJump(player.world, player, stack);
                }
            });
        });
    }

    private OmegaServerPackets() {
        // NO-OP
    }
}
