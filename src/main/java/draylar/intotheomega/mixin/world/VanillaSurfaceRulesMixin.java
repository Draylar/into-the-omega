package draylar.intotheomega.mixin.world;

import draylar.intotheomega.registry.OmegaBiomes;
import net.minecraft.world.gen.surfacebuilder.MaterialRules;
import net.minecraft.world.gen.surfacebuilder.VanillaSurfaceRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(VanillaSurfaceRules.class)
public class VanillaSurfaceRulesMixin {

    /**
     * @author
     */
//    @Overwrite
//    public static MaterialRules.MaterialRule getEndStoneRule() {
//        return MaterialRules.sequence(
//                MaterialRules.condition(
//                        MaterialRules.biome(OmegaBiomes.BLACK_THORN_FOREST_KEY),
//                        new Mate
//                )
//        );
//    }
}
