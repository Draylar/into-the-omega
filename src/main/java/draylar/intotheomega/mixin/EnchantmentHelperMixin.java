package draylar.intotheomega.mixin;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.impl.OmegaManipulator;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Optional;

@Mixin(EnchantmentHelper.class)
public abstract class EnchantmentHelperMixin {

    @Shadow
    public static int getEquipmentLevel(Enchantment enchantment, LivingEntity entity) {
        return 0;
    }

    @Inject(
            method = "getLevel",
            at = @At("RETURN"),
            cancellable = true
    )
    private static void modifyLevel(Enchantment enchantment, ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        if(((OmegaManipulator) enchantment).isOmega()) {
            Enchantment original = ((OmegaManipulator) enchantment).getVanilla();
            int ret = cir.getReturnValue();
            cir.setReturnValue(ret + original.getMaxLevel());
        }
    }

    @Unique private static Optional<Enchantment> me_cachedEnchantment;

    @Inject(
            method = "forEachEnchantment(Lnet/minecraft/enchantment/EnchantmentHelper$Consumer;Lnet/minecraft/item/ItemStack;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/ListTag;getCompound(I)Lnet/minecraft/nbt/CompoundTag;", ordinal = 1),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private static void storeData(EnchantmentHelper.Consumer action, ItemStack stack, CallbackInfo ci, ListTag listTag, int i, String id) {
        me_cachedEnchantment = Registry.ENCHANTMENT.getOrEmpty(new Identifier(id));
    }

    @Redirect(
            method = "forEachEnchantment(Lnet/minecraft/enchantment/EnchantmentHelper$Consumer;Lnet/minecraft/item/ItemStack;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/CompoundTag;getInt(Ljava/lang/String;)I")
    )
    private static int modifyLevel(CompoundTag tag, String key) {
        if(me_cachedEnchantment != null && me_cachedEnchantment.isPresent()) {
            Enchantment cached = me_cachedEnchantment.get();
            if(((OmegaManipulator) cached).isOmega()) {
                return tag.getInt("lvl") + ((OmegaManipulator) cached).getVanilla().getMaxLevel();
            }
        }

        return tag.getInt("lvl");
    }

    @Inject(
            method = "getEquipmentLevel",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void getEquipmentLevel(Enchantment enchantment, LivingEntity entity, CallbackInfoReturnable<Integer> cir) {
        if(!((OmegaManipulator) enchantment).isOmega()) {
            Identifier existingID = Registry.ENCHANTMENT.getId(enchantment);

            // check minecraft namespace and convert to omega
            if(existingID != null && existingID.getNamespace().equals("minecraft")) {
                Identifier omegaID = IntoTheOmega.id(String.format("omega_%s", existingID.getPath()));

                // verify omega enchantment exists
                if(Registry.ENCHANTMENT.containsId(omegaID)) {
                    Enchantment omegaEnchantment = Registry.ENCHANTMENT.get(omegaID);
                    cir.setReturnValue(getEquipmentLevel(omegaEnchantment, entity));
                }
            }
        }
    }
}
