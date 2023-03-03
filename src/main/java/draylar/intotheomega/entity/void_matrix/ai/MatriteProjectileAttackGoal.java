package draylar.intotheomega.entity.void_matrix.ai;

import draylar.intotheomega.api.PositionUtils;
import draylar.intotheomega.api.RandomUtils;
import draylar.intotheomega.entity.matrite.MatriteEntity;
import draylar.intotheomega.entity.void_matrix.VoidMatrixEntity;
import draylar.intotheomega.entity.void_matrix.indicator.MatriteTargetIndicatorEntity;
import draylar.intotheomega.registry.OmegaEntities;
import draylar.intotheomega.registry.OmegaParticles;
import draylar.intotheomega.util.ParticleUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

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
            int attacks = 30 + world.random.nextInt(20);
            int currentTime = 0;

            // Each attack appears 5 ticks after the previous one.
            for (int i = 0; i < attacks; i++) {
                int attackTime = 5;
                queuedAttacks.add(vm.age + currentTime + attackTime);
                currentTime += attackTime;
            }
        }
    }

    @Override
    public boolean canStart() {
        cooldown = Math.max(0, cooldown - 1);
        return cooldown == 0 && super.canStart();
    }

    @Override
    public void stop() {
        super.stop();
        cooldown = 20 * 5;
    }

    @Override
    public void tick() {
        super.tick();

        List<Integer> toRemove = new ArrayList<>();

        queuedAttacks.forEach(time -> {

            // Entity age has passed the scheduled time for this attack. Trigger it now.
            if(vm.age >= time) {
                toRemove.add(time);

                for (int i = 0; i < 5; i++) {
                    if(trySpawnMatriteProjectile()) {
                        break;
                    }
                }
            }
        });

        queuedAttacks.removeAll(toRemove);

        // If there are no more projectiles to shoot, end this task.
        if(queuedAttacks.isEmpty()) {
            stop();
        }
    }

    private boolean trySpawnMatriteProjectile() {
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

            // Summon Matrite target around player
            MatriteTargetIndicatorEntity targetIndicator = new MatriteTargetIndicatorEntity(OmegaEntities.MATRITE_TARGET_INDICATOR, world);
            @Nullable BlockPos position = findRandomGroundTarget(target);
            if(position == null) {
                return false;
            }

            // don't allow us to spawn targets near an existing target
            if(!world.getEntitiesByClass(MatriteTargetIndicatorEntity.class, Box.of(new Vec3d(position.getX(), position.getY(), position.getZ()), 6, 2, 6), found -> true).isEmpty()) {
                return false;
            }

            // don't position on top of VM
            // don't spawn position on top of VM
            if(position.isWithinDistance(vm.getPos(), 10.0f)) {
                return false;
            }

            targetIndicator.updatePosition(position.getX(), position.getY(), position.getZ());
            world.spawnEntity(targetIndicator);

            MatriteEntity matrite = new MatriteEntity(world);
            matrite.setTarget(targetIndicator, 0);
            matrite.setSource(vm);
            matrite.updatePosition(finalX, finalY, finalZ);
            matrite.setOwner(vm);
            world.spawnEntity(matrite);
        }

        return true;
    }

    @Nullable
    private BlockPos findRandomGroundTarget(PlayerEntity target) {
        if(world.random.nextDouble() <= 0.1) {
            double x = target.getX() + RandomUtils.range(world.random, 3.0f);
            double z = target.getZ() + RandomUtils.range(world.random, 3.0f);
            return PositionUtils.findClosestGroundPosition(world, new BlockPos(x, target.getY(), z), 2, 3);
        } else {
            double x = target.getX() + RandomUtils.range(world.random, 16.0f);
            double z = target.getZ() + RandomUtils.range(world.random, 16.0f);
            return PositionUtils.findClosestGroundPosition(world, new BlockPos(x, target.getY(), z), 3, 4);
        }
    }
}
