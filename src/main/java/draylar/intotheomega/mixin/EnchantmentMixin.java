package draylar.intotheomega.mixin;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.impl.OmegaManipulator;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enchantment.class)
public abstract class EnchantmentMixin implements OmegaManipulator {

    @Unique private boolean me_isOmega = false;
    @Unique private Enchantment me_vanilla = null;
    @Shadow protected String translationKey;

    @Shadow public abstract int getMaxLevel();

    @Inject(
            method = "getOrCreateTranslationKey",
            at = @At("HEAD")
    )
    private void modifyInitialTranslationKey(CallbackInfoReturnable<String> cir) {
        Identifier regID = Registry.ENCHANTMENT.getId((Enchantment) (Object) this);

        if(regID != null && regID.getNamespace().equals(IntoTheOmega.MODID)) {
            this.translationKey = Util.createTranslationKey("enchantment", new Identifier(regID.getPath().replace("omega_", "")));
        }
    }

    /**
     * Prefixes Omega Enchantment names with the "Ω" symbol.
     *
     * @param level
     * @param cir
     */
    @Inject(
            method = "getName",
            at = @At("RETURN"),
            cancellable = true
    )
    private void modifyName(int level, CallbackInfoReturnable<Text> cir) {
        Text ret = cir.getReturnValue();
        Identifier regID = Registry.ENCHANTMENT.getId((Enchantment) (Object) this);

        if(regID != null && regID.getNamespace().equals(IntoTheOmega.MODID)) {
            if(level == this.getMaxLevel()) {
                cir.setReturnValue(new LiteralText(IntoTheOmega.OMEGA + " ").formatted(Formatting.AQUA).append(ret));
            } else {
                cir.setReturnValue(new LiteralText(IntoTheOmega.OMEGA + " ").formatted(Formatting.RED).append(ret));
            }
        }
    }

    @Override
    public void setOmega(boolean v) {
        this.me_isOmega = v;
    }

    @Override
    public void setVanilla(Enchantment e) {
        this.me_vanilla = e;
    }

    @Override
    public boolean isOmega() {
        return me_isOmega;
    }

    @Override
    public Enchantment getVanilla() {
        return me_vanilla;
    }

    @Inject(
            method = "isAvailableForEnchantedBookOffer",
            at = @At("HEAD"),
            cancellable = true
    )
    private void isAvailableForEnchantedBookOffer(CallbackInfoReturnable<Boolean> cir) {
        if(isOmega()) {
            cir.setReturnValue(false);
        }
    }

    @Inject(
            method = "isAvailableForRandomSelection",
            at = @At("HEAD"),
            cancellable = true
    )
    private void isAvailableForRandomSelection(CallbackInfoReturnable<Boolean> cir) {
        if(isOmega()) {
            cir.setReturnValue(false);
        }
    }

    @Inject(
            method = "canAccept",
            at = @At("HEAD"),
            cancellable = true
    )
    private void canAccept(Enchantment other, CallbackInfoReturnable<Boolean> cir) {
        if(this.isOmega() && this.getVanilla().equals(other)) {
            cir.setReturnValue(false);
        }
    }
}
