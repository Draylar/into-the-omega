package draylar.intotheomega.mixin;

import draylar.intotheomega.IntoTheOmega;
import net.minecraft.client.resource.SplashTextResourceSupplier;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Arrays;
import java.util.List;

@Mixin(SplashTextResourceSupplier.class)
public class SplashTextMixin {

    @Unique
    private static final List<String> SPLASH_TEXT = Arrays.asList(
            "§dTranscendent!",
            "§dBeyond all limits!",
            "§dInto the Omega!",
            "§dDiscover The End!",
            "§dTranscend your limits!",
            "§dSparkling in the twilight!",
            "§dBlessed by the galaxy!",
            "§dA mysterious enigma!",
            "§dThe Omega calls for you!",
            "§dSupreme omega!",
            "§dTo The End!"
    );

    @Inject(method = "prepare(Lnet/minecraft/resource/ResourceManager;Lnet/minecraft/util/profiler/Profiler;)Ljava/util/List;", at = @At("HEAD"), cancellable = true)
    private void returnCustomSplashText(ResourceManager resourceManager, Profiler profiler, CallbackInfoReturnable<List<String>> cir) {
        if(IntoTheOmega.CONFIG.splashOverride) {
            cir.setReturnValue(SPLASH_TEXT);
        }
    }
}
