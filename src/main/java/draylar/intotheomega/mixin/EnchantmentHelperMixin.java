package draylar.intotheomega.mixin;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.impl.OmegaManipulator;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Optional;

@Mixin(EnchantmentHelper.class)
public abstract class EnchantmentHelperMixin {

    /**
     * Modifies {@link EnchantmentHelper#getLevel(Enchantment, ItemStack)} to return the highest value between either
     * the passed in enchantment, or the Omega variant (if applicable).
     *
     * @param enchantment  enchantment to check level of
     * @param stack        stack to check for enchantments on
     * @param cir          mixin callback info
     */
    @Inject(
            method = "getLevel",
            at = @At("RETURN"),
            cancellable = true
    )
    private static void modifyLevel(Enchantment enchantment, ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        int vanillaLevel = cir.getReturnValue();

        // check if current enchantment instance is not omega
        if(!((OmegaManipulator) enchantment).isOmega()) {
            // get vanilla registry ID
            Identifier existingID = Registry.ENCHANTMENT.getId(enchantment);

            // verify minecraft namespace and convert to omega
            if(existingID != null && existingID.getNamespace().equals("minecraft")) {
                Identifier omegaID = IntoTheOmega.id(String.format("omega_%s", existingID.getPath()));

                // verify omega enchantment exists
                if(((SimpleRegistryAccessor) Registry.ENCHANTMENT).getIdToEntry().containsKey(omegaID)) {
                    Enchantment omegaEnchantment = Registry.ENCHANTMENT.get(omegaID);
                    cir.setReturnValue(Math.max(vanillaLevel, EnchantmentHelper.getLevel(omegaEnchantment, stack)));
                }
            }
        }

        // make omega enchantments, if valid, return their level + the max level of the prev tool
        else {
            Enchantment vanilla = ((OmegaManipulator) enchantment).getVanilla();

            // make sure the omega enchantment exists in the first place (solves issue where all tools have enchantments)
            if(vanillaLevel > 0) {
                cir.setReturnValue(vanillaLevel + vanilla.getMaxLevel());
            }
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

            // we can assume it is okay to add the max level to the omega enchantment level, because this is only called for enchantments on the tag
            if(((OmegaManipulator) cached).isOmega()) {
                return tag.getInt("lvl") + ((OmegaManipulator) cached).getVanilla().getMaxLevel();
            }
        }

        return tag.getInt("lvl");
    }
}
