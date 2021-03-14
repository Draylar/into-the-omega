package draylar.intotheomega.entity.enigma;

import draylar.intotheomega.registry.OmegaParticles;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

import java.util.EnumSet;

public class EnigmaKingSmashGoal extends Goal {

    public static final double SMASH_ANIMATION_LENGTH = 4.5;
    private static final int ANIMATION_TIME_TICKS = (int) (SMASH_ANIMATION_LENGTH * 20) + 20 + 20;
    private final EnigmaKingEntity king;
    private int ticks = 0;

    public EnigmaKingSmashGoal(EnigmaKingEntity king) {
        this.king = king;
        setControls(EnumSet.of(Control.MOVE, Control.JUMP));
    }

    @Override
    public boolean canStart() {
        boolean rand = king.world.random.nextInt(100) == 0;
        boolean notRunning = ticks == 0;
        boolean hasTarget = king.getTarget() != null;

        return rand && notRunning && hasTarget;
    }

    @Override
    public void start() {
        king.updateActivity(EnigmaKingEntity.ACTIVITY_SMASHING);
        king.lookAtEntity(king.getTarget(), 1000, 1000);

        // particles
        for(int i = 0; i < 250; i++) {
            ((ServerWorld) king.getEntityWorld()).spawnParticles(
                    OmegaParticles.SMALL_OMEGA_BURST,
                    king.getX() + Math.sin(i),
                    king.getY() + 0.1,
                    king.getZ() + Math.cos(i),
                    1,
                    0,
                    0,
                    0,
                    0
            );
        }
    }

    @Override
    public void tick() {
        ticks++;

        if(ticks == 35) {
            if(king.getTarget() != null) {
                double distance = king.distanceTo(king.getTarget());
                Vec3d towardsTarget = king.getTarget().getPos().subtract(king.getPos()).normalize().multiply(distance / 5).add(0, .75f, 0);
                king.setVelocity(towardsTarget);
            }
        }

        if(ticks == 60) {
            // spawn particles
            ((ServerWorld) king.world).spawnParticles(ParticleTypes.EXPLOSION_EMITTER, king.getX(), king.getY() - 1, king.getZ(), 3, 0, 0, 0, 1);
            ((ServerWorld) king.world).spawnParticles(OmegaParticles.SMALL_OMEGA_BURST, king.getX(), king.getY(), king.getZ(), 150, 0, 0, 0, 0.2);

            // damage entities
            king.world.getOtherEntities(king, new Box(king.getBlockPos().add(-3, -3, -3), king.getBlockPos().add(3, 3, 3)), found -> {
                return true;
            }).forEach(entity -> {
                entity.damage(DamageSource.mob(king), 10);
            });

            // sound
            ((ServerWorld) king.world).playSoundFromEntity(null, king, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.HOSTILE, 1.0f, 1.0f);
        }
    }

    @Override
    public boolean shouldContinue() {
        return ticks < ANIMATION_TIME_TICKS;
    }

    @Override
    public void stop() {
        king.updateActivity(EnigmaKingEntity.ACTIVITY_NONE);
        ticks = 0;
    }
}
