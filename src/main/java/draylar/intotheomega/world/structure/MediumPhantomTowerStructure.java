package draylar.intotheomega.world.structure;

import com.mojang.serialization.Codec;
import draylar.intotheomega.world.generator.MediumPhantomTowerGenerator;
import net.minecraft.util.BlockRotation;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;

public class MediumPhantomTowerStructure extends EndIslandStructure {

    public MediumPhantomTowerStructure(Codec<DefaultFeatureConfig> codec) {
        super(codec, height -> (collector, context) -> {
                    collector.addPiece(new MediumPhantomTowerGenerator(context.structureManager(), context.chunkPos().getBlockPos(0, height, 0), BlockRotation.random(context.random())));
                }
        );
    }
}
