package draylar.intotheomega.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.world.World;

public class DualInanisEntity extends InanisEntity {

    public DualInanisEntity(EntityType<? extends InanisEntity> type, World world) {
        super(type, world);
    }

    public DualInanisEntity(EntityType<? extends InanisEntity> type, World world, LivingEntity owner, ItemStack source) {
        super(type, world, owner, source);
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }
}
