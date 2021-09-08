package draylar.intotheomega.entity.void_matrix;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.registry.OmegaEntities;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

public class VoidMatrixBeamEntity extends Entity {

    public static final Identifier ENTITY_ID = IntoTheOmega.id("vm_beam");
    public static final int DEATH_TIME = 20 * 3;

    public VoidMatrixBeamEntity(EntityType<?> type, World world) {
        super(type, world);
        ignoreCameraFrustum = true;
    }

    @Environment(EnvType.CLIENT)
    public VoidMatrixBeamEntity(ClientWorld world, double x, double y, double z) {
        super(OmegaEntities.VOID_MATRIX_BEAM, world);
        this.updatePosition(x, y, z);
        this.updateTrackedPosition(x, y, z);
    }

    @Override
    public void tick() {
        super.tick();

        if(!world.isClient) {

            if(age == 1) {
                ((ServerWorld) world).playSound(null, getX(), getY(), getZ(), SoundEvents.BLOCK_BEACON_ACTIVATE, SoundCategory.HOSTILE, 5.0f, 2.0f);
            }

            // Beam explodes at 2 seconds
            if(age == 40) {
                world.playSound(null, getX(), getY(), getZ(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.HOSTILE, 1.0f, 3.0f);

                // attack players
                world.getEntitiesByClass(LivingEntity.class, new Box(getBlockPos().add(-3, -100, -3), getBlockPos().add(3, 100, 3)), entity -> !(entity instanceof VoidMatrixEntity)).forEach(e -> {
                    double scale = (4 - Math.min(3, (float) Math.sqrt(Math.pow(e.getX() - getX(), 2) + Math.pow(e.getZ() - getZ(), 2)))) / 4;
                    e.damage(DamageSource.GENERIC, 15.0f * (float) scale);
                });
            }

            // Kill after death time is up
            if (age > DEATH_TIME) {
                remove();
            }
        }
    }

    @Override
    protected void initDataTracker() {

    }

    @Override
    protected void readCustomDataFromTag(CompoundTag tag) {

    }

    @Override
    protected void writeCustomDataToTag(CompoundTag tag) {

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

    @Override
    public void pushAwayFrom(Entity entity) {

    }

    @Override
    public boolean shouldRender(double distance) {
        return true;
    }
}
