package draylar.intotheomega.entity.void_matrix.ai;

import draylar.intotheomega.entity.void_matrix.VoidMatrixEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.world.GameMode;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public abstract class StageGoal extends Goal {

    protected final VoidMatrixEntity vm;
    protected final ServerWorld world;
    protected final VoidMatrixEntity.Stage stage;
    private boolean running = false;

    public StageGoal(VoidMatrixEntity vm, VoidMatrixEntity.Stage stage) {
        this.vm = vm;
        this.world = (ServerWorld) vm.world;
        this.stage = stage;
    }

    @Override
    public void start() {
        running = true;
    }

    @Override
    public boolean canStart() {
        boolean isValidHealth = vm.getHealth() <= stage.getMaxHealth(vm) && vm.getHealth() > stage.getMinHealth(vm);

        // Only attempt to search for targets if the health range is met
        if(isValidHealth) {
            List<ServerPlayerEntity> nearbyTargets = world.getEntitiesByClass(
                    ServerPlayerEntity.class,
                    new Box(vm.getBlockPos().add(-64, -64, -64), vm.getBlockPos().add(64, 64, 64)),
                    player -> !player.interactionManager.getGameMode().equals(GameMode.CREATIVE) && !player.interactionManager.getGameMode().equals(GameMode.SPECTATOR));

            return !nearbyTargets.isEmpty() && !running;
        }

        return false;
    }

    @Override
    public void stop() {
        running = false;
    }

    @Override
    public boolean shouldContinue() {
        return running;
    }

    @Nullable
    public PlayerEntity locateRandomNearbyPlayer() {
        List<ServerPlayerEntity> nearbyTargets = world.getEntitiesByClass(
                ServerPlayerEntity.class,
                new Box(vm.getBlockPos().add(-64, -64, -64), vm.getBlockPos().add(64, 64, 64)),
                player -> !player.interactionManager.getGameMode().equals(GameMode.CREATIVE) && !player.interactionManager.getGameMode().equals(GameMode.SPECTATOR));

        if(nearbyTargets.isEmpty()) {
            return null;
        } else {
            return nearbyTargets.get(world.random.nextInt(nearbyTargets.size()));
        }
    }

    /**
     * Returns 1 or -1 randomly.
     */
    public int randomPolarity(Random random) {
        int r = random.nextInt(2);
        return r == 0 ? -1 : 1;
    }
}
