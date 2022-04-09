package draylar.intotheomega.registry.client;

import draylar.intotheomega.entity.*;
import draylar.intotheomega.entity.matrite.MatriteEntity;
import draylar.intotheomega.entity.void_matrix.VoidMatrixBeamEntity;
import draylar.intotheomega.registry.OmegaEntities;
import draylar.intotheomega.registry.OmegaServerPackets;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.PacketByteBuf;

import java.util.UUID;

@Environment(EnvType.CLIENT)
public class OmegaClientPackets {

    public static void init() {

    }

    public static void doubleJump(int slot) {
        PacketByteBuf packet = new PacketByteBuf(Unpooled.buffer());
        packet.writeInt(slot);
        ClientPlayNetworking.send(OmegaServerPackets.DOUBLE_JUMP_PACKET, packet);
    }

    private OmegaClientPackets() {

    }
}
