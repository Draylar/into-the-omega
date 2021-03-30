package draylar.intotheomega.entity.void_matrix.ai;

import draylar.intotheomega.entity.void_matrix.VoidMatrixBeamEntity;
import draylar.intotheomega.entity.void_matrix.VoidMatrixEntity;
import draylar.intotheomega.registry.OmegaEntities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;

public class BeamAttackGoal extends StageGoal {

    private final int speed;

    public BeamAttackGoal(VoidMatrixEntity entity, int speed, VoidMatrixEntity.Stage stage) {
        super(entity, stage);
        this.speed = speed;
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void tick() {
        // Add new attacks when appropriate
        if(vm.age % speed == 0) {
            addRandomAttack();
        }
    }

    public void addRandomAttack() {
        PlayerEntity target = locateRandomNearbyPlayer();

        if(target != null) {
            int rand = world.random.nextInt(10);

            // Attack directly on top of the player.
            if(rand <= 3) {
                Vec3d pos = target.getPos();
                VoidMatrixBeamEntity beam = new VoidMatrixBeamEntity(OmegaEntities.VOID_MATRIX_BEAM, world);
                beam.updatePosition(pos.getX(), pos.getY(), pos.getZ());
                world.spawnEntity(beam);
            }

            // Attack in the direction the player is walking.
            // Weight: 2/10
            else if(rand <= 5 && target instanceof ServerPlayerEntity) {
                double yaw = Math.toRadians(target.headYaw);
                Vec3d look = new Vec3d(Math.sin(yaw), 0, Math.cos(yaw));
                Vec3d pos = target.getPos();
                Vec3d beamPos = pos.add(look.multiply(-5, 1, 5));

                int time = vm.age + world.random.nextInt(40);
                VoidMatrixBeamEntity beam = new VoidMatrixBeamEntity(OmegaEntities.VOID_MATRIX_BEAM, world);
                beam.updatePosition(pos.getX(), pos.getY(), pos.getZ());
                world.spawnEntity(beam);
            }

            // Attack close to the player.
            // Weight: 2/10
            else if(rand <= 8) {
                Vec3d pos = target.getPos();
                pos = pos.add(world.random.nextInt(3) * randomPolarity(world.random), 0, world.random.nextInt(3) * randomPolarity(world.random));
                int time = vm.age + world.random.nextInt(40);
                VoidMatrixBeamEntity beam = new VoidMatrixBeamEntity(OmegaEntities.VOID_MATRIX_BEAM, world);
                beam.updatePosition(pos.getX(), pos.getY(), pos.getZ());
                world.spawnEntity(beam);
            }

            // Attack at a random position around the Matrix Entity.
            // Weight: 1/10
            else {
                // todo: this will probably only go for corners
                Vec3d pos = vm.getPos();
                pos = pos.add(world.random.nextInt(7) * randomPolarity(world.random), 0, world.random.nextInt(7) * randomPolarity(world.random));
                int time = vm.age + world.random.nextInt(40);
                VoidMatrixBeamEntity beam = new VoidMatrixBeamEntity(OmegaEntities.VOID_MATRIX_BEAM, world);
                beam.updatePosition(pos.getX(), pos.getY(), pos.getZ());
                world.spawnEntity(beam);
            }
        }
    }
}
