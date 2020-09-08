package draylar.intotheomega.item;

import draylar.intotheomega.impl.AttackingItem;
import draylar.intotheomega.registry.OmegaParticles;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
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
        System.out.println(mod);

        // Particles
        if(!world.isClient) {
            HitResult trace = user.rayTrace(16, 0, false);
            ((ServerWorld) world).spawnParticles(OmegaParticles.OMEGA_PARTICLE, trace.getPos().getX(), trace.getPos().getY(), trace.getPos().getZ(), (int) (5 * mod), .5, .5, .5, 1);
            ((ServerWorld) world).spawnParticles(ParticleTypes.ENCHANT, trace.getPos().getX(), trace.getPos().getY(), trace.getPos().getZ(), (int) (25 * mod), .5, .5, .5, 1);
            world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.PLAYERS, mod, 1.0F / (RANDOM.nextFloat() * 0.4F + 1.2F));
        }

        super.usageTick(world, user, stack, remainingUseTicks);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if(!world.isClient && user instanceof PlayerEntity) {
            // Find location to launch explosives at
            HitResult trace = user.rayTrace(16, 0, false);

            // Generate explovies
            world.createExplosion(user, trace.getPos().getX(), trace.getPos().getY(), trace.getPos().getZ(), 4, Explosion.DestructionType.NONE);

            // Set cool-down to 10 seconds
            ((PlayerEntity) user).getItemCooldownManager().set(this, 20 * 10);
        }


        return super.finishUsing(stack, world, user);
    }

    @Override
    public void attack(PlayerEntity player, World world, ItemStack stack) {
        if(!world.isClient) {
            ServerWorld serverWorld = (ServerWorld) world;
            HitResult res = player.rayTrace(50, 0, false);

            double mult = .25;

            Vec3d origin = player.getPos();
            Vec3d target = res.getPos();
            double diff = Math.sqrt(origin.squaredDistanceTo(target)); // distance in blocks from origin to target
            Vec3d per = target.subtract(origin).multiply(1 / diff).multiply(mult); // position to increment per each block
            Vec3d cur = new Vec3d(origin.getX(), origin.getY(), origin.getZ()); // current position

            for(double i = 0; i < diff; i += mult) {
//                serverWorld.spawnParticles(OmegaParticles.OMEGA_PARTICLE, cur.getX(), cur.getY(), cur.getZ(), 1, 0, 0, 0, 0);
                serverWorld.spawnParticles(ParticleTypes.ENCHANT, cur.getX(), cur.getY() + 1, cur.getZ(), 1, 0, 0, 0, 0);
                cur = cur.add(per);
            }

            if(res instanceof EntityHitResult) {
                ((EntityHitResult) res).getEntity().damage(DamageSource.MAGIC, 5);
            }
        }
    }
}
