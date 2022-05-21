package draylar.intotheomega.api;

import com.google.common.collect.ImmutableList;
import draylar.intotheomega.api.event.BatchAttackEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;

import java.util.ArrayList;
import java.util.List;

public class BatchDamage {

    public static List<LivingEntity> damage(PlayerEntity player, DamageSource source, float damage, List<LivingEntity> targets) {
        List<LivingEntity> hit = new ArrayList<>();

        BatchAttackData data = new BatchAttackData(targets, damage);
        BatchAttackEvents.BEFORE.invoker().attack(player, data);

        for (LivingEntity target : targets) {
            if(target.damage(source, data.getDamage())) {
                // TODO: apply critical hit modifiers here
                hit.add(target);
            }
        }

        BatchAttackEvents.AFTER.invoker().attack(player, data);

        return hit;
    }

    public static class BatchAttackData {

        private final int targetCount;
        private final ImmutableList<LivingEntity> entities;
        private float baseCriticalHitChance; // 0.0f -> 1.0f (100%)
        private float baseCriticalDamageModifier;
        private float damage;

        public BatchAttackData(List<LivingEntity> entities, float damage) {
            this.targetCount = entities.size();
            this.entities = ImmutableList.<LivingEntity>builder().addAll(entities).build();
            this.damage = damage;
        }

        public void setBaseCriticalHitChance(float baseCriticalHitChance) {
            this.baseCriticalHitChance = baseCriticalHitChance;
        }

        public void setBaseCriticalDamageModifier(float baseCriticalDamageModifier) {
            this.baseCriticalDamageModifier = baseCriticalDamageModifier;
        }

        public void setDamage(float damage) {
            this.damage = damage;
        }

        public int getTargetCount() {
            return targetCount;
        }

        public List<LivingEntity> getEntities() {
            return entities;
        }

        public float getBaseCriticalHitChance() {
            return baseCriticalHitChance;
        }

        public float getBaseCriticalDamageModifier() {
            return baseCriticalDamageModifier;
        }

        public float getDamage() {
            return damage;
        }
    }
}
