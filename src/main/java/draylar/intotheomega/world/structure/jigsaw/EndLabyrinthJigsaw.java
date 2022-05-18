package draylar.intotheomega.world.structure.jigsaw;

import com.mojang.serialization.Codec;
import net.minecraft.world.gen.feature.JigsawFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;

public class EndLabyrinthJigsaw extends JigsawFeature {

    public EndLabyrinthJigsaw(Codec<StructurePoolFeatureConfig> configCodec) {
        super(configCodec, 30, true, false, context -> true);
    }
}
