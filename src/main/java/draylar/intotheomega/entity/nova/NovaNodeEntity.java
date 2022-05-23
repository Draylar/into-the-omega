package draylar.intotheomega.entity.nova;

import draylar.intotheomega.api.ParticleHelper;
import draylar.intotheomega.registry.OmegaEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

public class NovaNodeEntity extends Entity {

    private int shotsFired = 0;
    private int shotCooldown = world.random.nextInt(20) + 20;
    private OriginNovaEntity parent;

    public NovaNodeEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    public void tick() {
        super.tick();

        if(!world.isClient) {
            shotCooldown = Math.max(0, shotCooldown - 1);
            if(shotCooldown <= 0) {
                shoot();
                shotsFired++;
                if(shotsFired >= 5) {
                    discard();
                } else {
                    shotCooldown = world.random.nextInt(50) + 50;
                }
            }
        }
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if(!world.isClient) {
            discard();
            ParticleHelper.spawnDistanceParticles((ServerWorld) world, ParticleTypes.EXPLOSION, getX(), getY(), getZ(), 1, 0, 0, 0, 0);
            if(parent != null) {
                parent.childDamage(source);
            }
        }

        return super.damage(source, amount);
    }

    public void shoot() {
        PlayerEntity target = world.getClosestPlayer(this, 64);
        if(target != null) {
            NovaStrikeEntity strike = new NovaStrikeEntity(OmegaEntities.NOVA_STRIKE, world);
            strike.updatePosition(getX(), getY(), getZ());
            strike.setVelocity(target.getEyePos().subtract(getPos()).normalize().multiply(2));
            world.spawnEntity(strike);
        }
    }

    @Override
    public void initDataTracker() {

    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {

    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {

    }

    @Override
    public Packet<?> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }

    public void setParent(OriginNovaEntity origin) {
        this.parent = origin;
    }
}
