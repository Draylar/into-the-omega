package draylar.intotheomega.entity.void_matrix.ai;

import draylar.intotheomega.entity.matrite.MatriteEntity;
import draylar.intotheomega.entity.void_matrix.VoidMatrixEntity;
import net.minecraft.entity.player.PlayerEntity;

import java.util.ArrayList;
import java.util.List;

public class MatriteProjectileAttackGoal extends StageGoal {

    private final List<Integer> queuedAttacks = new ArrayList<>();
    private int cooldown = 0;

    public MatriteProjectileAttackGoal(VoidMatrixEntity entity, VoidMatrixEntity.Stage stage) {
        super(entity, stage);
    }

    @Override
    public void start() {
        super.start();

        if(cooldown <= 0 && queuedAttacks.isEmpty()) {
            // Queue up 10-25 attacks
            int attacks = 10 + world.random.nextInt(15);
            int currentTime = 0;

            // Each attack appears 5 ticks after the previous one.
            for (int i = 0; i < attacks; i++) {
                int attackTime = 5;
                queuedAttacks.add(vm.age + currentTime + attackTime);
                currentTime += attackTime;
            }

            cooldown = 20 * 15;
        }
    }

    @Override
    public void tick() {
        super.tick();

        cooldown = Math.max(0, cooldown - 1);
        List<Integer> toRemove = new ArrayList<>();

        queuedAttacks.forEach(time -> {

            // Entity age has passed the scheduled time for this attack. Trigger it now.
            if(vm.age >= time) {
                toRemove.add(time);

                // TODO: the following position-selecting code only finds positions in the 4 corners around the entity.

                // determine base position of new attack around source entity
                int randX = 4 + world.random.nextInt(3);
                int randY = 5 + world.random.nextInt(5);
                int randZ = 4 + world.random.nextInt(3);

                // apply random polarity to x and z
                randX *= randomPolarity(world.random);
                randZ *= randomPolarity(world.random);

                float finalX = (float) vm.getX() + randX;
                float finalY = (float) vm.getY() + randY;
                float finalZ = (float) vm.getZ() + randZ;

                // Find a target nearby to shoot projectiles at
                PlayerEntity target = locateRandomNearbyPlayer();
                if(target != null) {
                    MatriteEntity matrite = new MatriteEntity(world);
                    matrite.setTarget(target, 20 * 4 + world.random.nextInt(2));
                    matrite.setSource(vm);
                    matrite.updatePosition(finalX, finalY, finalZ);
                    matrite.setOwner(vm);
                    world.spawnEntity(matrite);
                }
            }
        });

        queuedAttacks.removeAll(toRemove);

        // If there are no more projectiles to shoot, end this task.
        if(queuedAttacks.isEmpty()) {
            stop();
        }
    }
}
