package draylar.intotheomega.mixin.world;

import draylar.intotheomega.api.EndBiomeHelper;
import draylar.intotheomega.api.OpenSimplex2F;
import draylar.intotheomega.registry.OmegaBiomes;
import draylar.jvoronoi.JVoronoi;
import net.minecraft.util.math.noise.SimplexNoiseSampler;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.source.TheEndBiomeSource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.ArrayList;
import java.util.List;

/**
 * Fabric API provides an API for manipulating end biomes, but it is limited in control.
 * For specific cases where we need to control exactly how biomes spawn,
 *      this mixin takes priority over the Fabric API.
 */
@Mixin(TheEndBiomeSource.class)
public class EndBiomeSourceMixin {

    @Shadow @Final private long seed;

    @Shadow @Final private Registry<Biome> biomeRegistry;

    @Unique private static OpenSimplex2F NOISE = new OpenSimplex2F(0);
    @Unique private static long cachedSeed = Integer.MAX_VALUE;
    @Unique private static JVoronoi voronoi = new JVoronoi(0, 250);
    @Unique private static List<RegistryKey<Biome>> biomeSections = new ArrayList<>();

    static {
        biomeSections.add(OmegaBiomes.OMEGA_SLIME_WASTES_KEY);
        biomeSections.add(BiomeKeys.JUNGLE);

        // 3/5 do not have content rn
        biomeSections.add(null);
        biomeSections.add(null);
        biomeSections.add(null);
    }

    @Inject(method = "getBiomeForNoiseGen",at = @At("HEAD"))
    private void initializeNoise(int biomeX, int biomeY, int biomeZ, CallbackInfoReturnable<Biome> cir) {
        if(cachedSeed != seed) {
            cachedSeed = seed;
            voronoi = new JVoronoi(cachedSeed, 250);
            NOISE = new OpenSimplex2F(cachedSeed);
        }
    }

    @Inject(method = "getBiomeForNoiseGen", at = @At("RETURN"), cancellable = true)
    private void addVoronoiBiomes(int biomeX, int biomeY, int biomeZ, CallbackInfoReturnable<Biome> cir) {
        // Check distance from center.
        double fromCenter = Math.sqrt(Math.pow(biomeX, 2) + Math.pow(biomeZ, 2));

        // Ensure our position is valid before proceeding.
        if(fromCenter >= 1_000) {
            // [0, 1]
            double value = voronoi.tesselateWithEdge(biomeX, biomeZ, 100);

            // Ignore Voronoi edges to add proper borders between biome zones.
            if(value > 0) {
                int index = (int) (value * biomeSections.size());
                RegistryKey<Biome> found = biomeSections.get(index);

                if(found != null) {
                    cir.setReturnValue(biomeRegistry.get(found));
                }
            }
        }
    }

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
