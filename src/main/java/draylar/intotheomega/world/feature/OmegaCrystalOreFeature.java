package draylar.intotheomega.world.feature;

import com.mojang.serialization.Codec;
import draylar.intotheomega.registry.OmegaBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Heightmap;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.BitSet;
import java.util.Random;

public class OmegaCrystalOreFeature extends OreFeature {

    private static final int SIZE = 16;

    public OmegaCrystalOreFeature(Codec<OreFeatureConfig> codec) {
        super(codec);
    }

    public boolean generate(FeatureContext<OreFeatureConfig> context) {
        // Do not generate ore on the main end island
        if(Math.sqrt(context.getOrigin().getSquaredDistance(0, 0, 0, true)) < 1024) {
            return false;
        }

        return super.generate(context);
    }
}