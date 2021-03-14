package draylar.intotheomega.network;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.IntoTheOmegaClient;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockPos;

public class ClientNetworking implements Networking {

    public static void requestPhasePadTeleport(BlockPos to) {
        PacketByteBuf packet = new PacketByteBuf(Unpooled.buffer());
        packet.writeBlockPos(to);
        ClientPlayNetworking.send(PHASE_PAD_TELEPORT_REQUEST, packet);
    }

    public static void init() {
        ClientPlayNetworking.registerGlobalReceiver(ICE_ISLAND_UPDATE, (client, clientPlayNetworkHandler, packetByteBuf, packetSender) -> {
            boolean in = packetByteBuf.readBoolean();

            client.execute(() -> {
                IntoTheOmegaClient.inIceIsland = in;
            });
        });
    }

    private ClientNetworking() {
        // NO-OP
    }
}
