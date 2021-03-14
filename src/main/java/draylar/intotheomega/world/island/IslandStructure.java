package draylar.intotheomega.world.island;

import com.mojang.serialization.Codec;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class IslandStructure extends StructureFeature<DefaultFeatureConfig> {

    public IslandStructure(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public StructureStartFactory<DefaultFeatureConfig> getStructureStartFactory() {
        return IslandStructureStart::new;
    }
}
