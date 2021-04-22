package draylar.intotheomega.mixin.dungeon;

import draylar.intotheomega.registry.OmegaStatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockItem.class)
public class BlockItemMixin {

    @Inject(
            method = "place(Lnet/minecraft/item/ItemPlacementContext;)Lnet/minecraft/util/ActionResult;",
            at = @At("HEAD"), cancellable = true)
    private void beforePlace(ItemPlacementContext context, CallbackInfoReturnable<ActionResult> cir) {
        if(context.getPlayer() != null && context.getPlayer().hasStatusEffect(OmegaStatusEffects.DUNGEON_LOCK)) {
            cir.setReturnValue(ActionResult.FAIL);
        }
    }
}
