package draylar.intotheomega.entity.void_matrix.ai;

import draylar.intotheomega.api.particle.Particles;
import draylar.intotheomega.entity.void_matrix.VoidMatrixEntity;
import draylar.intotheomega.registry.OmegaParticles;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

import java.util.EnumSet;

public class KnockbackPulseGoal extends StageGoal {

    private static final int MAX_TICKS = 60;
    private int ticks = 0;
    private boolean circular = false;

    public KnockbackPulseGoal(VoidMatrixEntity vm, VoidMatrixEntity.Stage stage) {
        super(vm, stage);
        setControls(EnumSet.of(Control.MOVE));
    }

    @Override
    public boolean canStart() {
        if(vm.debugGoal(this)) {
            vm.consumeGoalDebug();
            return true;
        }

        return super.canStart() && vm.getTarget() != null && vm.getTarget().getPos().distanceTo(vm.getPos()) <= 8 && world.random.nextInt(10) == 0;
    }

    @Override
    public void start() {
        super.start();
        ticks = 0;
        world.sendEntityStatus(vm, VoidMatrixEntity.STOMP);
        circular = world.random.nextDouble() <= 0.5;

        // Send a Circular Indicator VFX particle to clients
        if(false) {
            Particles.sendTo(PlayerLookup.tracking(vm), OmegaParticles.VOID_MATRIX$SLAM_CIRCULAR_INDICATOR, vm.getX(), vm.getY(), vm.getZ(), 1);
        } else {
            Particles.sendTo(PlayerLookup.tracking(vm), OmegaParticles.VOID_MATRIX$SLAM_LENGTH_EXPAND_INDICATOR, vm.getX(), vm.getY(), vm.getZ(), 15, 75, 0, 0);
        }
    }

    @Override
    public void tick() {
        super.tick();
        ticks++;

        if(ticks > 20 && ticks < 65) {
            float progress = (ticks - 20) / 30f;
            world.playSound(null, vm.getX(), vm.getY(), vm.getZ(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.HOSTILE, 2.0f, progress * 2f);
        }

        if(ticks == 67) {
            // fx
            world.spawnParticles(OmegaParticles.MATRIX_EXPLOSION, vm.getX(), vm.getY(), vm.getZ(), 250, 6, 0, 6, 0);
            world.spawnParticles(ParticleTypes.EXPLOSION, vm.getX(), vm.getY(), vm.getZ(), 100, 6, 1, 6, 0);
            world.playSound(null, vm.getX(), vm.getY(), vm.getZ(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.HOSTILE, 2.0f, 0.5f);
            world.playSound(null, vm.getX(), vm.getY(), vm.getZ(), SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.HOSTILE, 2.0f, 1.0f);

            // kb nearby
            int radius = 14;
            world.getEntitiesByClass(LivingEntity.class, new Box(vm.getBlockPos().add(-radius, -5, -radius), vm.getBlockPos().add(radius, 5, radius)), entity -> entity != vm).forEach(entity -> {
                double distance = entity.getPos().distanceTo(vm.getPos());
                double power = 8 * (1.0 - distance / 28);
                Vec3d ndist = entity.getPos().subtract(vm.getPos()).normalize().multiply(power);
                entity.setVelocity(ndist.getX(), ndist.getY() + Math.max(0.5, power / 5.0), ndist.getZ());
                entity.velocityModified = true;

                // damage
                entity.damage(DamageSource.mob(vm), (float) power * 5);
            });
        }
    }

    @Override
    public boolean shouldContinue() {
        return ticks <= 90;
    }

    @Override
    public void stop() {
        super.stop();
        world.sendEntityStatus(vm, VoidMatrixEntity.STOP);
    }

    @Override
    public boolean shouldRunEveryTick() {
        return true;
    }
}
