package draylar.intotheomega.item.nova;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import draylar.intotheomega.api.BatchDamage;
import draylar.intotheomega.api.item.BatchDamageHandler;
import draylar.intotheomega.api.item.DamageHandler;
import draylar.intotheomega.registry.OmegaDamageSources;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;
import java.util.UUID;

public class BoundNovaItem extends TrinketItem implements DamageHandler, BatchDamageHandler {

    public BoundNovaItem(Settings settings) {
        super(settings);
    }

    @Override
    public void onDamageTarget(World world, PlayerEntity holder, ItemStack stack, LivingEntity target, float finalDamageCache) {
        if(!world.isClient) {
            NbtCompound data = stack.getOrCreateSubNbt("NovaData");
            if(data.contains("Storage")) {
                data.putInt("Storage", Math.min(200, data.getInt("Storage") + (int) finalDamageCache));
            } else {
                data.putInt("Storage", Math.min(200, (int) finalDamageCache));
            }
        }
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getModifiers(ItemStack stack, SlotReference slot, LivingEntity entity, UUID uuid) {
        float damageScale = (stack.getOrCreateSubNbt("NovaData").getInt("Storage") / 200f) * 0.25f;

        return ImmutableMultimap
                .<EntityAttribute, EntityAttributeModifier>builder()
                .put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(uuid, "Nova Core modifier", damageScale, EntityAttributeModifier.Operation.MULTIPLY_TOTAL))
                .build();
    }

    @Override
    public void beforeBatchDamage(World world, PlayerEntity holder, ItemStack stack, BatchDamage.BatchAttackData data) {
        if(data.getTargetCount() >= 5) {
            if(stack.getOrCreateSubNbt("NovaData").getInt("Storage") >= 200) {
                data.setDamage(data.getDamage() * 1.25f);
            }
        }
    }

    @Override
    public void afterBatchDamage(World world, PlayerEntity holder, ItemStack stack, BatchDamage.BatchAttackData data) {
        if(data.getTargetCount() >= 5) {
            int accumulatedDamage = stack.getOrCreateSubNbt("NovaData").getInt("Storage");

            // Deal collected damage to all nearby enemies
            List<Entity> nearby = world.getOtherEntities(holder, new Box(holder.getBlockPos()).expand(15), it -> it instanceof Monster);
            float splitDamage = Math.min(50, accumulatedDamage / nearby.size());
            for (Entity entity : nearby) {

                // Should always be the case given that we're looking for Monster subtypes
                if(entity instanceof LivingEntity living) {
                    living.damage(OmegaDamageSources.nova(holder), splitDamage);
                }
            }

            // TODO: special effect of some sort to indicate a burst and/or AOE damage attack occurred

            stack.getOrCreateSubNbt("NovaData").putInt("Storage", 0);
        }
    }
}
