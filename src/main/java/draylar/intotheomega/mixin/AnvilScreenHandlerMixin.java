package draylar.intotheomega.mixin;

import draylar.intotheomega.impl.OmegaManipulator;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.ForgingScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Iterator;
import java.util.Map;

@Mixin(AnvilScreenHandler.class)
public abstract class AnvilScreenHandlerMixin extends ForgingScreenHandler {

    private AnvilScreenHandlerMixin(ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(type, syncId, playerInventory, context);
    }

//    @Inject(
//            method = "updateResult",
//            at = @At("HEAD"),
//            cancellable = true
//    )
//    private void onUpdateResults(CallbackInfo ci) {
//        ItemStack leftInput = this.input.getStack(0);
//        ItemStack rightInput = this.input.getStack(1);
//
//        boolean leftHasOmega = false;
//        boolean rightHasOmega = false;
//
//        for (Map.Entry<Enchantment, Integer> entry : EnchantmentHelper.get(leftInput).entrySet()) {
//            Enchantment enchantment = entry.getKey();
//            if (((OmegaManipulator) enchantment).isOmega()) {
//                leftHasOmega = true;
//                break;
//            }
//        }
//
//        for (Map.Entry<Enchantment, Integer> entry : EnchantmentHelper.get(rightInput).entrySet()) {
//            Enchantment enchantment = entry.getKey();
//            if (((OmegaManipulator) enchantment).isOmega()) {
//                rightHasOmega = true;
//                break;
//            }
//        }
//
//        if(leftHasOmega && rightHasOmega) {
//            ci.cancel();
//        }
//    }

    private Enchantment omega_enchantment = null;
    private int omega_u = 0;
    private int omega_t = 0;

    @Inject(
            method = "updateResult",
            at = @At(value = "INVOKE", target = "Ljava/util/Map;get(Ljava/lang/Object;)Ljava/lang/Object;", shift = At.Shift.AFTER),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void storeU(
            CallbackInfo ci,
            ItemStack itemStack,
            int i,
            int j,
            int k,
            ItemStack itemStack2,
            ItemStack itemStack3,
            Map map,
            boolean bl,
            Map map2,
            boolean bl2,
            boolean bl3,
            Iterator var12,
            Enchantment enchantment,
            int t) {
        this.omega_enchantment = enchantment;
        this.omega_t = (Integer) map.getOrDefault(enchantment, 0);
        this.omega_u = (Integer) map2.get(enchantment);
    }

    @ModifyVariable(
            method = "updateResult",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/Enchantment;isAcceptableItem(Lnet/minecraft/item/ItemStack;)Z", shift = At.Shift.AFTER),
            index = 15
    )
    private int yeet(int u) {
        if(((OmegaManipulator) omega_enchantment).isOmega()) {
            return Math.max(omega_u, omega_t);
        }

        return u;
    }
}
