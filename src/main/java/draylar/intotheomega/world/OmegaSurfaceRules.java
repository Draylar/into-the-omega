package draylar.intotheomega.world;

import draylar.intotheomega.biome.ChorusForestBiome;
import draylar.intotheomega.biome.OmegaSlimeWasteBiome;
import draylar.intotheomega.biome.StarfallValleyBiome;
import draylar.intotheomega.impl.NoiseApply;
import draylar.intotheomega.registry.OmegaBlocks;
import draylar.intotheomega.registry.world.OmegaNoiseKeys;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.VerticalSurfaceType;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.noise.NoiseParametersKeys;
import net.minecraft.world.gen.random.SimpleRandom;
import net.minecraft.world.gen.surfacebuilder.MaterialRules;

import java.util.Arrays;

public class OmegaSurfaceRules {

    private static final SimpleRandom RANDOM = new SimpleRandom(0);
    private static DoublePerlinNoiseSampler noise;
    private static int lastOctave = -8;
    private static Double[] lastAmplitude = new Double[0];

    private static int octave = -8;
    private static Double[] amplitude = new Double[] { 1.0d };

    public static MaterialRules.MaterialRule create() {
        octave = -7;
        amplitude = new Double[] { 1.0d, 0.5d, 1.0d };
        if(noise == null || lastOctave != octave || !Arrays.equals(lastAmplitude, amplitude)) {
            lastOctave = octave;
            lastAmplitude = amplitude;
            noise = DoublePerlinNoiseSampler.create(RANDOM, new DoublePerlinNoiseSampler.NoiseParameters(octave, Arrays.asList(amplitude)));
        }

        MaterialRules.MaterialCondition riverInteriorNoise = MaterialRules.noiseThreshold(OmegaNoiseKeys.OMEGA_SLIME_WASTE_RIVER, 0.0f, 0.1f);
        MaterialRules.MaterialCondition riverEdgeNoise = MaterialRules.noiseThreshold(OmegaNoiseKeys.OMEGA_SLIME_WASTE_RIVER, 0.1, 0.2f);
        MaterialRules.MaterialCondition riverInnerEdgeNoise = MaterialRules.noiseThreshold(OmegaNoiseKeys.OMEGA_SLIME_WASTE_RIVER, -0.1, 0.0f);

        ((NoiseApply) riverInteriorNoise).set(noise, 0.0f, 0.1f);
        ((NoiseApply) riverEdgeNoise).set(noise, 0.1f, 0.2f);
        ((NoiseApply) riverInnerEdgeNoise).set(noise, -0.1f, 0.0f);

        MaterialRules.MaterialRule omegaSlimeWasteRivers = MaterialRules.condition(MaterialRules.biome(OmegaSlimeWasteBiome.KEY),
                MaterialRules.sequence(

                        MaterialRules.condition(riverInnerEdgeNoise,
                                MaterialRules.sequence(
                                        MaterialRules.condition(
                                                MaterialRules.stoneDepth(3, true, VerticalSurfaceType.FLOOR),
                                                MaterialRules.block(Blocks.OBSIDIAN.getDefaultState()))
                                )),

                        // End Stone mix around the edge of Slime Rivers
                        MaterialRules.condition(riverEdgeNoise,
                                MaterialRules.sequence(
                                        MaterialRules.condition(
                                                MaterialRules.stoneDepth(3, true, VerticalSurfaceType.FLOOR),
                                                MaterialRules.block(Blocks.OBSIDIAN.getDefaultState()))
                                )),

                        // River Inside
                        MaterialRules.condition(riverInteriorNoise,
                                MaterialRules.sequence(
                                        // air above river
                                        MaterialRules.condition(
                                                MaterialRules.stoneDepth(-1, true, VerticalSurfaceType.FLOOR),
                                                MaterialRules.block(Blocks.AIR.getDefaultState())),

                                        // river
                                        MaterialRules.condition(
                                                MaterialRules.stoneDepth(3, true, VerticalSurfaceType.FLOOR),
                                                MaterialRules.block(OmegaBlocks.OMEGA_SLIME_FLUID.getDefaultState()))
                                ))

                )
        );

        MaterialRules.MaterialRule omegaSlimeWaste = MaterialRules.condition(MaterialRules.biome(OmegaSlimeWasteBiome.KEY),
                MaterialRules.condition(MaterialRules.stoneDepth(1, true, VerticalSurfaceType.FLOOR),
                        MaterialRules.sequence(

                                // Green
                                MaterialRules.condition(
                                        MaterialRules.noiseThreshold(NoiseParametersKeys.CAVE_CHEESE, 0.0f, 1.0f),
                                        MaterialRules.block(OmegaBlocks.CONGEALED_OMEGA_SLIME.getDefaultState())),

                                // Omega
                                MaterialRules.condition(
                                        MaterialRules.noiseThreshold(NoiseParametersKeys.CAVE_CHEESE, -1.0f, 0.0f),
                                        MaterialRules.block(OmegaBlocks.CONGEALED_SLIME.getDefaultState()))
                        )));

        MaterialRules.MaterialRule coreEndIsland = MaterialRules.condition(MaterialRules.biome(BiomeKeys.THE_END),
                MaterialRules.condition(MaterialRules.stoneDepth(1, true, VerticalSurfaceType.FLOOR),
                        MaterialRules.sequence(

                                // Purple
                                MaterialRules.condition(
                                        MaterialRules.noiseThreshold(NoiseParametersKeys.CAVE_CHEESE, -0.05f, 0.0f),
                                        MaterialRules.block(Blocks.CRYING_OBSIDIAN.getDefaultState()))
                        )));

        return MaterialRules.sequence(coreEndIsland, omegaSlimeWasteRivers, omegaSlimeWaste, StarfallValleyBiome.createSurfaceRule(), ChorusForestBiome.createSurfaceRule());
    }
}
