package draylar.intotheomega.item.weapon.bow;

import draylar.intotheomega.api.RaycastUtils;
import draylar.intotheomega.entity.LevitationProjectileEntity;
import draylar.intotheomega.registry.OmegaEntities;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class LevitationBlasterItem extends Item {

    public LevitationBlasterItem(Settings settings) {
        super(settings);
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return Integer.MAX_VALUE;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        user.setCurrentHand(hand);
        return super.use(world, user, hand);
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        super.usageTick(world, user, stack, remainingUseTicks);

        int ticksElapsed = getMaxUseTime(stack) - remainingUseTicks;
        if(world.isClient) {
            if(ticksElapsed == 20) {
                user.playSound(SoundEvents.ITEM_CROSSBOW_LOADING_END, 5.0f, 1.0f);
            }
        }
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        int ticksElapsed = getMaxUseTime(stack) - remainingUseTicks;

        // When the bow is used for more than 2 seconds, it will shoot out a ray of particles, which hurt and apply levitation to enemies.
        if(ticksElapsed >= 20) {
            if(!world.isClient) {
                LevitationProjectileEntity projectile = new LevitationProjectileEntity(OmegaEntities.LEVITATION_PROJECTILE, world);
                projectile.updatePosition(user.getX(), user.getEyeY() - 0.2, user.getZ());
                projectile.setOwner(user);
                projectile.setVelocity(user.getRotationVector().multiply(3));
                world.spawnEntity(projectile);
            } else {
                user.playSound(SoundEvents.ENTITY_SHULKER_SHOOT, 2.0f, 1.0f);
            }

            // Set cooldown on Levitation Blaster after a full charge attack
            if(user instanceof PlayerEntity player) {
                player.getItemCooldownManager().set(this, 20);
            }

            return;
        }

        super.onStoppedUsing(stack, world, user, remainingUseTicks);
    }
}
