package draylar.intotheomega.entity;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.registry.OmegaEntities;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;
import java.util.UUID;

public class OmegaSlimeMountEntity extends Entity {

    public static final Identifier SPAWN_PACKET = IntoTheOmega.id("omega_slime_mount");
    private boolean pressingLeft;
    private boolean pressingRight;
    private boolean pressingForward;
    private boolean pressingBack;
    private boolean groundedLastTick = false;

    public OmegaSlimeMountEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    @Environment(EnvType.CLIENT)
    public OmegaSlimeMountEntity(World world, double x, double y, double z, int id, UUID uuid) {
        super(OmegaEntities.OMEGA_SLIME_MOUNT, world);
        updatePosition(x, y, z);
        updateTrackedPosition(x, y, z);
        setEntityId(id);
        setUuid(uuid);
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
    public void tick() {
        super.tick();
        float velocityDecay = 0.05F;
        double e = this.hasNoGravity() ? 0.0D : -0.03999999910593033D;

        if (this.isLogicalSideForUpdatingMovement()) {
            Vec3d vec3d = this.getVelocity();
            this.setVelocity(vec3d.x * (double) velocityDecay, vec3d.y + e, vec3d.z * (double)velocityDecay);
            Vec3d vec3d2 = this.getVelocity();
            this.setVelocity(vec3d2.x, (vec3d2.y - 0.06153846016296973D) , vec3d2.z);

            if(world.isClient) {
                updateMovement();
            }

            List<Entity> passengerList = getPassengerList();
            if(!passengerList.isEmpty()) {
                Entity entity = passengerList.get(0);
                setRotation(entity.yaw, entity.pitch);
            }

            this.move(MovementType.SELF, this.getVelocity());
        }

        if(!world.isClient) {
            if(!groundedLastTick && isOnGround()) {
                groundedLastTick = true;
                world.playSound(null, getX(), getY(), getZ(), SoundEvents.ENTITY_SLIME_JUMP, SoundCategory.PLAYERS, .25f, 1.2f);
            }

            else if (groundedLastTick && !isOnGround()) {
                groundedLastTick = false;
            }
        }
    }

    @Override
    public Entity getPrimaryPassenger() {
        List<Entity> list = this.getPassengerList();
        return list.isEmpty() ? null : (Entity)list.get(0);
    }

    private void updateMovement() {
        if (this.hasPassengers()) {
            float f = 0.0F;

            if (this.pressingRight != this.pressingLeft && !this.pressingForward && !this.pressingBack) {
                f += 0.005F;
            }

            if (this.pressingForward) {
                f += 0.04F;
            }

            if (this.pressingBack) {
                f -= 0.005F;
            }

            f *= 10;
            this.setVelocity(this.getVelocity().add((MathHelper.sin(-this.yaw * 0.017453292F) * f), 0.0D, (MathHelper.cos(this.yaw * 0.017453292F) * f)));
        }
    }

    @Override
    public void removePassenger(Entity passenger) {
        super.removePassenger(passenger);
        this.remove();
    }

    @Override
    public Packet<?> createSpawnPacket() {
        PacketByteBuf packet = new PacketByteBuf(Unpooled.buffer());

        // entity position
        packet.writeDouble(getX());
        packet.writeDouble(getY());
        packet.writeDouble(getZ());

        // entity id & uuid
        packet.writeInt(getEntityId());
        packet.writeUuid(getUuid());

        return ServerSidePacketRegistry.INSTANCE.toPacket(SPAWN_PACKET, packet);
    }

    public void jump() {
        if(onGround) {
            setVelocity(0, 1.5f, 0);
            world.playSound(null, getX(), getY(), getZ(), SoundEvents.ENTITY_SLIME_JUMP, SoundCategory.PLAYERS, .25f, 1.0f);
        }
    }

    @Environment(EnvType.CLIENT)
    public void setInputs(boolean pressingLeft, boolean pressingRight, boolean pressingForward, boolean pressingBack) {
        this.pressingLeft = pressingLeft;
        this.pressingRight = pressingRight;
        this.pressingForward = pressingForward;
        this.pressingBack = pressingBack;
    }
}
