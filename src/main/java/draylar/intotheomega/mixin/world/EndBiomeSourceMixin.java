package draylar.intotheomega.mixin.world;

import draylar.intotheomega.api.EndBiomeHelper;
import draylar.intotheomega.api.OpenSimplex2F;
import draylar.intotheomega.registry.OmegaBiomes;
import net.minecraft.util.math.noise.SimplexNoiseSampler;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.TheEndBiomeSource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

/**
 * Fabric API provides an API for manipulating end biomes, but it is limited in control.
 * For specific cases where we need to control exactly how biomes spawn,
 *      this mixin takes priority over the Fabric API.
 */
@Mixin(TheEndBiomeSource.class)
public class EndBiomeSourceMixin {

    @Shadow @Final private long seed;

    @Shadow @Final private Registry<Biome> biomeRegistry;
    @Shadow @Final private Biome centerBiome;

    @Unique private static OpenSimplex2F NOISE = new OpenSimplex2F(0);
    @Unique private static long cachedSeed = Integer.MAX_VALUE;

    /**
     * The Abyssal Biomes are home to the Abyss Flower Island, which needs to spawn in a very empty area.
     * To accomplish this, we place a circle of empty biomes in random spots around the end,
     *      which gives us the empty area we need to work with.
     *
     * @param biomeX  x position of the biome check
     * @param biomeY  y position of the biome check
     * @param biomeZ  z position of the biome check
     * @param cir     mixin callback information
     */
    @Inject(method = "getBiomeForNoiseGen", at = @At("RETURN"), cancellable = true)
    private void addAbyssalBiomes(int biomeX, int biomeY, int biomeZ, CallbackInfoReturnable<Biome> cir) {
        // Assign new noise if it does not match this world
        if(cachedSeed != seed) {
            cachedSeed = seed;
            NOISE = new OpenSimplex2F(cachedSeed);
        }

        // Check distance from center.
        double fromCenter = Math.sqrt(Math.pow(biomeX, 2) + Math.pow(biomeZ, 2));

        // 1. ensure we are not near spawn
        // 2. ensure noise evaluation succeeds
        //    => place Abyssal Void
        int distance = EndBiomeHelper.distanceToZone(biomeX * 4, biomeZ * 4);
        if(fromCenter >= 10_000) {
            if(distance < 6) {
                cir.setReturnValue(biomeRegistry.get(OmegaBiomes.ABYSSAL_CORE_KEY));
            } else if (distance < 250) {
                cir.setReturnValue(biomeRegistry.get(OmegaBiomes.ABYSSAL_VOID_KEY));
            }
        }
    }

    @Inject(method = "getNoiseAt", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;abs(F)F", ordinal = 0), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private static void cancelIslandsInAbyssalAreas(SimplexNoiseSampler simplexNoiseSampler, int columnX, int columnZ, CallbackInfoReturnable<Float> cir, int k, int l, int m, int n, float f, int o, int p, long q, long r) {
        // eval noise to cancel islands
        if(EndBiomeHelper.distanceToZone((columnX - 1) * 8, (columnZ - 1) * 8) < 250) {
            cir.setReturnValue(f);
        }
    }
}
