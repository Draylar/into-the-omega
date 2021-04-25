package draylar.intotheomega.entity;

import draylar.intotheomega.registry.OmegaStatusEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.world.World;

public class FrostedEndermanEntity extends EndermanEntity {

    public FrostedEndermanEntity(EntityType<? extends EndermanEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public boolean tryAttack(Entity target) {
        boolean result = super.tryAttack(target);
        if(result && target instanceof LivingEntity) {
            ((LivingEntity) target).addStatusEffect(new StatusEffectInstance(OmegaStatusEffects.ABYSSAL_FROSTBITE, 15, 0));
        }

        return result;
    }
}
