package draylar.intotheomega.entity;

import draylar.intotheomega.api.docs.Description;
import draylar.intotheomega.entity.ai.LookAtTargetGoal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

@Description("This Ghoul was perfectly content with you strolling around the End until you summoned it through an interdimensional supernova. Good luck!")
public class NovaGhoulEntity extends PathAwareEntity implements IAnimatable {

    private final AnimationFactory factory = new AnimationFactory(this);

    public NovaGhoulEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void initGoals() {
//        goalSelector.add(0, new SummonGhoulingGoal(this));
        goalSelector.add(1, new MeleeAttackGoal(this, 1.0f, false));
        goalSelector.add(2, new LookAtTargetGoal(this));
        goalSelector.add(3, new WanderAroundFarGoal(this, 0.5f));
//        goalSelector.add(5, new FadeAwayGoal());
        targetSelector.add(0, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
    }

    public static DefaultAttributeContainer.Builder createNovaGhoulAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 10.0f)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.15f)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 100.0f);
    }

    @Override
    public boolean tryAttack(Entity target) {
        if(target instanceof LivingEntity livingTarget) {
            livingTarget.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 20 * 5, 0));
        }

        return super.tryAttack(target);
    }

    @Nullable
    @Override
    public SoundEvent getDeathSound() {
        return SoundEvents.PARTICLE_SOUL_ESCAPE;
    }

    @Nullable
    @Override
    public SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.PARTICLE_SOUL_ESCAPE;
    }

    @Nullable
    @Override
    public SoundEvent getAmbientSound() {
        return SoundEvents.PARTICLE_SOUL_ESCAPE;
    }

    @Override
    public void takeKnockback(double strength, double x, double z) {

    }

    @Override
    public void registerControllers(AnimationData data) {

    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}
