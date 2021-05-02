package draylar.intotheomega.mixin.mechanic;

import draylar.intotheomega.registry.OmegaItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.EndPortalBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EndPortalBlock.class)
public class EndPortalBlockMixin {

    @Inject(
            method = "onEntityCollision",
            at = @At("HEAD"), cancellable = true)
    private void netheriteToVoid(BlockState state, World world, BlockPos pos, Entity entity, CallbackInfo ci) {
        if(entity instanceof ItemEntity) {
            ItemEntity ie = (ItemEntity) entity;
            ItemStack stack = ie.getStack();
            if(stack.getItem().equals(Items.NETHERITE_SCRAP)) {
                ItemStack newStack = new ItemStack(OmegaItems.VOIDING_END_GEODE);
                newStack.setCount(stack.getCount());
                ie.setStack(newStack);
                ie.setVelocity(0, .05, 0);
                ie.setNoGravity(true);
            } else if (stack.getItem().equals(OmegaItems.VOIDING_END_GEODE)) {
                ci.cancel();
            }
        }
    }
}
