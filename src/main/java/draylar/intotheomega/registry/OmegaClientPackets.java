package draylar.intotheomega.registry;

import draylar.intotheomega.entity.*;
import draylar.intotheomega.entity.matrite.MatriteEntity;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.PacketByteBuf;

import java.util.UUID;

public class OmegaClientPackets {

    public static void init() {
        ClientSidePacketRegistry.INSTANCE.register(InanisEntity.ENTITY_ID, (context, packet) -> {
            double x = packet.readDouble();
            double y = packet.readDouble();
            double z = packet.readDouble();

            int entityId = packet.readInt();
            UUID uuid = packet.readUuid();

            context.getTaskQueue().execute(() -> {
                InanisEntity inanis = new InanisEntity(OmegaEntities.INANIS, MinecraftClient.getInstance().world, x, y, z);
                inanis.setEntityId(entityId);
                inanis.setUuid(uuid);
                MinecraftClient.getInstance().world.addEntity(entityId, inanis);
            });
        });

        ClientSidePacketRegistry.INSTANCE.register(DualInanisEntity.ENTITY_ID, (context, packet) -> {
            double x = packet.readDouble();
            double y = packet.readDouble();
            double z = packet.readDouble();

            int entityId = packet.readInt();
            UUID uuid = packet.readUuid();

            context.getTaskQueue().execute(() -> {
                DualInanisEntity inanis = new DualInanisEntity(MinecraftClient.getInstance().world, x, y, z);
                inanis.setEntityId(entityId);
                inanis.setUuid(uuid);
                MinecraftClient.getInstance().world.addEntity(entityId, inanis);
            });
        });

        ClientSidePacketRegistry.INSTANCE.register(VioletUnionBladeEntity.ENTITY_ID, (context, packet) -> {
            double x = packet.readDouble();
            double y = packet.readDouble();
            double z = packet.readDouble();

            int entityId = packet.readInt();
            UUID uuid = packet.readUuid();

            context.getTaskQueue().execute(() -> {
                VioletUnionBladeEntity blade = new VioletUnionBladeEntity(MinecraftClient.getInstance().world, x, y, z);
                blade.setEntityId(entityId);
                blade.setUuid(uuid);
                MinecraftClient.getInstance().world.addEntity(entityId, blade);
            });
        });

        ClientSidePacketRegistry.INSTANCE.register(MatriteEntity.ENTITY_ID, (context, packet) -> {
            double x = packet.readDouble();
            double y = packet.readDouble();
            double z = packet.readDouble();

            int entityId = packet.readInt();
            UUID uuid = packet.readUuid();

            context.getTaskQueue().execute(() -> {
                MatriteEntity matrite = new MatriteEntity(MinecraftClient.getInstance().world, x, y, z);
                matrite.setEntityId(entityId);
                matrite.setUuid(uuid);
                MinecraftClient.getInstance().world.addEntity(entityId, matrite);
            });
        });

        ClientSidePacketRegistry.INSTANCE.register(MatrixBombEntity.SPAWN_PACKET, (context, packet) -> {
            double x = packet.readDouble();
            double y = packet.readDouble();
            double z = packet.readDouble();

            int entityID = packet.readInt();
            UUID entityUUID = packet.readUuid();

            context.getTaskQueue().execute(() -> {
                MatrixBombEntity proj = new MatrixBombEntity(MinecraftClient.getInstance().world, x, y, z, entityID, entityUUID);
                MinecraftClient.getInstance().world.addEntity(entityID, proj);
            });
        });

        ClientSidePacketRegistry.INSTANCE.register(OmegaSlimeMountEntity.SPAWN_PACKET, (context, packet) -> {
            double x = packet.readDouble();
            double y = packet.readDouble();
            double z = packet.readDouble();

            int entityID = packet.readInt();
            UUID entityUUID = packet.readUuid();

            context.getTaskQueue().execute(() -> {
                OmegaSlimeMountEntity proj = new OmegaSlimeMountEntity(MinecraftClient.getInstance().world, x, y, z, entityID, entityUUID);
                MinecraftClient.getInstance().world.addEntity(entityID, proj);
            });
        });

        ClientSidePacketRegistry.INSTANCE.register(ObsidianThornEntity.SPAWN_PACKET, (context, packet) -> {
            double x = packet.readDouble();
            double y = packet.readDouble();
            double z = packet.readDouble();

            int entityID = packet.readInt();
            UUID entityUUID = packet.readUuid();

            context.getTaskQueue().execute(() -> {
                ObsidianThornEntity thorn = new ObsidianThornEntity(MinecraftClient.getInstance().world, x, y, z, entityID, entityUUID);
                MinecraftClient.getInstance().world.addEntity(entityID, thorn);
            });
        });
    }

    public static void doubleJump(int slot) {
        PacketByteBuf packet = new PacketByteBuf(Unpooled.buffer());
        packet.writeInt(slot);
        ClientSidePacketRegistry.INSTANCE.sendToServer(OmegaServerPackets.DOUBLE_JUMP_PACKET, packet);
    }

    private OmegaClientPackets() {

    }
}
