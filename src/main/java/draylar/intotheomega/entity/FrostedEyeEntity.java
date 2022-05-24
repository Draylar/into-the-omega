package draylar.intotheomega.entity;

import draylar.intotheomega.entity.ai.LookAtTargetGoal;
import draylar.intotheomega.registry.OmegaParticles;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.Random;

public class FrostedEyeEntity extends MobEntity implements IAnimatable {

    private final AnimationFactory factory = new AnimationFactory(this);

    public FrostedEyeEntity(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void initGoals() {
        super.initGoals();

        goalSelector.add(0, new LookAtTargetGoal(this));
        targetSelector.add(0, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
    }

    public static boolean canSpawnAt(EntityType<FrostedEyeEntity> type, ServerWorldAccess serverWorldAccess, SpawnReason spawnReason, BlockPos pos, Random random) {
        boolean validLight = serverWorldAccess.getDifficulty() != Difficulty.PEACEFUL && HostileEntity.isSpawnDark(serverWorldAccess, pos, random) && canMobSpawn(type, serverWorldAccess, spawnReason, pos, random);
        boolean validPosition = pos.getY() >= 170;
        return validLight && validPosition;
    }

    @Override
    public void tick() {
        super.tick();

        if(world.isClient) {
            world.addParticle(
                    OmegaParticles.ICE_FLAKE,
                    this.getParticleX(0.5D),
                    this.getRandomBodyY() - 0.25D,
                    this.getParticleZ(0.5D),
                    (this.random.nextDouble() - 0.5D) * 2.0D,
                    -this.random.nextDouble(),
                    (this.random.nextDouble() - 0.5D) * 2.0D
            );
        }
    }

    @Override
    public void checkDespawn() {

    }

    @Override
    public boolean hasNoGravity() {
        return true;
    }

    @Override
    public void registerControllers(AnimationData animationData) {

    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}
