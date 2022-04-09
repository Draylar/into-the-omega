package draylar.intotheomega.item;

import draylar.intotheomega.entity.matrite.MatriteEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.function.Consumer;

public class MatrixBlasterItem extends Item {

    public MatrixBlasterItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.NEUTRAL, 0.5F, 2F / (world.random.nextFloat() * 0.4F + 0.8F));

        if (!world.isClient) {
            MatriteEntity matrite = new MatriteEntity(world);
            matrite.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.5F, 1.0F);
            matrite.updatePosition(user.getX(), user.getEyeY() - .5f, user.getZ());
            world.spawnEntity(matrite);
        }

        user.incrementStat(Stats.USED.getOrCreateStat(this));
        user.getItemCooldownManager().set(this, 20 * 2);

        if (!user.getAbilities().creativeMode) {
            itemStack.damage(1, user, p -> user.sendToolBreakStatus(user.getActiveHand()));
        }

        return TypedActionResult.success(itemStack, world.isClient());
    }
}
