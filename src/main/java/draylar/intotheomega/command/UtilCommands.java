package draylar.intotheomega.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.TypeFilter;
import net.minecraft.world.GameMode;

public class UtilCommands implements CommandRegistrationCallback {

    @Override
    public void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated) {
        var gmc = CommandManager.literal("gmc")
                .requires(source -> source.hasPermissionLevel(2))
                .executes(context -> {
                    context.getSource().getPlayer().changeGameMode(GameMode.CREATIVE);
                    return 1;
                }).build();

        var gms = CommandManager.literal("gms")
                .requires(source -> source.hasPermissionLevel(2))
                .executes(context -> {
                    context.getSource().getPlayer().changeGameMode(GameMode.SURVIVAL);
                    return 1;
                }).build();

        var km = CommandManager.literal("km")
                .requires(source -> source.hasPermissionLevel(2))
                .executes(context -> {
                    for (Entity entity : context.getSource().getPlayer().getWorld().getEntitiesByType(TypeFilter.instanceOf(Entity.class), it -> (!(it instanceof PlayerEntity)))) {
                        entity.kill();
                    }

                    return 1;
                }).build();

        var speed = CommandManager.literal("speed")
                .requires(source -> source.hasPermissionLevel(2))
                .then(CommandManager.argument("speed", FloatArgumentType.floatArg(0.0f, 50.0f)).executes(context -> {
                    float speedArgument = FloatArgumentType.getFloat(context, "speed");
                    ServerPlayerEntity player = context.getSource().getPlayer();
                    player.getAbilities().setFlySpeed(speedArgument);
                    player.sendAbilitiesUpdate();
                    return 1;
                })).build();

        dispatcher.getRoot().addChild(gmc);
        dispatcher.getRoot().addChild(gms);
        dispatcher.getRoot().addChild(speed);
        dispatcher.getRoot().addChild(km);
    }
}
