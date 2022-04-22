package draylar.intotheomega.mixin.world;

import net.minecraft.block.Block;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.OptionalLong;

@Mixin(DimensionType.class)
public class EndBiomeHeightMixin {

    @Shadow @Final public static Identifier THE_END_ID;
    @Unique private static Identifier effectIdContext;

    @Inject(method = "create", at = @At("HEAD"))
    private static void storeIDContext(OptionalLong fixedTime, boolean hasSkylight, boolean hasCeiling, boolean ultrawarm, boolean natural, double coordinateScale, boolean hasEnderDragonFight, boolean piglinSafe, boolean bedWorks, boolean respawnAnchorWorks, boolean hasRaids, int minimumY, int height, int logicalHeight, TagKey<Block> tagKey, Identifier effects, float ambientLight, CallbackInfoReturnable<DimensionType> cir) {
        effectIdContext = effects;
    }

    @ModifyVariable(method = "create", at = @At("HEAD"), index = 13)
    private static int modifyMaxHeight(int value) {
        if(effectIdContext.equals(THE_END_ID)) {
            return 512;
        }

        return value;
    }

    @ModifyVariable(method = "create", at = @At("HEAD"), index = 14)
    private static int modifyMaxLocalHeight(int value) {
        if(effectIdContext.equals(THE_END_ID)) {
            return 512;
        }

        return value;
    }
}
