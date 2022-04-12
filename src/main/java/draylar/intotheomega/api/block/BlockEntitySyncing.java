package draylar.intotheomega.api.block;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.server.world.ServerWorld;

public interface BlockEntitySyncing {

    default void sync() {
        if(this instanceof BlockEntity blockEntity) {
            if(blockEntity.getWorld() != null && !blockEntity.getWorld().isClient) {
                blockEntity.markDirty();
                ((ServerWorld) blockEntity.getWorld()).getChunkManager().markForUpdate(blockEntity.getPos());
            }
        }
    }
}
