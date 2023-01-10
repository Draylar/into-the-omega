package draylar.intotheomega.entity.void_matrix.ai;

import draylar.intotheomega.entity.void_matrix.VoidMatrixEntity;
import draylar.intotheomega.mixin.access.EntityAccessor;
import draylar.intotheomega.registry.OmegaDamageSources;
import draylar.intotheomega.registry.OmegaParticles;
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

public class LaserSpinGoal extends StageGoal {

    private long lastLaser = 0;
    private boolean inPosition = false;

    public LaserSpinGoal(VoidMatrixEntity vm, VoidMatrixEntity.Stage stage) {
        super(vm, stage);
        setControls(EnumSet.of(Control.MOVE, Control.LOOK, Control.JUMP));
    }

    @Override
    public boolean canStart() {
        return super.canStart() && vm.getHome() != null && world.random.nextInt(100) == 0 && !vm.isFiringLaser() && System.currentTimeMillis() - lastLaser > 10_000;
    }

    @Override
    public void start() {
        super.start();
        vm.setLaserTicks(0);

        System.out.println("START");
        BlockPos home = vm.getHome();
        vm.getMoveControl().moveTo(home.getX(), home.getY() + 1, home.getZ(), 3);
    }

    @Override
    public boolean shouldContinue() {
        return super.shouldContinue() && vm.getLaserTicks() <= VoidMatrixEntity.MAX_LASER_TICKS;
    }

    @Override
    public void tick() {
        super.tick();

        if(!inPosition && !vm.getMoveControl().isMoving() || vm.getBlockPos().equals(vm.getHome().up())) {
            vm.setFiringLaser(true);
            inPosition = true;
        }

        vm.prevPitch = 0;
        vm.setPitch(0);

        if(inPosition) {
            vm.setLaserTicks(vm.getLaserTicks() + 1);
            world.playSound(null, vm.getX(), vm.getY(), vm.getZ(), SoundEvents.BLOCK_CONDUIT_ACTIVATE, SoundCategory.HOSTILE, 3, 1.0f);

            if (vm.getLaserTicks() > 35) {
                damage();
            }

            vm.bodyYaw += 1.7;
            vm.setYaw(vm.bodyYaw);
        }
    }

    public void damage() {
        // custom raycast due to the void matrix pitch not being in line with the laser
        // TODO: we can remove this
        Vec3d cameraPos = vm.getCameraPosVec(0);
        Vec3d rotationVec = ((EntityAccessor) vm).callGetRotationVector(vm.getPitch(0) + 0, vm.getYaw(0));
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
            world.getEntitiesByClass(LivingEntity.class, new Box(cur.add(-1, -1, -1), cur.add(1, 1, 1)), entity -> !hitEntities.contains(entity.getUuid()) && entity != vm).forEach(entity -> {
                entity.damage(OmegaDamageSources.createMatrixLaser(vm), 15);
                world.spawnParticles(OmegaParticles.MATRIX_EXPLOSION, entity.getX(), entity.getY() + .5, entity.getZ(), 25, 0, 0, 0, .1);
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
        inPosition = false;
    }
}
