package draylar.intotheomega.world.feature;

import com.mojang.serialization.Codec;
import draylar.intotheomega.registry.OmegaTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChorusFlowerBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.Random;

public class VariableChorusPlantFeature extends Feature<DefaultFeatureConfig> {

    private final int size;

    public VariableChorusPlantFeature(Codec<DefaultFeatureConfig> configCodec, int size) {
        super(configCodec);
        this.size = size;
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess structureWorldAccess = context.getWorld();
        BlockPos blockPos = context.getOrigin();

        Random random = context.getRandom();
        BlockState down = structureWorldAccess.getBlockState(blockPos.down());
        if(structureWorldAccess.isAir(blockPos) && (down.isOf(Blocks.END_STONE) || down.isIn(OmegaTags.CHORUS_GROUND))) {
            ChorusFlowerBlock.generate(structureWorldAccess, blockPos, random, size);
            return true;
        }

        return false;
    }
}
