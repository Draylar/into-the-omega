package draylar.intotheomega.mixin;

import draylar.intotheomega.impl.AttackingItem;
import draylar.intotheomega.registry.OmegaServerPackets;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    @Shadow public ClientPlayerEntity player;

    @Inject(
            method = "doAttack",
            at = @At("HEAD")
    )
    private void onAttack(CallbackInfo ci) {
        // tell server to call attack method on held item
        PacketByteBuf packet = new PacketByteBuf(Unpooled.buffer());
        ClientSidePacketRegistry.INSTANCE.sendToServer(OmegaServerPackets.ATTACK_PACKET, packet);

        // call on client
        ItemStack playerHeldStack = player.getMainHandStack();
        if(playerHeldStack.getItem() instanceof AttackingItem) {
            ((AttackingItem) playerHeldStack.getItem()).attack(player, player.world, playerHeldStack);
        }
    }
}
