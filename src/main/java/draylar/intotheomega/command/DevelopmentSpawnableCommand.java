package draylar.intotheomega.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import draylar.intotheomega.api.DevelopmentSpawnable;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;

import java.util.HashMap;
import java.util.Map;

public class DevelopmentSpawnableCommand {

    private static final Map<String, DevelopmentSpawnable> STRUCTURES = new HashMap<>();

    public static void initialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            dispatcher.getRoot().addChild(
                    CommandManager.literal("devspawn")
                            .then(CommandManager.argument("structure", StringArgumentType.string())
                                    .suggests((context, builder) -> {
                                        STRUCTURES.keySet().forEach(builder::suggest);
                                        return builder.buildFuture();
                                    }).executes(context -> {
                                        String structure = StringArgumentType.getString(context, "structure");
                                        DevelopmentSpawnable spawnable = DevelopmentSpawnableCommand.STRUCTURES.get(structure);
                                        if(spawnable != null) {
                                            ServerPlayerEntity player = context.getSource().getPlayer();
                                            HitResult ray = player.raycast(256, 0, false);
                                            spawnable.spawn((ServerWorld) player.world, new BlockPos(ray.getPos().getX(), ray.getPos().getY(), ray.getPos().getZ()));
                                        }

                                        return 1;
                                    })).build()
            );
        });
    }

    public static void registerSpawnable(String id, DevelopmentSpawnable spawnable) {
        DevelopmentSpawnableCommand.STRUCTURES.put(id, spawnable);
    }
}
