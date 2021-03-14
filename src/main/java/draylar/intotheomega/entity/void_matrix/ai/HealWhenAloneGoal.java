package draylar.intotheomega.entity.void_matrix.ai;

import draylar.intotheomega.entity.void_matrix.VoidMatrixEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.world.GameMode;

public class HealWhenAloneGoal extends Goal {

    private final ServerWorld world;
    private final VoidMatrixEntity entity;
    private int lastHealAge = 0;

    public HealWhenAloneGoal(VoidMatrixEntity entity) {
        this.world = (ServerWorld) entity.world;
        this.entity = entity;
    }


    // todo: what about a smooth increment every tick (1 hp/t) when players are not nearby?
    @Override
    public boolean canStart() {
        if(entity.age % 20 == 0) {
            return world.getEntitiesByClass(
                    ServerPlayerEntity.class,
                    new Box(entity.getBlockPos().add(-64, -64, -64), entity.getBlockPos().add(64, 64, 64)),
                    player -> true).isEmpty();
        }

        return false;
    }

    @Override
    public void start() {
        // Heal by 50 every 5 seconds when triggered
        if(entity.age > lastHealAge + (20 * 5)) {
            entity.heal(50);
            lastHealAge = entity.age;
        }
    }

    @Override
    public boolean shouldContinue() {
        return canStart();
    }
}
