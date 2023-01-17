package draylar.intotheomega.registry.client;

import draylar.intotheomega.api.data.player.PlayerDataAttachment;
import draylar.intotheomega.registry.OmegaServerPackets;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;

@Environment(EnvType.CLIENT)
public class OmegaClientPackets {

    public static void init() {
        ClientPlayNetworking.registerGlobalReceiver(OmegaServerPackets.SYNC_PLAYER_DATA, (client, handler, buf, responseSender) -> {
            int playerId = buf.readInt();
            NbtCompound data = buf.readNbt();
            client.execute(() -> {
                Entity found = client.world.getEntityById(playerId);
                if(found instanceof PlayerEntity player) {
                    PlayerDataAttachment.read(player, data);
                }
            });
        });
    }

    public static void doubleJump(int slot) {
        PacketByteBuf packet = new PacketByteBuf(Unpooled.buffer());
        packet.writeInt(slot);
        ClientPlayNetworking.send(OmegaServerPackets.DOUBLE_JUMP_PACKET, packet);
    }

    private OmegaClientPackets() {

    }
}
