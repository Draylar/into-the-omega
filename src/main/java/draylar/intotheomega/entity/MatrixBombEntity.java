package draylar.intotheomega.entity;

import draylar.intotheomega.IntoTheOmega;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.thrown.ThrownEntity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class MatrixBombEntity extends ThrownEntity implements IAnimatable {

    public static final Identifier SPAWN_PACKET = IntoTheOmega.id("matrix_bomb");

    public MatrixBombEntity(EntityType<? extends ThrownEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void initDataTracker() {

    }

    @Override
    public Packet<?> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }

    @Override
    public void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);

        if(!world.isClient) {
            world.createExplosion(this, getX(), getY(), getZ(), 3, Explosion.DestructionType.NONE);
            world.sendEntityStatus(this, (byte) 3);
            discard();
        }
    }

    @Override
    public void registerControllers(AnimationData animationData) {

    }

    @Override
    public AnimationFactory getFactory() {
        return null;
    }
}
