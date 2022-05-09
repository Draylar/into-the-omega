package draylar.intotheomega.item;

import draylar.intotheomega.entity.HomingStarlitProjectileEntity;
import draylar.intotheomega.registry.OmegaEntities;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class StarlightPassionItem extends Item {

    public StarlightPassionItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        user.setCurrentHand(hand);
        return super.use(world, user, hand);
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return Integer.MAX_VALUE;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);

        tooltip.add(LiteralText.EMPTY);
        tooltip.add(new LiteralText("Fires homing §dstarlight missiles").formatted(Formatting.GRAY));
        tooltip.add(new LiteralText("which explode on impact.").formatted(Formatting.GRAY));
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BLOCK;
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        super.usageTick(world, user, stack, remainingUseTicks);

        if(!world.isClient && remainingUseTicks % 10 == 0) {
            HomingStarlitProjectileEntity projectile = new HomingStarlitProjectileEntity(OmegaEntities.HOMING_STARLIT_PROJECTILE, world);
            projectile.updatePosition(user.getX(), user.getY() + 0.5, user.getZ());
            Vec3d rot = user.getRotationVector();
            projectile.setVelocity(rot);
            projectile.setVelocity(new Vec3d(world.random.nextFloat(5) - 2.5, 1, world.random.nextFloat(5) - 2.5));
            world.spawnEntity(projectile);
        }
    }
}
