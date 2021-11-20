package draylar.intotheomega.command;

import com.mojang.brigadier.tree.RootCommandNode;
import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;

public class EndCommand {

    public static void register(RootCommandNode<ServerCommandSource> root) {
        root.addChild(CommandManager.literal("end")
                .executes(context -> {
                    FabricDimensions.teleport(
                            context.getSource().getPlayer(),
                            context.getSource().getPlayer().getServer().getWorld(World.END),
                            new TeleportTarget(new Vec3d(0, 100, 0), Vec3d.ZERO, 0, 0));

                    return 1;
                }).build());
    }
}
