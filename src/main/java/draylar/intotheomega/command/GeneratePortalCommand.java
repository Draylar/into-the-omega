package draylar.intotheomega.command;

import com.mojang.brigadier.tree.LiteralCommandNode;
import draylar.intotheomega.mixin.ServerChunkManagerAccessor;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.EndPortalFeature;

public class GeneratePortalCommand {

    public static void initialize() {
        if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
            CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
                LiteralCommandNode<ServerCommandSource> pillar = CommandManager.literal("portal")
                        .requires(source -> source.hasPermissionLevel(2))
                        .executes(context -> {
                            ServerPlayerEntity player = context.getSource().getPlayer();
                            HitResult cast = player.raycast(128, 0, false);
                            Vec3d pos = cast.getPos().subtract(0, 1, 0);
                            BlockPos blockPos = new BlockPos(pos);
                            EndPortalFeature feature = new EndPortalFeature(true);
                            feature.generate((ServerWorld) player.world, ((ServerChunkManagerAccessor) player.world.getChunkManager()).getChunkGenerator(), player.world.random, blockPos, DefaultFeatureConfig.INSTANCE);
                            return 1;
                        }).build();

                dispatcher.getRoot().addChild(pillar);
            });
        }
    }
}
