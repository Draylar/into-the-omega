package draylar.intotheomega.mixin.dev;

import draylar.intotheomega.IntoTheOmegaClient;
import draylar.intotheomega.mixin.MobEntityAccessor;
import draylar.intotheomega.network.Networking;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.ai.goal.PrioritizedGoal;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.Inject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mixin(DebugInfoSender.class)
public class DebugInfoSenderMixin {

    /**
     * @author Draylar
     */
    @Overwrite
    public static void sendGoalSelector(World world, MobEntity mob, GoalSelector goalSelector) {
        // Do not do this in production
        if(FabricLoader.getInstance().isDevelopmentEnvironment()) {

            // Only send AI debug data from S2C
            if(world instanceof ServerWorld) {
                List<PrioritizedGoal> collect = goalSelector.getRunningGoals().collect(Collectors.toList());

                // send to players
                IntoTheOmegaClient.DEVELOPMENT_AI_SYNC.put(mob.getEntityId(), new ArrayList<>(collect));
            }
        }
    }

    /**
     * @author Draylar
     */
    @Overwrite
    public static void sendPathfindingData(World world, MobEntity mob, @Nullable Path path, float nodeReachProximity) {
        // Do not do this in production
        if(FabricLoader.getInstance().isDevelopmentEnvironment()) {

            // Only send AI debug data from S2C
            if(world instanceof ServerWorld) {
                IntoTheOmegaClient.DEVELOPMENT_PATH_SYNC.put(mob.getEntityId(), path);
            }
        }
    }
}
