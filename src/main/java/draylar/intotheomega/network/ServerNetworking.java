package draylar.intotheomega.network;

import draylar.intotheomega.registry.OmegaBlocks;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;

public class ServerNetworking implements Networking {

    public static void init() {
        ServerPlayNetworking.registerGlobalReceiver(PHASE_PAD_TELEPORT_REQUEST, (server, player, serverPlayNetworkHandler, packet, sender) -> {
            BlockPos to = packet.readBlockPos();
            BlockPos under = player.getBlockPos().down();
            ServerWorld world = player.getWorld();

            if(world.getBlockState(under).getBlock().equals(OmegaBlocks.PHASE_PAD)) {
                if(world.getBlockState(to).getBlock().equals(OmegaBlocks.PHASE_PAD)) {
                    double distance = Math.sqrt(under.getSquaredDistance(to));

                    if(distance <= 32) {
                        player.teleport(to.getX() + .5, to.getY() + 1, to.getZ() + .5);
                        player.playSound(SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1, 1);
                    }
                }
            }
        });
    }

    private ServerNetworking() {
        // NO-OP
    }

    public static void updateIceIsland(ServerPlayerEntity player, boolean inIceIsland) {
        PacketByteBuf packet = new PacketByteBuf(Unpooled.buffer());
        packet.writeBoolean(inIceIsland);
        ServerPlayNetworking.send(player, ICE_ISLAND_UPDATE, packet);
    }
}
