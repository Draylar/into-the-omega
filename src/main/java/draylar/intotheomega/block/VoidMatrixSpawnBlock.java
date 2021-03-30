package draylar.intotheomega.block;

import draylar.intotheomega.entity.block.VoidMatrixSpawnBlockEntity;
import draylar.intotheomega.entity.void_matrix.VoidMatrixEntity;
import draylar.intotheomega.registry.OmegaEntities;
import draylar.intotheomega.registry.OmegaItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class VoidMatrixSpawnBlock extends Block implements BlockEntityProvider {

    public VoidMatrixSpawnBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(!world.isClient && hand == Hand.MAIN_HAND) {
            VoidMatrixEntity voidMatrixEntity = OmegaEntities.VOID_MATRIX.create(world);
            voidMatrixEntity.updatePosition(pos.getX() + .5, pos.getY() + 10, pos.getZ() + .5);
            voidMatrixEntity.setHome(pos);
            world.spawnEntity(voidMatrixEntity);
        }

        return ActionResult.SUCCESS;
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockView world) {
        return new VoidMatrixSpawnBlockEntity();
    }
}
