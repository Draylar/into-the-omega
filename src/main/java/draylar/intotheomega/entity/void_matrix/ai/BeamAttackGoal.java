package draylar.intotheomega.entity.void_matrix.ai;

import draylar.intotheomega.client.particle.OmegaParticle;
import draylar.intotheomega.entity.void_matrix.VoidMatrixEntity;
import draylar.intotheomega.registry.OmegaParticles;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeamAttackGoal extends StageGoal {

    private final Map<Integer, BeamAttack> activeAttacks = new HashMap<>();

    public BeamAttackGoal(VoidMatrixEntity entity, VoidMatrixEntity.Stage stage) {
        super(entity, stage);
    }

    @Override
    public void start() {
        super.start();
        activeAttacks.clear();
    }

    @Override
    public void tick() {
        List<Integer> toRemove = new ArrayList<>();

        // Tick each running attack
        activeAttacks.forEach((time, beamAttack) -> {
            if (time <= vm.age) {
                beamAttack.tick();
            }

            if (beamAttack.isDone()) {
                toRemove.add(time);
            }
        });

        // Add new attacks when appropriate
        if(vm.age % 50 == 0) {
            for(int i = 0; i < 5; i++) {
                addRandomAttack();
            }
        }

        // Remove finished attacks
        toRemove.forEach(time -> activeAttacks.remove(time));

        if(!stage.equals(vm.getStage())) {
            stop();
        }
    }

    // Attacks can spawn at a random position around the Matrix,
       // at a position close to the player,
       // or directly on top of the player.
    public void addRandomAttack() {
        PlayerEntity target = locateRandomNearbyPlayer();

        if(target != null) {
            int rand = world.random.nextInt(10);

            // Attack directly on top of the player.
            if(rand <= 3) {
                Vec3d pos = target.getPos();
                int time = vm.age + world.random.nextInt(40);
                BeamAttack attack = new BeamAttack(world, vm, pos);
                activeAttacks.put(time, attack);
            }

            // Attack in the direction the player is walking.
            // Weight: 2/10
            else if(rand <= 5 && target instanceof ServerPlayerEntity) {
                double yaw = Math.toRadians(target.headYaw);
                Vec3d look = new Vec3d(Math.sin(yaw), 0, Math.cos(yaw));
                Vec3d pos = target.getPos();
                Vec3d beamPos = pos.add(look.multiply(-5, 1, 5));

                int time = vm.age + world.random.nextInt(40);
                BeamAttack attack = new BeamAttack(world, vm, beamPos);
                activeAttacks.put(time, attack);
            }

            // Attack close to the player.
            // Weight: 2/10
            else if(rand <= 8) {
                Vec3d pos = target.getPos();
                pos = pos.add(world.random.nextInt(3) * randomPolarity(world.random), 0, world.random.nextInt(3) * randomPolarity(world.random));
                int time = vm.age + world.random.nextInt(40);
                BeamAttack attack = new BeamAttack(world, vm, pos);
                activeAttacks.put(time, attack);
            }

            // Attack at a random position around the Matrix Entity.
            // Weight: 1/10
            else {
                // todo: this will probably only go for corners
                Vec3d pos = vm.getPos();
                pos = pos.add(world.random.nextInt(7) * randomPolarity(world.random), 0, world.random.nextInt(7) * randomPolarity(world.random));
                int time = vm.age + world.random.nextInt(40);
                BeamAttack attack = new BeamAttack(world, vm, pos);
                activeAttacks.put(time, attack);
            }
        }
    }

    public static class BeamAttack {

        private final static int total = 20 * 2;
        private final static int prep = 20;

        private final ServerWorld world;
        private final LivingEntity source;
        private final Vec3d pos;
        private int ticksAlive = 0;

        public BeamAttack(ServerWorld world, LivingEntity source, Vec3d pos) {
            this.world = world;
            this.source = source;
            this.pos = pos;
        }

        public void tick() {
            // Spawn small beam in first 3/5ths, then attack in final 2/5th
            if(ticksAlive % 5 == 0) {
                if (ticksAlive < prep) {
                    spawnParticleBeam(ParticleTypes.CRIT);
                } else {
                    spawnParticleBeam(OmegaParticles.DARK);
                    spawnParticleBeam(OmegaParticles.OMEGA_PARTICLE);
                }
            }

            // On the final tick of the prep phase, play explosion sounds at the target location
            if(ticksAlive == prep) {
                world.playSound(null, pos.x, pos.y, pos.z, SoundEvents.ENTITY_DRAGON_FIREBALL_EXPLODE, SoundCategory.HOSTILE, 1, 2f);
            }

            // In the time after the preparation phase, damage players standing in the beam
            if(ticksAlive > prep) {
                if(ticksAlive % 5 == 0) {
                    world.getEntitiesByClass(LivingEntity.class, new Box(new BlockPos(pos.add(-2f + .5, -1, -2f + .5)), new BlockPos(pos.add(2f + .5, 10, 2f + .5))), entity -> {
                        return !(entity instanceof VoidMatrixEntity);
                    }).forEach(entity -> {
                        entity.damage(DamageSource.MAGIC, 5);
                    });
                }
            }

            ticksAlive++;
        }

        private void spawnParticleBeam(ParticleEffect type) {
            for(double y = pos.getY() - 15; y < pos.getY() + 25; y += 1) {
                for (int x = -1; x <= 1; x++) {
                    for (int z = -1; z <= 1; z++) {
                        world.spawnParticles(type, pos.getX() + x, y, pos.getZ() + z, 1, 0, 0, 0, .1);
                    }
                }
            }
        }

        public boolean isDone() {
            return ticksAlive > total;
        }
    }
}
