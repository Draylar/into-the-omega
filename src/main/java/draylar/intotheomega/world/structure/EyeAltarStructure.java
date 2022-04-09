package draylar.intotheomega.world.structure;

import com.mojang.serialization.Codec;
import draylar.intotheomega.world.generator.EyeAltarGenerator;
import net.minecraft.util.BlockRotation;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;

public class EyeAltarStructure extends EndIslandStructure {

    public EyeAltarStructure(Codec<DefaultFeatureConfig> codec) {
        super(codec, height -> (collector, context) -> {
                    collector.addPiece(new EyeAltarGenerator(context.structureManager(), context.chunkPos().getBlockPos(0, height, 0), BlockRotation.random(context.random())));
                }
        );
    }
}
