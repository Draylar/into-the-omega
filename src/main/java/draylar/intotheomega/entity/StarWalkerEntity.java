package draylar.intotheomega.entity;

import draylar.intotheomega.entity.ai.HumanoidAttackGoal;
import draylar.intotheomega.registry.OmegaParticles;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class StarWalkerEntity extends HostileEntity {

    public StarWalkerEntity(EntityType<? extends HostileEntity> type, World world) {
        super(type, world);
    }

    @Override
    protected void initGoals() {
        goalSelector.add(2, new HumanoidAttackGoal(this, 1.0, false));
        goalSelector.add(7, new WanderAroundFarGoal(this, 1.0));
        goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
        goalSelector.add(8, new LookAroundGoal(this));
        targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
    }

    public static DefaultAttributeContainer.Builder createStarWalkerAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 64.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.75f)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 10.0)
                .add(EntityAttributes.GENERIC_ARMOR, 2.0);
    }

    @Override
    public void tick() {
        super.tick();

        if(world.isClient) {
            world.addParticle(OmegaParticles.TINY_STAR, getX() + Math.sin(age), getY() + Math.sin(age * 5), getZ() + Math.cos(age), 0, 0, 0);
            world.addParticle(OmegaParticles.STARLIGHT, getX() + Math.sin(age), getY() + Math.sin(age * 5), getZ() + Math.cos(age), 0, 0, 0);
        }
    }

    @Nullable
    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        initEquipment(difficulty);
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    public void dropEquipment(DamageSource source, int lootingMultiplier, boolean allowDrops) {

    }

    @Override
    public SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_ENDERMAN_AMBIENT;
    }

    @Override
    public SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_ENDERMAN_HURT;
    }

    @Override
    public SoundEvent getDeathSound() {
        return SoundEvents.BLOCK_END_PORTAL_SPAWN;
    }
}
