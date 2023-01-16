package draylar.intotheomega.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import draylar.intotheomega.api.entity.AIDebug;
import draylar.intotheomega.entity.void_matrix.ai.KnockbackPulseGoal;
import draylar.intotheomega.registry.OmegaEntities;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.command.CommandSource;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class AICommand implements CommandRegistrationCallback {

    private static final Map<EntityType<?>, Map<String, Class<? extends Goal>>> AVAILABLE_DEBUG_GOALS = Map.of(
            OmegaEntities.VOID_MATRIX, Map.of(
                    "knockback_pulse", KnockbackPulseGoal.class
            )
    );

    @Override
    public void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated) {
        var ai = CommandManager.literal("ai")
                .requires(source -> source.hasPermissionLevel(2))
                .then(CommandManager.argument("entity", EntityArgumentType.entities())
                        .then(CommandManager.argument("id", StringArgumentType.word())
                                .suggests((context, builder) -> {
                                    Collection<? extends Entity> entities = EntityArgumentType.getEntities(context, "entity");
                                    List<String> suggestions = new ArrayList<>();
                                    for (Entity entity : entities) {
                                        Map<String, Class<? extends Goal>> type = AVAILABLE_DEBUG_GOALS.get(entity.getType());
                                        if(type != null) {
                                            suggestions.addAll(type.keySet());
                                        }
                                    }

                                    CommandSource.suggestMatching(suggestions, builder);
                                    return builder.buildFuture();
                                })
                                .executes(context -> {
                                    String id = StringArgumentType.getString(context, "id");
                                    Collection<? extends Entity> entities = EntityArgumentType.getEntities(context, "entity");

                                    for (Entity entity : entities) {
                                        EntityType<?> type = entity.getType();
                                        Map<String, Class<? extends Goal>> byId = AVAILABLE_DEBUG_GOALS.get(type);
                                        if(byId != null) {
                                            Class<? extends Goal> goal = byId.get(id);
                                            if(goal != null) {
                                                if(entity instanceof AIDebug debug) {
                                                    debug.markDebugGoal(goal);
                                                }
                                            }
                                        }
                                    }

                                    return 1;
                                })
                        )
                );

        dispatcher.register(ai);
    }
}
