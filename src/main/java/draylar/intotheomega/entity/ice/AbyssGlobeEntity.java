package draylar.intotheomega.entity.ice;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class AbyssGlobeEntity extends Entity {

    @Nullable private UUID ownerUuid = null;

    public AbyssGlobeEntity(EntityType<?> type, World world) {
        super(type, world);
        ignoreCameraFrustum = true;

    }

    @Override
    public void tick() {
        super.tick();

        if(!world.isClient) {

            // Nearby mobs are slowed, debuffed, and slowly damaged.
            world.getEntitiesByClass(LivingEntity.class, new Box(getBlockPos()).expand(16, 3, 16), it -> it.getUuid() != ownerUuid).forEach(entity -> {
               entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 25, 0, true, false));
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 25, 0, true, false));
                entity.damage(DamageSource.GENERIC, 1.0f);
            });

            // After 10 seconds, remove the globe.
            if(age >= 10 * 20) {
                discard();
            }
        }
    }

    public void setOwner(@Nullable UUID owner) {
        this.ownerUuid = owner;
    }

    @Override
    public void initDataTracker() {

    }

    @Override
    public void readCustomDataFromNbt(NbtCompound tag) {

    }

    @Override
    public void writeCustomDataToNbt(NbtCompound tag) {

    }

    @Override
    public Packet<?> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }
}
