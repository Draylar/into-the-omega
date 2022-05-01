package draylar.intotheomega.block;

import draylar.intotheomega.impl.ServerPlayerMirrorExtensions;
import draylar.intotheomega.registry.OmegaBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class MirrorBlock extends BlockWithEntity {

    public MirrorBlock(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new MirrorBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, OmegaBlockEntities.MIRROR_BLOCK, world.isClient ? null : MirrorBlockEntity::serverTick);
    }

    public static class MirrorBlockEntity extends BlockEntity {

        public MirrorBlockEntity(BlockPos pos, BlockState state) {
            super(OmegaBlockEntities.MIRROR_BLOCK, pos, state);
        }

        public static <E extends BlockEntity> void serverTick(World world, BlockPos pos, BlockState blockState, E e) {
            PlayerEntity closest = world.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), 64, false);
            if(closest != null) {
                ((ServerPlayerMirrorExtensions) closest).setMirrorOrigin(pos);
            }
        }
    }
}
