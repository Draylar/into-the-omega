package draylar.intotheomega.world.structure;

import com.mojang.serialization.Codec;
import draylar.intotheomega.world.generator.SmallPhantomTowerGenerator;
import net.minecraft.util.BlockRotation;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;

public class SmallPhantomTowerStructure extends EndIslandStructure {

    public SmallPhantomTowerStructure(Codec<DefaultFeatureConfig> codec) {
        super(codec, height -> (collector, context) -> {
                    collector.addPiece(new SmallPhantomTowerGenerator(context.structureManager(), context.chunkPos().getBlockPos(0, height, 0), BlockRotation.random(context.random())));
                }
        );
    }
}
