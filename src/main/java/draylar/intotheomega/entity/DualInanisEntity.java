package draylar.intotheomega.entity;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.registry.OmegaEntities;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class DualInanisEntity extends InanisEntity {

    public static final Identifier ENTITY_ID = IntoTheOmega.id("dual_inanis");

    public DualInanisEntity(EntityType<? extends InanisEntity> type, World world) {
        super(type, world);
    }

    public DualInanisEntity(EntityType<? extends InanisEntity> type, World world, LivingEntity owner, ItemStack source) {
        super(type, world, owner, source);
    }

    @Environment(EnvType.CLIENT)
    public DualInanisEntity(World world, double x, double y, double z) {
        super(OmegaEntities.DUAL_INANIS, world, x, y, z);
    }

    @Override
    public Packet<?> createSpawnPacket() {
        PacketByteBuf packet = new PacketByteBuf(Unpooled.buffer());

        packet.writeDouble(this.getX());
        packet.writeDouble(this.getY());
        packet.writeDouble(this.getZ());
        packet.writeInt(this.getEntityId());
        packet.writeUuid(this.getUuid());

        return ServerSidePacketRegistry.INSTANCE.toPacket(ENTITY_ID, packet);
    }
}
