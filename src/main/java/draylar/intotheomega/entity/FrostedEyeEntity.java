package draylar.intotheomega.entity;

import draylar.intotheomega.entity.ai.LookAtTargetGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class FrostedEyeEntity extends MobEntity {

    public FrostedEyeEntity(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void initGoals() {
        super.initGoals();

        goalSelector.add(0, new LookAtTargetGoal(this));
        targetSelector.add(0, new FollowTargetGoal<>(this, PlayerEntity.class, true));
    }

    @Override
    public void checkDespawn() {

    }

    @Override
    public boolean hasNoGravity() {
        return true;
    }
}
