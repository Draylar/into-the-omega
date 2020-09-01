package draylar.intotheomega.item;

import draylar.intotheomega.registry.OmegaParticles;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class OmegaSpearItem extends Item {

    public OmegaSpearItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        user.setCurrentHand(hand);
        return TypedActionResult.consume(user.getStackInHand(hand));
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 72000;
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if(!world.isClient) {
            int timeUsed = this.getMaxUseTime(stack) - remainingUseTicks;

            ServerWorld serverWorld = (ServerWorld) world;
            HitResult res = user.rayTrace(100, 0, false);

            double mult = .25;

            Vec3d origin = user.getPos();
            Vec3d target = res.getPos();
            double diff = Math.sqrt(origin.squaredDistanceTo(target)); // distance in blocks from origin to target
            Vec3d per = target.subtract(origin).multiply(1 / diff).multiply(mult); // position to increment per each block
            Vec3d cur = new Vec3d(origin.getX(), origin.getY(), origin.getZ()); // current position

            world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.PLAYERS, 1.0F, 1.0F / (RANDOM.nextFloat() * 0.4F + 1.2F));
            world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_TRIDENT_THROW, SoundCategory.PLAYERS, 1.0F, 1.0F / (RANDOM.nextFloat() * 0.4F + 1.2F));

            for(double i = 0; i < diff; i += mult) {
                serverWorld.spawnParticles(OmegaParticles.OMEGA_PARTICLE, cur.getX() + Math.sin(i), cur.getY() + Math.cos(i), cur.getZ() +  Math.cos(i), 1, 0, 0, 0, 0);
                cur = cur.add(per);
            }

            if(res instanceof EntityHitResult) {
                ((EntityHitResult) res).getEntity().damage(DamageSource.MAGIC, 5);
            }
        }

        super.onStoppedUsing(stack, world, user, remainingUseTicks);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.SPEAR;
    }
}
