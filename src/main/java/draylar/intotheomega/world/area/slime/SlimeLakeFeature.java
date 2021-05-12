package draylar.intotheomega.world.area.slime;

import com.mojang.serialization.Codec;
import draylar.intotheomega.registry.OmegaBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.StructureFeature;

import java.util.Random;

public class SlimeLakeFeature extends Feature<DefaultFeatureConfig> {

    private static final BlockState CAVE_AIR = Blocks.CAVE_AIR.getDefaultState();

    public SlimeLakeFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(StructureWorldAccess structureWorldAccess, ChunkGenerator chunkGenerator, Random random, BlockPos pos, DefaultFeatureConfig config) {
        // Find a position to generate our lake structure at.
        while (pos.getY() > 5 && structureWorldAccess.isAir(pos)) {
            pos = pos.down();
        }

        // Only place the lake if the y-position is valid.
        if (pos.getY() <= 4) {
            return false;
        } else {
            pos = pos.down(4);

            // Do not overwrite Omega Slime Obelisk structures!
            if (structureWorldAccess.getStructures(ChunkSectionPos.from(pos), StructureFeature.VILLAGE).findAny().isPresent()) {
                return false;
            } else {
                boolean[] bls = new boolean[2048];
                int i = random.nextInt(4) + 4;

                int x;
                for (x = 0; x < i; ++x) {
                    double d = random.nextDouble() * 6.0D + 3.0D;
                    double e = random.nextDouble() * 4.0D + 2.0D;
                    double f = random.nextDouble() * 6.0D + 3.0D;
                    double g = random.nextDouble() * (16.0D - d - 2.0D) + 1.0D + d / 2.0D;
                    double h = random.nextDouble() * (8.0D - e - 4.0D) + 2.0D + e / 2.0D;
                    double k = random.nextDouble() * (16.0D - f - 2.0D) + 1.0D + f / 2.0D;

                    // Collect positions based on a radius around the central spawn point.
                    for (int l = 1; l < 15; ++l) {
                        for (int m = 1; m < 15; ++m) {
                            for (int n = 1; n < 7; ++n) {
                                double o = ((double) l - g) / (d / 2.0D);
                                double p = ((double) n - h) / (e / 2.0D);
                                double q = ((double) m - k) / (f / 2.0D);
                                double radius = o * o + p * p + q * q;
                                if (radius < 1.0D) {
                                    bls[(l * 16 + m) * 8 + n] = true;
                                }
                            }
                        }
                    }
                }

                int y;
                int z;
                boolean bl2;
                for (x = 0; x < 16; ++x) {
                    for (z = 0; z < 16; ++z) {
                        for (y = 0; y < 8; ++y) {
                            bl2 = !bls[(x * 16 + z) * 8 + y] && (x < 15 && bls[((x + 1) * 16 + z) * 8 + y] || x > 0 && bls[((x - 1) * 16 + z) * 8 + y] || z < 15 && bls[(x * 16 + z + 1) * 8 + y] || z > 0 && bls[(x * 16 + (z - 1)) * 8 + y] || y < 7 && bls[(x * 16 + z) * 8 + y + 1] || y > 0 && bls[(x * 16 + z) * 8 + (y - 1)]);
                            if (bl2) {
                                Material material = structureWorldAccess.getBlockState(pos.add(x, y, z)).getMaterial();
                                if (y >= 4 && material.isLiquid()) {
                                    return false;
                                }

                                if (y < 4 && !material.isSolid() && structureWorldAccess.getBlockState(pos.add(x, y, z)) != OmegaBlocks.OMEGA_SLIME_FLUID.getDefaultState()) {
                                    return false;
                                }
                            }
                        }
                    }
                }

                // Populate Air & Fluid based on placement positions.
                for (x = 0; x < 16; ++x) {
                    for (z = 0; z < 16; ++z) {
                        for (y = 0; y < 8; ++y) {
                            if (bls[(x * 16 + z) * 8 + y]) {
                                structureWorldAccess.setBlockState(pos.add(x, y, z), y >= 4 ? CAVE_AIR : OmegaBlocks.OMEGA_SLIME_FLUID.getDefaultState(), 2);
                            }
                        }
                    }
                }

                // Place random End Stone Brick blocks around the lake to add some noise.
                for (x = 0; x < 16; ++x) {
                    for (z = 0; z < 16; ++z) {
                        for (y = 0; y < 8; ++y) {
                            bl2 = !bls[(x * 16 + z) * 8 + y] && (x < 15 && bls[((x + 1) * 16 + z) * 8 + y] || x > 0 && bls[((x - 1) * 16 + z) * 8 + y] || z < 15 && bls[(x * 16 + z + 1) * 8 + y] || z > 0 && bls[(x * 16 + (z - 1)) * 8 + y] || y < 7 && bls[(x * 16 + z) * 8 + y + 1] || y > 0 && bls[(x * 16 + z) * 8 + (y - 1)]);
                            if (bl2 && (y < 4 || random.nextInt(2) != 0) && structureWorldAccess.getBlockState(pos.add(x, y, z)).getMaterial().isSolid()) {
                                structureWorldAccess.setBlockState(pos.add(x, y, z), Blocks.END_STONE_BRICKS.getDefaultState(), 2);
                            }
                        }
                    }
                }

                // Add a ceiling waterfall for some lakes.
                if(random.nextDouble() < .25) {
                    while(pos.getY() <= 200) {
                        structureWorldAccess.setBlockState(pos, OmegaBlocks.OMEGA_SLIME_FLUID.getDefaultState(), 3);
                        pos = pos.up();
                    }
                }

                return true;
            }
        }
    }
}