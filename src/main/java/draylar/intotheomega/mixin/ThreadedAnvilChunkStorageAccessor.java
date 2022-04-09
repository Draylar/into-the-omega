package draylar.intotheomega.mixin;

import net.minecraft.server.world.ThreadedAnvilChunkStorage;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ThreadedAnvilChunkStorage.class)
public interface ThreadedAnvilChunkStorageAccessor {

    @Accessor
    ChunkGenerator getChunkGenerator();
}
