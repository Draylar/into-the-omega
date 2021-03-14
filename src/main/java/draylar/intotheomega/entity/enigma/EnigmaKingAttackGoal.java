package draylar.intotheomega.entity.enigma;

import draylar.intotheomega.registry.OmegaParticles;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;

import java.util.EnumSet;

public class EnigmaKingAttackGoal extends Goal {

    private final EnigmaKingEntity king;
    private int ticks;
    private Path path;
    private long lastUpdateTime;
    private int updateCountdownTicks = 0;
    private double targetX;
    private double targetY;
    private double targetZ;
    private int ticksUntilNextAttack;

    // custom delayed-attack tracking
    private static final int MAX_ATTACK_TICKS = 32;
    private boolean isAttacking = false;
    private int attackTicks = 0;
    private LivingEntity target;

    public EnigmaKingAttackGoal(EnigmaKingEntity king) {
        super();
        this.king = king;
        setControls(EnumSet.of(Control.MOVE, Control.LOOK));
    }

    /*
     * The Enigma King can start a melee attack if a valid target is within
     *   3 blocks of the king.
     */
    @Override
    public boolean canStart() {
        long worldTime = king.world.getTime();

        // Do not attempt to attack within 20t of the last attempt.
        if (worldTime - lastUpdateTime < 20L) {
            return false;
        } else {
            this.lastUpdateTime = worldTime;
            LivingEntity target = king.getTarget();

            // validate target before processing
            if (target == null) {
                return false;
            } else if (!target.isAlive()) {
                return false;
            } else {
                this.path = king.getNavigation().findPathTo(target, 0);

                if (this.path != null) {
                    return true;
                } else {
                    return getSquaredMaxAttackDistance(target) >= king.squaredDistanceTo(target.getX(), target.getY(), target.getZ());
                }
            }
        }
    }

    @Override
    public boolean shouldContinue() {
        LivingEntity target = king.getTarget();

        if (target == null) {
            return false;
        } else if (!target.isAlive()) {
            return false;
        } else if (!king.isInWalkTargetRange(target.getBlockPos())) {
            return false;
        } else {
            return !(target instanceof PlayerEntity) || !target.isSpectator() && !((PlayerEntity)target).isCreative();
        }
    }

    @Override
    public void start() {
        king.getNavigation().startMovingAlong(path, 1);
        king.setAttacking(true);
        updateCountdownTicks = 0;
        ticksUntilNextAttack = 0;
    }

    @Override
    public void stop() {
        LivingEntity livingEntity = king.getTarget();
        if (!EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR.test(livingEntity)) {
            king.setTarget(null);
        }

        king.setAttacking(false);
        king.getNavigation().stop();
    }

    @Override
    public void tick() {
        assert king.getTarget() != null;

        LivingEntity target = king.getTarget();
        king.getLookControl().lookAt(target, 30.0F, 30.0F);
        double distanceToTarget = king.squaredDistanceTo(target.getX(), target.getY(), target.getZ());

        if(isAttacking) {
            attackTicks++;

            if(attackTicks == 35) {
                ((ServerWorld) king.world).playSoundFromEntity(null, king, SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, SoundCategory.HOSTILE, 0.25f, 0.0f);
            }

            // do damage mid-animation
            if(attackTicks == 40) {
                if (distanceToTarget <= getSquaredMaxAttackDistance(target)) {
                    king.tryAttack(target);
                }
            }

            // particles
            if(attackTicks > 30 && attackTicks < 40) {
                Vec3d offset = king.getRotationVector();
            }

            // end attack
            if(attackTicks >= 50) {
                attackTicks = 0;
                isAttacking = false;
                king.updateActivity(EnigmaKingEntity.ACTIVITY_NONE);
            }

            return;
        }

        updateCountdownTicks = Math.max(updateCountdownTicks - 1, 0);
        if ((king.getVisibilityCache().canSee(target)) && this.updateCountdownTicks <= 0 && (targetX == 0.0D && targetY == 0.0D && targetZ == 0.0D || target.squaredDistanceTo(targetX, targetY, targetZ) >= 1.0D || king.getRandom().nextFloat() < 0.05F)) {
            this.targetX = target.getX();
            this.targetY = target.getY();
            this.targetZ = target.getZ();
            this.updateCountdownTicks = 4 + king.getRandom().nextInt(7);
            if (distanceToTarget > 1024.0D) {
                this.updateCountdownTicks += 10;
            } else if (distanceToTarget > 256.0D) {
                this.updateCountdownTicks += 5;
            }

            if (!king.getNavigation().startMovingTo(target, 1)) {
                this.updateCountdownTicks += 15;
            }
        }

        this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack - 1, 0);
        this.attack(target, distanceToTarget);
    }

    private void attack(LivingEntity target, double distanceToTarget) {
        double attackRange = this.getSquaredMaxAttackDistance(target);

        if (distanceToTarget <= attackRange && this.ticksUntilNextAttack <= 0) {
            king.updateActivity(EnigmaKingEntity.ACTIVITY_ATTACKING);
            resetAttackCooldown();
            isAttacking = true;
        }
    }

    protected void resetAttackCooldown() {
        ticksUntilNextAttack = 5;
    }

    protected boolean canAttack() {
        return ticksUntilNextAttack <= 0;
    }

    public double getSquaredMaxAttackDistance(LivingEntity target) {
        return king.getWidth() * 3.0F * king.getWidth() * 3.0F + target.getWidth();
    }
}
