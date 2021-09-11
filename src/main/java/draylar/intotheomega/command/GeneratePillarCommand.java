package draylar.intotheomega.command;

import com.google.common.collect.ImmutableList;
import com.mojang.brigadier.arguments.IntegerArgumentType;
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
import net.minecraft.world.gen.feature.EndSpikeFeature;
import net.minecraft.world.gen.feature.EndSpikeFeatureConfig;

public class GeneratePillarCommand {

    public static void initialize() {
        if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
            CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
                LiteralCommandNode<ServerCommandSource> pillar = CommandManager.literal("pillar")
                        .requires(source -> source.hasPermissionLevel(2))
                        .then(CommandManager.argument("size", IntegerArgumentType.integer(1, 10))
                                .executes(context -> {
                                    int width = IntegerArgumentType.getInteger(context, "size");
                                    ServerPlayerEntity player = context.getSource().getPlayer();
                                    HitResult cast = player.raycast(128, 0, false);
                                    Vec3d pos = cast.getPos();
                                    BlockPos blockPos = new BlockPos(pos);
                                    EndSpikeFeature feature = new EndSpikeFeature(EndSpikeFeatureConfig.CODEC);
                                    EndSpikeFeature.Spike spike = new EndSpikeFeature.Spike(blockPos.getX(), blockPos.getZ(), width, 115, false);

                                    feature.generate((ServerWorld) player.world, ((ServerChunkManagerAccessor) player.world.getChunkManager()).getChunkGenerator(), player.world.random, blockPos, new EndSpikeFeatureConfig(
                                            false, ImmutableList.of(spike), new BlockPos(0, 128, 0)
                                    ));

                                    return 1;
                                })).build();

                dispatcher.getRoot().addChild(pillar);
            });
        }
    }
}
