package draylar.intotheomega.entity.void_matrix.ai;

import draylar.intotheomega.entity.void_matrix.VoidMatrixEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

import java.util.EnumSet;

public class TeleportHomeWhenAloneGoal extends Goal {

    private final ServerWorld world;
    private final VoidMatrixEntity entity;

    public TeleportHomeWhenAloneGoal(VoidMatrixEntity entity) {
        this.world = (ServerWorld) entity.world;
        this.entity = entity;
        this.setControls(EnumSet.of(Goal.Control.MOVE));
    }

    @Override
    public boolean canStart() {
        if(entity.age % 20 == 0 && entity.hasHome()) {
            // Ensure entity is >5 blocks away from home before teleporting
            BlockPos home = entity.getHome();
            BlockPos pos = entity.getBlockPos();
            double dist = Math.sqrt(home.getSquaredDistance(pos));

            if(dist >= 5) {
                return world.getEntitiesByClass(
                        ServerPlayerEntity.class,
                        new Box(entity.getBlockPos().add(-64, -64, -64), entity.getBlockPos().add(64, 64, 64)),
                        player -> true).isEmpty();
            }
        }

        return false;
    }

    @Override
    public void start() {
        if(entity.hasHome()) {
            entity.teleport(entity.getHome().getX() + 1, entity.getHome().getY(), entity.getHome().getZ() + 1);
        }
    }

    @Override
    public boolean shouldContinue() {
        return false;
    }
}
