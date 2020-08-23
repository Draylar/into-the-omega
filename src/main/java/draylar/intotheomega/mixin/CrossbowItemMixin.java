package draylar.intotheomega.mixin;

import draylar.intotheomega.registry.OmegaEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.List;
import java.util.Random;

@Mixin(CrossbowItem.class)
public abstract class CrossbowItemMixin {

    @Shadow
    protected static List<ItemStack> getProjectiles(ItemStack crossbow) {
        return null;
    }

    @Shadow
    protected static float[] getSoundPitches(Random random) {
        return new float[0];
    }

    @Shadow
    protected static void shoot(World world, LivingEntity shooter, Hand hand, ItemStack crossbow, ItemStack projectile, float soundPitch, boolean creative, float speed, float divergence, float simulated) {
    }

    @Shadow
    protected static void postShoot(World world, LivingEntity entity, ItemStack stack) {
    }

    @ModifyVariable(
            method = "loadProjectiles",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getArrowType(Lnet/minecraft/item/ItemStack;)Lnet/minecraft/item/ItemStack;"),
            index = 3
    )
    private static int setupOmegaMultishot(int amount, LivingEntity shooter, ItemStack projectile) {
        int i = EnchantmentHelper.getLevel(OmegaEnchantments.MULTISHOT, projectile);
        if(i > 0) {
            return 5;
        }

        return amount;
    }

    /**
     * I would say "watchu doin mojang," but I'm doing much worse
     *
     * @author Draylar
     * @param world
     * @param entity
     * @param hand
     * @param stack
     * @param speed
     * @param divergence
     */
    @Overwrite
    public static void shootAll(World world, LivingEntity entity, Hand hand, ItemStack stack, float speed, float divergence) {
        List<ItemStack> list = getProjectiles(stack);
        float[] fs = getSoundPitches(entity.getRandom());
        boolean bl = entity instanceof PlayerEntity && ((PlayerEntity)entity).abilities.creativeMode;

        // 0 -> 0
        // 1 -> -10
        // 2 -> 10
        // 3 -> -20
        // 4 -> 20

        shoot(world, entity, hand, stack, list.get(0), fs[0], bl, speed, divergence, 0.0F);
        for(int i = 1; i < list.size(); ++i) {
            ItemStack itemStack = list.get(i);

            if (!itemStack.isEmpty()) {
                if(i % 2 == 0) {
                    shoot(world, entity, hand, stack, itemStack, fs[i % 3], bl, speed, divergence, i / 2f * 10);
                } else {
                    shoot(world, entity, hand, stack, itemStack, fs[i % 3], bl, speed, divergence, (i + 1) / 2f * -10);
                }
            }
        }

        postShoot(world, entity, stack);
    }
}
