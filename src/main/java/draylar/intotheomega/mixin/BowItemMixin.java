package draylar.intotheomega.mixin;

import draylar.intotheomega.impl.ProjectileEntityManipulator;
import draylar.intotheomega.registry.OmegaEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(BowItem.class)
public class BowItemMixin {

    @Inject(
            method = "onStoppedUsing",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/projectile/PersistentProjectileEntity;setOnFireFor(I)V"),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void storeOmegaFlame(ItemStack stack, World world, LivingEntity user, int remainingUseTicks, CallbackInfo ci, PlayerEntity playerEntity, boolean bl, ItemStack itemStack, int i, float f, boolean bl2, ArrowItem arrowItem, PersistentProjectileEntity persistentProjectileEntity) {
        if (EnchantmentHelper.getLevel(OmegaEnchantments.FLAME, stack) > 0) {
            ((ProjectileEntityManipulator) persistentProjectileEntity).setOmegaFlame(true);
        } else {
            ((ProjectileEntityManipulator) persistentProjectileEntity).setOmegaFlame(false);
        }
    }
}
