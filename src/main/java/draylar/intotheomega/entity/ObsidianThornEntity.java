package draylar.intotheomega.entity;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.registry.OmegaEntities;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ObsidianThornEntity extends Entity {

    private static final String PROGRESS_KEY = "Progress";
    public static final Identifier SPAWN_PACKET = IntoTheOmega.id("obsidian_thorn");
    public static final TrackedData<Integer> PROGRESS = DataTracker.registerData(ObsidianThornEntity.class, TrackedDataHandlerRegistry.INTEGER);

    @Environment(EnvType.CLIENT)
    private final List<BlockPos> renderBlocks = new ArrayList<>();

    public ObsidianThornEntity(EntityType<?> type, World world) {
        super(type, world);
        this.ignoreCameraFrustum = true;
    }

    @Environment(EnvType.CLIENT)
    public ObsidianThornEntity(World world, double x, double y, double z, int id, UUID uuid) {
        super(OmegaEntities.OBSIDIAN_THORN, world);
        updatePosition(x, y, z);
        updateTrackedPosition(x, y, z);
        setEntityId(id);
        setUuid(uuid);
    }

    @Override
    public void tick() {
        super.tick();

        if(!world.isClient) {
            dataTracker.set(PROGRESS, Math.min(20 * 3, dataTracker.get(PROGRESS) + 1));
            calculateDimensions();

            // Despawn at 10 seconds.
            if(age > 10 * 20) {
                kill();
            }
        }
    }

    @Override
    public void onPlayerCollision(PlayerEntity player) {
       if(!world.isClient) {
           Integer progress = dataTracker.get(PROGRESS);

           // Thorn is still spawning: launch players up into the air.
           if(progress > 30 && progress < 40) {
               player.damage(DamageSource.GENERIC, 5.0f);
               player.setVelocity(0, 2, 0);
               player.velocityDirty = true;
               player.velocityModified = true;
           }

           // Damage players touching the thorn slightly.
           else if (progress > 40) {
               player.damage(DamageSource.GENERIC, 2.0f);
           }
       }
    }

    @Environment(EnvType.CLIENT)
    public List<BlockPos> getRenderBlocks() {
        // First-time setup
        if(renderBlocks.isEmpty()) {
            int height = 5 + world.random.nextInt(2); // [5, 7)

            for(int y = 0; y < height; y++) {
                int radius = (height - y) / 2;

                // circle
                for(int x = -radius; x <= radius; x++) {
                    for(int z = -radius; z <= radius; z++) {
                        double distance = Math.sqrt(Math.pow(x + .5, 2) + Math.pow(z + .5, 2));

                        if(distance <= radius + world.random.nextInt(2)) {
                            renderBlocks.add(new BlockPos(x, y, z));
                        }
                    }
                }
            }
        }

        return renderBlocks;
    }

    @Override
    public void initDataTracker() {
        dataTracker.startTracking(PROGRESS, 0);
    }

    @Override
    public void readCustomDataFromTag(CompoundTag tag) {
        tag.putInt(PROGRESS_KEY, dataTracker.get(PROGRESS));
    }

    @Override
    public void writeCustomDataToTag(CompoundTag tag) {
        dataTracker.set(PROGRESS, tag.getInt(PROGRESS_KEY));
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

    public int getProgress() {
        return dataTracker.get(PROGRESS);
    }
}
