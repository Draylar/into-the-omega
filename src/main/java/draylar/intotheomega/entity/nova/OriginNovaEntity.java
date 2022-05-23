package draylar.intotheomega.entity.nova;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.IntoTheOmegaClient;
import draylar.intotheomega.api.RandomUtils;
import draylar.intotheomega.api.skybox.SkyboxLayer;
import draylar.intotheomega.api.skybox.SkyboxManager;
import draylar.intotheomega.api.skybox.SkyboxTracking;
import draylar.intotheomega.registry.OmegaEntities;
import draylar.intotheomega.registry.OmegaParticles;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

public class OriginNovaEntity extends PathAwareEntity implements SkyboxTracking {

    private static final SkyboxLayer BOSS_SKYBOX = new SkyboxLayer(IntoTheOmega.id("textures/skybox/origin_nova/"));
    private final ServerBossBar bossBar = (ServerBossBar) new ServerBossBar(this.getDisplayName(), BossBar.Color.PURPLE, BossBar.Style.PROGRESS).setDarkenSky(true);

    public OriginNovaEntity(EntityType<? extends PathAwareEntity> type, World world) {
        super(type, world);
    }

    @Override
    public void tick() {
        super.tick();

        // Particle FX
        if(world.isClient) {
            if(age % 5 == 0) {
                world.addParticle(OmegaParticles.ORIGIN_NOVA, true, getX(), getY(), getZ(), 0, 0, 0);
            }
        }

        // Server-side AI logic
        if(!world.isClient) {
            if(age % 10 == 0) {
                Box nearby = new Box(getBlockPos()).expand(250);
                int nearbyGhouls = world.getEntitiesByType(TypeFilter.instanceOf(NovaGhoulEntity.class), nearby, Entity::isAlive).size();
                int nearbyPortals = world.getEntitiesByType(TypeFilter.instanceOf(NovaNodeEntity.class), nearby, Entity::isAlive).size();

                if(nearbyPortals <= 15) {
                    NovaNodeEntity portal = new NovaNodeEntity(OmegaEntities.NOVA_NODE, world);
                    portal.setParent(this);
                    portal.updatePosition(getX() + RandomUtils.range(world.random, 25.0f), getY() + world.random.nextFloat(15), getZ() + RandomUtils.range(world.random, 25.0f));
                    world.spawnEntity(portal);
                }
            }
        }
    }

    @Override
    public void onStartedTrackingBy(ServerPlayerEntity player) {
        super.onStartedTrackingBy(player);
        bossBar.addPlayer(player);
    }

    @Override
    public void onStoppedTrackingBy(ServerPlayerEntity player) {
        super.onStoppedTrackingBy(player);
        bossBar.removePlayer(player);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void startTracking() {
        IntoTheOmegaClient.getSkyboxManager().setLayer(SkyboxManager.BOSS_LAYER, BOSS_SKYBOX);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void stopTracking() {
        IntoTheOmegaClient.getSkyboxManager().discardLayer(SkyboxManager.BOSS_LAYER, BOSS_SKYBOX);
    }

    public void childDamage(DamageSource source) {
        damage(DamageSource.GENERIC, 10.0f);
    }

    @Override
    public boolean cannotDespawn() {
        return true;
    }

    @Override
    public boolean hasNoGravity() {
        return true;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if(!source.isOutOfWorld()) {
            return false;
        }

        return super.damage(source, amount);
    }
}
