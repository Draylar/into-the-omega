package draylar.intotheomega.impl.world;

import com.mojang.serialization.Codec;
import net.minecraft.world.gen.surfacebuilder.MaterialRules;

public class SlimeMaterialRule implements MaterialRules.MaterialRule {

    @Override
    public Codec<? extends MaterialRules.MaterialRule> codec() {
        return null;
    }

    @Override
    public MaterialRules.BlockStateRule apply(MaterialRules.MaterialRuleContext context) {
        return null;
    }
}
