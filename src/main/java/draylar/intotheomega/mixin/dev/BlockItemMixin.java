package draylar.intotheomega.mixin.dev;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.impl.ServerPlayerMirrorExtensions;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockItem.class)
public abstract class BlockItemMixin {

    @Shadow public abstract Block getBlock();

    @Inject(method = "place(Lnet/minecraft/item/ItemPlacementContext;)Lnet/minecraft/util/ActionResult;", at = @At("HEAD"))
    private void mirrorPlacement(ItemPlacementContext context, CallbackInfoReturnable<ActionResult> cir) {
        if(FabricLoader.getInstance().isDevelopmentEnvironment() && !context.getWorld().isClient && context.getPlayer() != null) {
            ServerPlayerMirrorExtensions mirror = (ServerPlayerMirrorExtensions) context.getPlayer();
            final BlockPos pos = context.getBlockPos();
            final World world = context.getWorld();
            final BlockPos center = mirror.getOrigin();
            if(center != null) {
                IntoTheOmega.mirrorPlacement(context.getPlayer(), context.getStack(), context.getSide(), world, center, pos, getBlock());
            }
        }
    }
}
