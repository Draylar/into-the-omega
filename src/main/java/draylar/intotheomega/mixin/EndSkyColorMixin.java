package draylar.intotheomega.mixin;

import draylar.intotheomega.api.client.EndSkyColor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Optional;

@Mixin(WorldRenderer.class)
public class EndSkyColorMixin {

    @Shadow private ClientWorld world;

    @Shadow @Final private MinecraftClient client;

    @Redirect(method = "renderEndSky", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/VertexConsumer;color(IIII)Lnet/minecraft/client/render/VertexConsumer;"))
    private VertexConsumer changeSkyColor(VertexConsumer instance, int r, int g, int b, int a) {
        RegistryEntry<Biome> biomeEntry = world.getBiome(client.player.getBlockPos());
        Biome biome = biomeEntry.value();
        Vec3d color = EndSkyColor.getColor(biomeEntry.getKey().get());
        return instance.color((int) color.getX(), (int) color.getY(), (int) color.getZ(), a);
    }
}
