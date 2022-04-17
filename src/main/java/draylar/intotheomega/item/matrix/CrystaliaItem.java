package draylar.intotheomega.item.matrix;

import draylar.intotheomega.impl.AttackingItem;
import draylar.intotheomega.registry.OmegaParticles;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrystaliaItem extends Item implements AttackingItem {

    public CrystaliaItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        // Set current hand
        ItemStack itemStack = user.getStackInHand(hand);
        user.setCurrentHand(hand);
        return TypedActionResult.consume(itemStack);
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 20 * 3;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        float mod = 1 - (float) remainingUseTicks / getMaxUseTime(stack);

        // Particles
        if(!world.isClient) {
            HitResult trace = user.raycast(16, 0, false);
            ((ServerWorld) world).spawnParticles(OmegaParticles.OMEGA_PARTICLE, trace.getPos().getX(), trace.getPos().getY(), trace.getPos().getZ(), (int) (5 * mod), .5, .5, .5, 1);
            ((ServerWorld) world).spawnParticles(ParticleTypes.ENCHANT, trace.getPos().getX(), trace.getPos().getY(), trace.getPos().getZ(), (int) (25 * mod), .5, .5, .5, 1);
            world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.PLAYERS, mod, 1.0F / (world.random.nextFloat() * 0.4F + 1.2F));
        }

        super.usageTick(world, user, stack, remainingUseTicks);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if(!world.isClient && user instanceof PlayerEntity) {
            // Find location to launch explosives at
            HitResult trace = user.raycast(16, 0, false);

            // Generate explovies
            world.createExplosion(user, trace.getPos().getX(), trace.getPos().getY(), trace.getPos().getZ(), 4, Explosion.DestructionType.NONE);

            // Set cool-down to 7 seconds
            ((PlayerEntity) user).getItemCooldownManager().set(this, 20 * 7);
        }


        return super.finishUsing(stack, world, user);
    }

    @Override
    public void attack(PlayerEntity player, World world, ItemStack stack) {
        if(player.getItemCooldownManager().isCoolingDown(this)) {
            return;
        }

        if(!world.isClient) {
            ServerWorld serverWorld = (ServerWorld) world;
            HitResult res = player.raycast(25, 0, false);

            double mult = .25;

            // Calculate positioning data
            Vec3d origin = new Vec3d(player.getX(), player.getEyeY() - .5, player.getZ());
            Vec3d target = res.getPos();
            double diff = Math.sqrt(origin.squaredDistanceTo(target)); // distance in blocks from origin to target
            Vec3d per = target.subtract(origin).multiply(1 / diff).multiply(mult); // position to increment per each block
            Vec3d cur = new Vec3d(origin.getX(), origin.getY(), origin.getZ()); // current position

            // only hit each entity once
            List<UUID> hitEntities = new ArrayList<>();

            for(double i = 0; i < diff; i += mult) {
                // Spawn particles along path
                serverWorld.spawnParticles(ParticleTypes.ENCHANT, cur.getX(), cur.getY(), cur.getZ(), 5, 0, 0, 0, .1);
                cur = cur.add(per);

                // Damage entities
                world.getEntitiesByClass(HostileEntity.class, new Box(cur.add(-.5, -.5, -.5), cur.add(.5, .5, .5)), entity -> !hitEntities.contains(entity.getUuid())).forEach(entity -> {
                    entity.damage(DamageSource.player(player), 5); // todo: this is true damage
                    // todo: power should impact damage ^^
                    serverWorld.spawnParticles(ParticleTypes.PORTAL, entity.getX(), entity.getY() + .5, entity.getZ(), 25, 0, 0, 0, .1);
                    hitEntities.add(entity.getUuid());
                });
            }

            player.getItemCooldownManager().set(this, 20 * 2); // 2 second cd

            // Play sound
            world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.PLAYERS, .5f, 1.0F / (world.random.nextFloat() * 0.4F));
        }
    }
}
