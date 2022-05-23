package draylar.intotheomega.mixin;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.impl.OmegaManipulator;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
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
    @Shadow public abstract String getTranslationKey();

    @Inject(
            method = "getOrCreateTranslationKey",
            at = @At("HEAD")
    )
    private void modifyInitialTranslationKey(CallbackInfoReturnable<String> cir) {
        if(isOmega()) {
            Identifier regID = Registry.ENCHANTMENT.getId((Enchantment) (Object) this);
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
    @Environment(EnvType.CLIENT)
    private void modifyName(int level, CallbackInfoReturnable<Text> cir) {
        Text ret = cir.getReturnValue();
        if(isOmega()) {
            MutableText append;

            if(level == this.getMaxLevel()) {
                append = new LiteralText(IntoTheOmega.OMEGA + " ").formatted(Formatting.AQUA).append(ret);
            } else {
                append = new LiteralText(IntoTheOmega.OMEGA + " ").formatted(Formatting.RED).append(ret);
            }

            if(FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
                if(Screen.hasControlDown()) {
                    append.append(new LiteralText(" (").append(new TranslatableText(this.getTranslationKey())).append(" ").append(String.valueOf(level + this.getMaxLevel())).append(")"));
                }
            }

            cir.setReturnValue(append);
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
