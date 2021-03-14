package draylar.intotheomega.entity;

import draylar.intotheomega.block.WatchingEyeBlock;
import draylar.intotheomega.registry.OmegaEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Tickable;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

import java.util.List;

public class WatchingEyeBlockEntity extends BlockEntity implements Tickable {

    public WatchingEyeBlockEntity() {
        super(OmegaEntities.WATCHING_EYE);
    }

    @Override
    public void tick() {
        assert world != null;

        if(!world.isClient) {
            updateState(!getNearbyWatchingPlayers(64).isEmpty());
        }
    }

    private void updateState(boolean powered) {
        assert world != null;

        BlockState blockState = world.getBlockState(pos);

        // Block is powered, toggle off
        if(blockState.get(WatchingEyeBlock.POWERED) && !powered) {
            world.setBlockState(pos, blockState.with(WatchingEyeBlock.POWERED, false), 3);
            world.updateNeighbors(pos, world.getBlockState(pos).getBlock());
        }

        // Block is not powered, toggle on
        if(!blockState.get(WatchingEyeBlock.POWERED) && powered) {
            world.setBlockState(pos, blockState.with(WatchingEyeBlock.POWERED, true), 3);
            world.updateNeighbors(pos, world.getBlockState(pos).getBlock());
        }
    }

    private List<PlayerEntity> getNearbyWatchingPlayers(int radius) {
        assert world != null;

        return world.getEntitiesByClass(PlayerEntity.class, new Box(pos.add(-radius, -radius, -radius), pos.add(radius, radius, radius)), player -> {
            HitResult rayTrace = player.raycast(64, 0, false);

            // Check if ray trace hit this position
            if(rayTrace instanceof BlockHitResult) {
                BlockHitResult blockHitResult = (BlockHitResult) rayTrace;
                BlockPos blockPos = blockHitResult.getBlockPos();
                return pos.equals(blockPos);
            }

            return false;
        });
    }
}
