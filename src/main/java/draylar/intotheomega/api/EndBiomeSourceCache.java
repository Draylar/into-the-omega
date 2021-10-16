package draylar.intotheomega.api;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.biome.source.TheEndBiomeSource;
import org.jetbrains.annotations.Nullable;

public class EndBiomeSourceCache implements ServerWorldEvents.Load {

    @Nullable
    public static TheEndBiomeSource cached = null;

    @Nullable
    public static DynamicRegistryManager manager;

    @Override
    public void onWorldLoad(MinecraftServer server, ServerWorld world) {
        if(world.getChunkManager().getChunkGenerator().getBiomeSource() instanceof TheEndBiomeSource) {
            cached = (TheEndBiomeSource) world.getChunkManager().getChunkGenerator().getBiomeSource();
            manager = server.getRegistryManager();
        }
    }
}
