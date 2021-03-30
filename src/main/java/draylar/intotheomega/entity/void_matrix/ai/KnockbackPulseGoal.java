package draylar.intotheomega.entity.void_matrix.ai;

import draylar.intotheomega.entity.void_matrix.VoidMatrixEntity;
import draylar.intotheomega.registry.OmegaParticles;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

import java.util.EnumSet;

public class KnockbackPulseGoal extends StageGoal {

    private static final int MAX_TICKS = 60;
    private int ticks = 0;

    public KnockbackPulseGoal(VoidMatrixEntity vm, VoidMatrixEntity.Stage stage) {
        super(vm, stage);
        setControls(EnumSet.of(Control.MOVE));
    }

    @Override
    public boolean canStart() {
        return super.canStart() && vm.getTarget() != null && vm.getTarget().getPos().distanceTo(vm.getPos()) <= 8 && world.random.nextInt(10) == 0;
    }

    @Override
    public void start() {
        super.start();
        ticks = 0;
        world.sendEntityStatus(vm, VoidMatrixEntity.STOMP);
    }

    @Override
    public void tick() {
        super.tick();
        ticks++;

        if(ticks > 20 && ticks < 50) {
            float progress = (ticks - 20) / 30f;
            world.playSound(null, vm.getX(), vm.getY(), vm.getZ(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.HOSTILE, 2.0f, progress * 2f);
        }

        if(ticks == 50) {
            // fx
            world.spawnParticles(OmegaParticles.MATRIX_EXPLOSION, vm.getX(), vm.getY(), vm.getZ(), 250, 3, 0, 3, 0);
            world.playSound(null, vm.getX(), vm.getY(), vm.getZ(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.HOSTILE, 2.0f, 0.5f);
            world.playSound(null, vm.getX(), vm.getY(), vm.getZ(), SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.HOSTILE, 2.0f, 1.0f);

            // kb nearby
            world.getEntitiesByClass(LivingEntity.class, new Box(vm.getBlockPos().add(-7, -5, -7), vm.getBlockPos().add(7, 5, 7)), entity -> entity != vm).forEach(entity -> {
                double distance = entity.getPos().distanceTo(vm.getPos());
                double power = Math.max(0, -1.25 * Math.sqrt(distance) + 4);
                Vec3d ndist = entity.getPos().subtract(vm.getPos()).normalize().multiply(3);
                entity.setVelocity(ndist.getX(), ndist.getY() + .5, ndist.getZ());
                entity.velocityModified = true;

                // damage
                entity.damage(DamageSource.mob(vm), (float) power * 10);
            });
        }
    }

    @Override
    public boolean shouldContinue() {
        return ticks <= MAX_TICKS;
    }

    @Override
    public void stop() {
        super.stop();
        world.sendEntityStatus(vm, VoidMatrixEntity.STOP);
    }
}
