package draylar.intotheomega.world.structure;

import draylar.intotheomega.world.generator.SmallChorusMonumentGenerator;
import net.minecraft.util.BlockRotation;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;

public class SmallChorusMonumentStructure extends EndIslandStructure {

    public SmallChorusMonumentStructure() {
        super(DefaultFeatureConfig.CODEC, height -> (collector, context) -> {
                    collector.addPiece(new SmallChorusMonumentGenerator(context.structureManager(), context.chunkPos().getBlockPos(0, height - 1, 0), BlockRotation.random(context.random())));
                });
    }
}
