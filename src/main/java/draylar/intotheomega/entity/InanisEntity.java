package draylar.intotheomega.entity;

import com.google.common.collect.Lists;
import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.registry.OmegaEntities;
import draylar.intotheomega.registry.OmegaParticles;
import io.netty.buffer.Unpooled;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.s2c.play.GameStateChangeS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

import java.util.Arrays;

public class InanisEntity extends PersistentProjectileEntity {

    public static final Identifier ENTITY_ID = IntoTheOmega.id("inanis");
    protected ItemStack source;
    private double damage = 8.0;

    public InanisEntity(EntityType<? extends InanisEntity> type, World world) {
        super(type, world);
    }

    public InanisEntity(EntityType<? extends InanisEntity> type, World world, LivingEntity owner, ItemStack source) {
        super(type, owner, world);
        this.source = source;
        this.pickupType = PickupPermission.DISALLOWED;
    }

    @Environment(EnvType.CLIENT)
    public InanisEntity(EntityType<? extends InanisEntity> type, World world, double x, double y, double z) {
        super(type, world);
        this.updatePosition(x, y, z);
        this.updateTrackedPosition(x, y, z);
    }

    @Override
    public void tick() {
        super.tick();

        if(!isOnGround() && !inGround) {
            world.playSound(null, getBlockPos(), SoundEvents.ENTITY_ENDER_DRAGON_AMBIENT, SoundCategory.PLAYERS, .5f, -.5f);
        }

        // undo .99 velocity modifier at end of super tick
        setVelocity(getVelocity().multiply(1.01));

        // kill if too old
        if(age > 20 * 15) {
            this.remove();
        }
    }

    @Override
    protected void onBlockHit(BlockHitResult result) {
        super.onBlockHit(result);

        if(world.isClient) {
            for (int i = 0; i < 50; i++) {
                world.addParticle(OmegaParticles.DARK, true, result.getPos().getX() + rangeRandom(world, -1.5, 1.5), result.getPos().getY() + rangeRandom(world, -1.5, 1.5), result.getPos().getZ() + rangeRandom(world, -1.5, 1.5), 0, 0, 0);
                world.addParticle(OmegaParticles.OMEGA_PARTICLE, true, result.getPos().getX() + rangeRandom(world, -1.5, 1.5), result.getPos().getY() + rangeRandom(world, -1.5, 1.5), result.getPos().getZ() + rangeRandom(world, -1.5, 1.5), 0, 0, 0);
                world.addParticle(ParticleTypes.DRAGON_BREATH, true, result.getPos().getX() + rangeRandom(world, -1.5, 1.5), result.getPos().getY() + rangeRandom(world, -1.5, 1.5), result.getPos().getZ() + rangeRandom(world, -1.5, 1.5), 0, 0, 0);
                world.addParticle(ParticleTypes.EXPLOSION, true, result.getPos().getX() + rangeRandom(world, -1.5, 1.5), result.getPos().getY() + rangeRandom(world, -1.5, 1.5), result.getPos().getZ() + rangeRandom(world, -1.5, 1.5), 0, 0, 0);
            }
        }

        this.remove();
    }

    public double rangeRandom(World world, double min, double max) {
        return min + world.random.nextDouble() * (max - min);
    }

    @Override
    public void onEntityHit(EntityHitResult result) {
        super.onEntityHit(result);
        Entity hitEntity = result.getEntity();

        // Calculate damage based on velocity
        float velocityLength = (float) this.getVelocity().length();
        int damage = MathHelper.ceil(MathHelper.clamp((double) velocityLength * this.damage, 0.0D, 2.147483647E9D));

        // Update piercing information if this Inanis entity has the enchantment
        if (this.getPierceLevel() > 0) {
            if (this.piercedEntities == null) {
                this.piercedEntities = new IntOpenHashSet(5);
            }

            if (this.piercingKilledEntities == null) {
                this.piercingKilledEntities = Lists.newArrayListWithCapacity(5);
            }

            // Pierced maximum amount of enemies, remove Inanis now
            if (this.piercedEntities.size() >= this.getPierceLevel() + 1) {
                this.remove();
                return;
            }

            // Record the entity that was recently pierced for future counting
            this.piercedEntities.add(hitEntity.getEntityId());
        }

        // Increase damage if the attack was a critical hit
        if (this.isCritical()) {
            long l = this.random.nextInt(damage / 2 + 2);
            damage = (int) Math.min(l + (long) damage, 2147483647L);
        }

        // Setup damage source based on Inanis owner
        Entity owner = this.getOwner();
        DamageSource damageSource;
        if (owner == null) {
            damageSource = DamageSource.arrow(this, this);
        } else {
            damageSource = DamageSource.arrow(this, owner);
            if (owner instanceof LivingEntity) {
                ((LivingEntity) owner).onAttacking(hitEntity);
            }
        }

        boolean hitEnderman = hitEntity.getType() == EntityType.ENDERMAN;
        int fireTicks = hitEntity.getFireTicks();

        // Set any non-Enderman entity on fire if this projectile was also on fire
        if (this.isOnFire() && !hitEnderman) {
            hitEntity.setOnFireFor(5);
        }

        if (hitEntity.damage(damageSource, (float) damage)) {
            if (hitEnderman) {
                return;
            }

            if (hitEntity instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity)hitEntity;
                if (!this.world.isClient && this.getPierceLevel() <= 0) {
                    livingEntity.setStuckArrowCount(livingEntity.getStuckArrowCount() + 1);
                }

                if (!this.world.isClient && owner instanceof LivingEntity) {
                    EnchantmentHelper.onUserDamaged(livingEntity, owner);
                    EnchantmentHelper.onTargetDamaged((LivingEntity)owner, livingEntity);
                }

                this.onHit(livingEntity);
                if (owner != null && livingEntity != owner && livingEntity instanceof PlayerEntity && owner instanceof ServerPlayerEntity && !this.isSilent()) {
                    ((ServerPlayerEntity)owner).networkHandler.sendPacket(new GameStateChangeS2CPacket(GameStateChangeS2CPacket.PROJECTILE_HIT_PLAYER, 0.0F));
                }

                if (!hitEntity.isAlive() && this.piercingKilledEntities != null) {
                    this.piercingKilledEntities.add(livingEntity);
                }

                // todo: critereon for killing with inanis?
            }

            if (this.getPierceLevel() <= 0) {
                this.remove();
            }
        } else {
            hitEntity.setFireTicks(fireTicks);
            this.setVelocity(this.getVelocity().multiply(-0.1D));
            this.yaw += 180.0F;
            this.prevYaw += 180.0F;
            if (!this.world.isClient && this.getVelocity().lengthSquared() < 1.0E-7D) {
                if (this.pickupType == PersistentProjectileEntity.PickupPermission.ALLOWED) {
                    this.dropStack(this.asItemStack(), 0.1F);
                }

                this.remove();
            }
        }
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
    public ItemStack asItemStack() {
        return source;
    }

    @Override
    public boolean hasNoGravity() {
        return true;
    }
}
