package draylar.intotheomega.entity.void_matrix.beam;

import draylar.intotheomega.entity.void_matrix.VoidMatrixEntity;
import draylar.intotheomega.registry.OmegaParticles;
import draylar.intotheomega.util.ParticleUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

public class VoidMatrixBeamEntity extends Entity {

    public static final int DEATH_TIME = 20 * 3;

    public VoidMatrixBeamEntity(EntityType<?> type, World world) {
        super(type, world);
        ignoreCameraFrustum = true;
    }

    @Override
    public void tick() {
        super.tick();

        if(!world.isClient) {

            if(age == 1) {
                world.playSound(null, getX(), getY(), getZ(), SoundEvents.BLOCK_BEACON_ACTIVATE, SoundCategory.HOSTILE, 5.0f, 2.0f);
            }

            // Beam explodes at 2 seconds
            if(age == 25) {
                world.playSound(null, getX(), getY(), getZ(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.HOSTILE, 5.0f, 3.0f);

                // particles
                ParticleUtils.spawnParticles(
                        (ServerWorld) world,
                        OmegaParticles.VOID_BEAM_DUST,
                        true,
                        getX(),
                        getY(),
                        getZ(),
                        100,
                        0,
                        10,
                        0,
                        0.1f
                );

                // attack players
                world.getEntitiesByClass(LivingEntity.class, new Box(getBlockPos().add(-3, -100, -3), getBlockPos().add(3, 100, 3)), entity -> !(entity instanceof VoidMatrixEntity)).forEach(e -> {
                    double scale = (4 - Math.min(3, (float) Math.sqrt(Math.pow(e.getX() - getX(), 2) + Math.pow(e.getZ() - getZ(), 2)))) / 4;
                    e.damage(DamageSource.GENERIC, 15.0f * (float) scale);
                });
            }

            // Kill after death time is up
            if(age > DEATH_TIME) {
                discard();
            }
        }
    }

    @Override
    protected void initDataTracker() {

    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound tag) {

    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound tag) {

    }

    @Override
    public Packet<?> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }

    @Override
    public void pushAwayFrom(Entity entity) {

    }

    @Override
    public boolean shouldRender(double distance) {
        return true;
    }
}
