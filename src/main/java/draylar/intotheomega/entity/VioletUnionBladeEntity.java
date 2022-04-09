package draylar.intotheomega.entity;

import draylar.intotheomega.registry.OmegaEntities;
import draylar.intotheomega.registry.OmegaParticles;
import draylar.intotheomega.util.ParticleUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class VioletUnionBladeEntity extends ProjectileEntity {

    public VioletUnionBladeEntity(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Environment(EnvType.CLIENT)
    public VioletUnionBladeEntity(World world, double x, double y, double z) {
        super(OmegaEntities.VIOLET_UNION_BLADE, world);
        this.updatePosition(x, y, z);
        this.updateTrackedPosition(x, y, z);
    }

    @Override
    public void tick() {
        super.tick();
        HitResult hitResult = ProjectileUtil.getCollision(this, this::collidesWith);
        boolean bl = false;

        // If the Violet Union Blade collides with anything, remove it and spawn particles.
        if(hitResult.getType() != HitResult.Type.MISS) {
            discard();

            // explosion
            if(!world.isClient) {
                ParticleUtils.spawnParticles((ServerWorld) world, OmegaParticles.VARIANT_FUSION, true, getX(), getY(), getZ(), 250, .1, .1, .1, .25);

                world.getEntitiesByClass(LivingEntity.class, new Box(getBlockPos().add(-3, -3, -3), getBlockPos().add(3, 3, 3)), entity -> true).forEach(entity -> {
                    entity.damage(DamageSource.GENERIC, 20);
                });

                world.playSound(null, getBlockPos(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, .5f, 3f);
            }
        }

        this.checkBlockCollision();
        Vec3d vec3d = this.getVelocity();
        double d = this.getX() + vec3d.x;
        double e = this.getY() + vec3d.y;
        double f = this.getZ() + vec3d.z;
//        this.method_26962();


        this.updatePosition(d, e, f);
    }

    @Override
    public void initDataTracker() {

    }

    @Override
    public Packet<?> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }
}
