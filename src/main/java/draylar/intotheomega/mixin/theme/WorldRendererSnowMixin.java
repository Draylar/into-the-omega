package draylar.intotheomega.mixin.theme;

import draylar.intotheomega.IntoTheOmegaClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRendererSnowMixin {

    @Shadow @Final private MinecraftClient client;

    @Redirect(
            method = "renderWeather",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/biome/Biome;getPrecipitation()Lnet/minecraft/world/biome/Biome$Precipitation;"))
    private Biome.Precipitation onGetPrecipitation(Biome biome) {
        if(IntoTheOmegaClient.inIceIsland) {
            return Biome.Precipitation.SNOW;
        }

        return biome.getPrecipitation();
    }

    // TODO: do not redirect here, switch to modifying the constant after it has been assigned.
    // I can not figure out how to do this right now, so I am opting for a redirect.
    @Redirect(
            method = "renderWeather",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/world/ClientWorld;getRainGradient(F)F"))
    private float modifyRainGradient(ClientWorld clientWorld, float delta) {
        if(IntoTheOmegaClient.inIceIsland) {
            return 5;
        }

        return client.world.getRainGradient(delta);
    }

//    @Redirect(
//            method = "renderWeather",
//            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/world/ClientWorld;getRainGradient(F)F"))
//    private float redirectTemperatureCheck(ClientWorld instance, float delta) {
//        if(IntoTheOmegaClient.inIceIsland) {
//            return 1.0f;
//        }
//
//        return client.world.getRainGradient(delta);
//    }
}
