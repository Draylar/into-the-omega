package draylar.intotheomega.entity.void_matrix.ai;

import draylar.intotheomega.entity.void_matrix.VoidMatrixEntity;
import draylar.intotheomega.mixin.access.EntityAccessor;
import draylar.intotheomega.registry.OmegaDamageSources;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.UUID;

public class FireLaserGoal extends StageGoal {

    private long lastLaser = 0;

    public FireLaserGoal(VoidMatrixEntity vm, VoidMatrixEntity.Stage stage) {
        super(vm, stage);
        setControls(EnumSet.of(Control.MOVE));
    }

    @Override
    public boolean canStart() {
        return super.canStart() && world.random.nextInt(200) == 0 && !vm.isFiringLaser() && System.currentTimeMillis() - lastLaser > 5_000;
    }

    @Override
    public void start() {
        super.start();
        vm.setFiringLaser(true);
        vm.setLaserTicks(0);
    }

    @Override
    public boolean shouldContinue() {
        return super.shouldContinue() && vm.getLaserTicks() <= VoidMatrixEntity.MAX_LASER_TICKS;
    }

    @Override
    public void tick() {
        super.tick();
        vm.setLaserTicks(vm.getLaserTicks() + 1);
        world.playSound(null, vm.getX(), vm.getY(), vm.getZ(), SoundEvents.BLOCK_CONDUIT_ACTIVATE, SoundCategory.HOSTILE, 3, 1.0f);

        if(vm.getLaserTicks() > 35) {
            damage();
        }
    }

    public void damage() {
        // custom raycast due to the void matrix pitch not being in line with the laser
        Vec3d cameraPos = vm.getCameraPosVec(0);
        Vec3d rotationVec = ((EntityAccessor) vm).callGetRotationVector(vm.getPitch(0) + 3, vm.getYaw(0));
        Vec3d expanded = cameraPos.add(rotationVec.x * 100, rotationVec.y * 100, rotationVec.z * 100);
        HitResult res = this.world.raycast(new RaycastContext(cameraPos, expanded, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.ANY, vm));

        double mult = .25;

        // Calculate positioning data
        Vec3d origin = vm.getPos().add(0, 1.5, 0);
        Vec3d target = res.getPos();
        double diff = Math.sqrt(origin.squaredDistanceTo(target)); // distance in blocks from origin to target
        Vec3d per = target.subtract(origin).multiply(1 / diff).multiply(mult); // position to increment per each block
        Vec3d cur = new Vec3d(origin.getX(), origin.getY(), origin.getZ()); // current position

        // only hit each entity once
        List<UUID> hitEntities = new ArrayList<>();

        for(double i = 0; i < diff; i += mult) {
            cur = cur.add(per);

            // Damage entities
            world.getEntitiesByClass(LivingEntity.class, new Box(cur.add(-.5, -.5, -.5), cur.add(.5, .5, .5)), entity -> !hitEntities.contains(entity.getUuid()) && entity != vm).forEach(entity -> {
                entity.damage(OmegaDamageSources.createMatrixLaser(vm), 5);
//
//                    world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), GOBSounds.KATANA_SWOOP, SoundCategory.PLAYERS, 2F, 1.5F + (float) world.random.nextDouble() * .5f);
//                    world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, 0.5F, 1.5F + (float) world.random.nextDouble() * .5f);
                world.spawnParticles(ParticleTypes.PORTAL, entity.getX(), entity.getY() + .5, entity.getZ(), 25, 0, 0, 0, .1);
                hitEntities.add(entity.getUuid());
            });
        }

        // particles at end
        if(res instanceof BlockHitResult) {
            BlockHitResult bhr = (BlockHitResult) res;
            BlockPos blockPos = bhr.getBlockPos();
            BlockState state = world.getBlockState(blockPos);

            if(!state.isAir()) {
                world.spawnParticles(ParticleTypes.EXPLOSION, target.getX(), target.getY() + .5, target.getZ(), 5, 0, 0, 0, 1);
                world.playSound(null, target.getX(), target.getY(), target.getZ(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.HOSTILE, 1.0f, 2.0f);
            }
        }
    }

    @Override
    public void stop() {
        super.stop();
        vm.setFiringLaser(false);
        vm.setLaserTicks(0);
        lastLaser = System.currentTimeMillis();
    }
}
