package draylar.intotheomega.entity;

import draylar.intotheomega.entity.movement.FloatingMoveControl;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import java.util.List;
import java.util.Optional;

public class VoidFloaterEntity extends PathAwareEntity {

    public VoidFloaterEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
        moveControl = new FloatingMoveControl(this);
    }

    @Override
    public void initGoals() {
        goalSelector.add(0, new DescendWithPlayerGoal(this));
        goalSelector.add(1, new SaveFallingPlayerGoal(this));

        // Void Floaters only attack when provoked.
        targetSelector.add(0, new RevengeGoal(this).setGroupRevenge());
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        if(getPassengerList().isEmpty()) {
            player.startRiding(this);
            return ActionResult.PASS;
        }

        return super.interactMob(player, hand);
    }

    @Override
    public void updatePassengerPosition(Entity passenger) {
        passenger.updatePosition(getX(), getY() - 1.75f, getZ());
    }

    @Override
    public boolean hasNoGravity() {
        return true;
    }

    @Override
    public int computeFallDamage(float fallDistance, float damageMultiplier) {
        return 0;
    }

    private static class SaveFallingPlayerGoal extends Goal {

        private final VoidFloaterEntity floater;
        private final ServerWorld world;
        private PlayerEntity target;
        private int cooldown = 0;
        private int goalTicks = 0;

        public SaveFallingPlayerGoal(VoidFloaterEntity floater) {
            this.floater = floater;
            this.world = (ServerWorld) floater.getEntityWorld();
        }

        @Override
        public boolean canStart() {
            if(cooldown > 0) {
                cooldown--;
                return false;
            }

            // Every second, check to see if any nearby players are falling.
            if(floater.age % 20 == 0) {
                List<PlayerEntity> nearbyPlayers = world.getPlayers(TargetPredicate.DEFAULT, null, new Box(floater.getBlockPos()).expand(100.0f, 200.0f, 100.0f));

                // Ignore players who are not following.
                // Ignore players already on a floater/vehicle.
                // Ignore players using elytra devices.
                nearbyPlayers.removeIf(player -> {
                    return player.getVelocity().getY() >= 0 || player.getVehicle() != null || player.isFallFlying() || player.fallDistance <= 5.0f;
                });

                // If a player is found, lock on and go.
                Optional<PlayerEntity> found = nearbyPlayers.stream().findFirst();
                if(found.isPresent()) {
                    target = found.get();
                    return true;
                }
            }

            return false;
        }

        @Override
        public void tick() {
            goalTicks++;

            // Race underneath the player to catch them.
            Vec3d underneathPlayer = target.getPos().subtract(0, 5, 0);
            Vec3d towardsUnderneath = underneathPlayer.subtract(floater.getPos()).normalize();

            // If we're underneath the player, wait.
            if((int) floater.getX() != (int) target.getX() || (int) floater.getZ() != (int) target.getZ()) {
                floater.setVelocity(towardsUnderneath.multiply(5, 1, 5));
            }

            // If the player is nearby, catch them.
            if(target.distanceTo(floater) <= 5) {
                target.startRiding(floater);
            }
        }

        @Override
        public boolean shouldContinue() {
            boolean underTime = goalTicks <= 30 * 20;
            boolean validTarget = target != null && target.isAlive();
            boolean targetPositionValid = validTarget && !target.isOnGround();
            return underTime && validTarget && targetPositionValid && floater.getPassengerList().isEmpty();
        }

        @Override
        public void stop() {
            target = null;
            cooldown = 5 * 20;
            floater.setVelocity(0, 0.1, 0);
        }
    }

    public HitResult raycastDown(double maxDistance, float tickDelta, boolean includeFluids) {
        Vec3d vec3d = this.getCameraPosVec(tickDelta);
        Vec3d vec3d2 = new Vec3d(0, -1, 0);
        Vec3d vec3d3 = vec3d.add(vec3d2.x * maxDistance, vec3d2.y * maxDistance, vec3d2.z * maxDistance);
        return this.world.raycast(new RaycastContext(vec3d, vec3d3, RaycastContext.ShapeType.OUTLINE, includeFluids ? RaycastContext.FluidHandling.ANY : RaycastContext.FluidHandling.NONE, this));
    }

    private static class DescendWithPlayerGoal extends Goal {

        private final VoidFloaterEntity floater;
        private final ServerWorld world;
        private boolean isDone = false;

        public DescendWithPlayerGoal(VoidFloaterEntity floater) {
            this.floater = floater;
            this.world = (ServerWorld) floater.getEntityWorld();
        }

        @Override
        public boolean canStart() {
            return floater.getPassengerList().stream().anyMatch(entity -> entity instanceof PlayerEntity);
        }

        @Override
        public void tick() {
            // slowly descend until we find a landing place
            floater.setVelocity(0, -0.2, 0);

            // If we're ~4 blocks above land, drop the player.
            HitResult underneath = floater.raycastDown(4, 0, false);
            if(underneath instanceof BlockHitResult) {
                if(!world.getBlockState(((BlockHitResult) underneath).getBlockPos()).isAir()) {
                    isDone = true;
                }
            }
        }

        @Override
        public boolean shouldContinue() {
            return !isDone && !floater.getPassengerList().isEmpty();
        }

        @Override
        public void stop() {
            isDone = false;
            floater.removeAllPassengers();
            floater.setVelocity(0, 0.1, 0);
        }
    }
}
