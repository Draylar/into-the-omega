package draylar.intotheomega.item;

import draylar.intotheomega.entity.MatrixBombEntity;
import draylar.intotheomega.registry.OmegaEntities;
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

public class MatrixBombItem extends Item {

    public MatrixBombItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.BLOCK_GLASS_HIT, SoundCategory.NEUTRAL, 0.5F, 2 / (RANDOM.nextFloat() * 0.4F + 0.8F));

        if (!world.isClient) {
            MatrixBombEntity proj = new MatrixBombEntity(OmegaEntities.MATRIX_BOMB, world);
            proj.setPos(user.getX(), user.getEyeY() - .5, user.getZ());
            proj.setOwner(user);
            proj.setProperties(user, user.pitch, user.yaw, 0, 1, 1);
            world.spawnEntity(proj);
        }

        user.getItemCooldownManager().set(this, 20 * 3);

        user.incrementStat(Stats.USED.getOrCreateStat(this));
        if (!user.abilities.creativeMode) {
            itemStack.decrement(1);
        }

        return TypedActionResult.success(itemStack, world.isClient());
    }
}
